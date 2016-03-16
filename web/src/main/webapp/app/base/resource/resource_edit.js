/**
 * Created by miles on 13-11-25.
 */
(function (angular, $) {
    var app = angular.module('eccrm.base.resource.edit', [
        'eccrm.base.resource',
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree'
    ]);
    app.controller('ResourceEditCtrl', function ($scope, CommonUtils, $timeout, ResourceService, ResourceParam) {
        // 定义变量
        $scope.resource = {};
        var originalName, originalCode;//原始信息，用于回显时的验证
        $scope.ztreeOptions = {
            data: function () {
                return CommonUtils.promise(function (defer) {
                    ResourceService.queryOther({id: $scope.resource.id}, function (data) {
                        data = data.data;
                        defer.resolve(data);
                    });
                });
            },
            click: function (node) {
                $scope.resource.parent = {id: node.id, name: node.name};
                $scope.form.name.$setViewValue($scope.resource.name);
            },
            position: 'absolute'
        };
        // 加载资源类型
        ResourceParam.types(function (types) {
            $scope.types = types;
        });
        // 加载资源状态
        ResourceParam.status(function (data) {
            $scope.status = data;
        });
        // 加载模块
        ResourceParam.modules(function (data) {
            $scope.modules = data;
        });

        //定义方法
        $scope.back = CommonUtils.back;

        // 根据ID加载资源
        var load = function (id, callback) {
            var result = ResourceService.get({id: id}, function (data) {
                data = data.data;
                originalName = data.name;
                originalCode = data.code;
                $scope.resource = data;
                $scope.resource.parent = {
                    id: data.parentId,
                    name: data.parentName
                };
                if (angular.isFunction(callback)) {
                    callback(data.data);
                }
            });
            CommonUtils.loading(result, '加载资源信息...');
        };

        // 在保存/更新前检查是否有效
        // success：有效后要执行的回调
        // fail：无效时要执行的回调
        var checkIsValid = function (success, fail) {
            var checks = [];
            // 检查名称是否合法
            checks.push(function () {
                var context = this;
                if ($scope.resource.name == originalName) {
                    context.resolve(true);
                    return;
                }
                var promise = ResourceService.hasName({
                    id: CommonUtils.parse($scope.resource, "parent.id"),
                    name: encodeURI(encodeURI($scope.resource.name))
                }, function (data) {
                    !data.data ? context.resolve() : context.reject('名称重复!');
                });
                CommonUtils.loading(promise, '校验名称的合法性...');
            });
            // 检查编号是否合法
            checks.push(function () {
                var context = this;
                if ($scope.resource.code == originalCode) {
                    context.resolve(true);
                    return;
                }
                var promise = ResourceService.hasCode({
                    code: encodeURI(encodeURI($scope.resource.code))
                }, function (data) {
                    !data.data ? context.resolve() : context.reject('编号重复!');
                });
                CommonUtils.loading(promise, '校验编号的合法性...');
            });
            // 检查所属模块
            checks.push(function () {
                var context = this;
                var type = $scope.resource.type;
                // 页面元素和数据都必须选择数据所属模块
                if ((type == '2' || type == '3') && !$scope.resource.module) {
                    context.reject('请选择所属模块!');
                } else {
                    context.resolve();
                }
            });
            CommonUtils.chain(checks, success, fail);
        };
        var setInvalid = function () {
            $scope.form.$setValidity('handling', false);
        };
        var setValid = function () {
            $scope.form.$setValidity('handling', true);
        };
        // 保存/更新失败后的回调
        var handleFailCallback = function (data) {
            if (data.success) {
                CommonUtils.addTab('update');
                $scope.back();
                return;
            }
            setValid();
        };
        // 保存
        $scope.save = function () {
            setInvalid();
            checkIsValid(function () {
                var result = ResourceService.save($scope.resource);
                CommonUtils.loading(result, '保存资源...', handleFailCallback);
            }, function (failReason) {
                alert(failReason);
                setValid();
            });
        };

        // 更新
        $scope.update = function () {
            setInvalid();
            checkIsValid(function () {
                var result = ResourceService.update($scope.resource);
                CommonUtils.loading(result, '更新资源...', handleFailCallback);
            }, function (failReason) {
                alert(failReason);
                setValid();
            });
        };

        //执行
        var pageType = $('#pageType').val();
        var id = $scope.resource.id = $('#id').val();
        if (pageType == 'modify') {//更新页面
            load(id);
        } else if (pageType == 'detail') {//查看页面
            load(id, function () {
                $('input,textarea,select').attr('disabled', 'disabled');
            });
        } else if (pageType == 'add') {
            $scope.resource = angular.extend({
                creatorName: CommonUtils.loginContext().username
            }, ResourceParam.defaults);
        }
    })
})(angular, jQuery);