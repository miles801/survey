/**
 * Created by miles on 13-11-25.
 */
(function (angular, $) {
    var app = angular.module('eccrm.base.menu.edit', [
        'eccrm.base.menu',
        'eccrm.base.resource',
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.directive.ztree'
    ]);
    app.controller('MenuEditController', function ($scope, CommonUtils, $timeout, MenuService, MenuTree, MenuConstant, ResourceService, ModalFactory) {

        //定义变量
        $scope.menu = {};

        $scope.uploadOptions = {
            // 最大上传文件数
            maxFile: 1,
            bid: $('#id').val(),
            btype: 'menuIcon',
            swfOption: {
                formData: {description: '菜单图标', businessType: 'menuIcon'},

                // 文件的被允许的大小，单位KB
                buttonText: '上传图标',
                fileSizeLimit: 100,
                fileTypeExts: '*.png;*.jpg;*.gif'
            }
        };
        var originalName, originalCode, originalStatus;//原始信息，用于回显时的验证
        $scope.hasName = {
            validateType: 'multiName',
            validateMsg: '名称重复',
            validateFn: function (value) {
                if (!value || value === originalName) return true;
                return CommonUtils.promise(function (defer) {
                    MenuService.hasName({
                        id: CommonUtils.parse($scope.menu, "parent.id"),
                        name: encodeURI(encodeURI($scope.menu.name))
                    }, function (data) {
                        defer.resolve(!data.data);
                    });
                });
            }
        };
        $scope.hasCode = {
            validateType: 'multiCode',
            validateMsg: '编号重复',
            validateFn: function (value) {
                if (!value || value === originalCode) return true;
                return CommonUtils.promise(function (defer) {
                    MenuService.hasCode({
                        code: encodeURI(encodeURI(value))
                    }, function (data) {
                        defer.resolve(!data.data);
                    });
                });
            }
        };
        // 选择上级菜单
        $scope.ztreeOptions = {
            data: function () {
                return CommonUtils.promise(function (defer) {
                    CommonUtils.loading(defer, '加载上级菜单...');
                    MenuService.queryOther({id: $scope.menu.id}, function (data) {
                        data = data.data;
                        defer.resolve(data);
                    });
                });
            },
            click: function (node) {
                $scope.menu.parent = {id: node.id, name: node.name};
                $scope.form.name.$setViewValue($scope.menu.name);
            },
            position: 'absolute'
        };
        // 选择资源
        $scope.ztreeResource = {
            data: function () {
                return CommonUtils.promise(function (defer) {
                    CommonUtils.loading(defer, '加载资源...');
                    ResourceService.queryValidResource({id: $scope.menu.id}, function (data) {
                        data = data.data;
                        defer.resolve(data);
                    });
                });
            },
            click: function (node) {
                $scope.menu.resource = {id: node.id, name: node.name};
            },
            position: 'absolute'
        };
        // 加载菜单类型
        MenuConstant.types(function (types) {
            $scope.types = types;
        });
        MenuConstant.status(function (data) {
            $scope.status = data;
        });

        //定义方法
        $scope.back = CommonUtils.back;
        var load = function (id, callback) {
            var result = MenuService.get({id: id});
            CommonUtils.loading(result, '加载菜单信息...', function (data) {
                data = data.data;
                originalName = data.name;
                originalCode = data.code;
                originalStatus = data.status;
                $scope.menu = data;
                $scope.menu.resource = {
                    id: data.resourceId,
                    name: data.resourceName
                };
                $scope.menu.parent = {
                    id: data.parentId,
                    name: data.parentName
                };
                if (angular.isFunction(callback)) {
                    callback(data.data);
                }

            });
        };
        // 获取附件的id
        var wrapAttachment = function () {
            var obj = angular.extend({}, $scope.menu);
            obj.icon = $scope.uploadOptions.getAttachment()[0];
            obj.attachmentIds = obj.icon;
            // 取最后一个
            return obj;
        };

        $scope.save = function () {
            var obj = wrapAttachment();
            var promise = MenuService.save(obj, function () {
                CommonUtils.addTab('update');
                $scope.back();
            });
            CommonUtils.loading(promise, '保存中...');
        };

        /**
         * 更新菜单
         * 更新前，先获得附件信息；
         * 更新前，验证状态是否变更，如果变更了，则提示进行确认
         */
        $scope.update = function () {
            var obj = wrapAttachment();
            var newStatus = obj.status;
            var doUpdate = function () {
                var promise = MenuService.update(obj, function () {
                    CommonUtils.addTab('update');
                    $scope.back();
                });
                CommonUtils.loading(promise, '更新中...');
            };
            if (newStatus != originalStatus) {
                ModalFactory.confirm({
                    scope: $scope,
                    content: '更新菜单时，更新了菜单状态，<span style="color:red;">会级联更新所有的子菜单</span>，请确认该操作!'
                }, doUpdate);
            } else {
                doUpdate();
            }
        };

        $scope.fileUploadOptions = {
            id: 'fileupload',
            success: function (data) {
                if (data && data.length > 0) {
                    $scope.menu.icon = data[0].id;
                }
            }
        };

        //执行
        var pageType = $('#pageType').val();
        var id = $scope.menu.id = $('#id').val();
        if (pageType == 'modify') {//更新页面
            load(id);
        } else if (pageType == 'detail') {//查看页面
            load(id, function () {
                $('input,textarea,select').attr('disabled', 'disabled');
            });
        } else if (pageType == 'add') {
            $scope.menu = angular.extend({
                creatorName: CommonUtils.loginContext().username
            }, MenuConstant.defaults);
        }
    })
})(angular, jQuery);