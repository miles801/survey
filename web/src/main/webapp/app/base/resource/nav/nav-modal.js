/**
 * 依赖于:
 *  menu.js
 *  ztree.js
 *  ztree-directive.js
 *
 * Created by miles on 2014/8/3.
 */
(function () {
    var app = angular.module('eccrm.base.nav.modal', [
        'eccrm.base.nav',
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.resource',
        'eccrm.directive.ztree'
    ]);

    app.factory('MenuNavModal', function ($modal, MenuNavService, MenuNavConstant, CommonUtils, ResourceService) {
            var init = function () {
                var scope = this;
                MenuNavConstant.types(function (data) {
                    scope.types = data;
                });
                MenuNavConstant.status(function (data) {
                    scope.status = data;
                });
                // 检验是否为数字，切是否为有效行、列数
                scope.numberCheck = {
                    validateMsg: '',
                    trigger: 'now',
                    validateCode: 'number',
                    validateFn: function (value, code, options) {
                        if (value === undefined || value == null || value == '') {
                            options.validateMsg = '必填项!';
                            return false;
                        }
                        var reg = /^\d+$/g;
                        if (!reg.test(value)) {
                            options.validateMsg = '只能是数字!';
                            return false;
                        }
                        var v = parseInt(value);
                        if (v < 1 || v > 3) {
                            options.validateMsg = '值只能是1、2、3!';
                            return false;
                        }
                        return true;
                    }
                };

                // 加载ztree
                scope.sourceTree = {
                    multi: true,
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            ResourceService.queryValidResource(function (data) {
                                data = data.data;
                                defer.resolve(data);
                            });
                        });
                    },
                    click: function (node) {
                        scope.bean.resourceId = node.id;
                        scope.bean.resourceName = node.name;
                    }
                };
                // 选择上级的树
                scope.parentTree = {
                    multi: true,
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            MenuNavService.queryValid(function (data) {
                                data = data.data;
                                defer.resolve(data);
                            });
                        });
                    },
                    click: function (node) {
                        scope.bean.parent = {id: node.id, name: node.name};
                    }
                };

            };
            return {
                // 打开新增弹出层
                add: function (callback) {
                    var modal = $modal({template: CommonUtils.contextPathURL('/app/base/resource/nav/nav-tpl.html')});
                    var $scope = modal.$scope;
                    $scope.bean = {
                        sequenceNo: 1,
                        status: 'ACTIVE',
                        showMore: true,
                        type: 'SINGLE',
                        rows: 1,
                        cols: 1,
                        createdDatetime: new Date().getTime(),
                        creatorId: CommonUtils.loginContext().userId,
                        creatorName: CommonUtils.loginContext().username
                    };
                    $scope.pageType = 'add';
                    init.call($scope);
                    $scope.save = function () {
                        if ($scope.bean.status == 'ACTIVE') {
                            $scope.bean.publishDatetime = new Date().getTime();
                        }

                        var result = MenuNavService.save($scope.bean);
                        CommonUtils.loading(result, '正在保存...', function (data) {
                                if (typeof callback === 'function') {
                                    callback($scope.bean);
                                }
                                modal.hide();
                            }
                        );
                    };
                },
                // 打开更新弹出层
                // 参数（必须）：id 导航菜单的id
                // 参数（可选）：callback 更新成功后的回调
                update: function (id, callback) {
                    var modal = $modal({template: CommonUtils.contextPathURL('/app/base/resource/nav/nav-tpl.html')});
                    var $scope = modal.$scope;
                    $scope.bean = {};
                    $scope.pageType = 'modify';
                    init.call($scope);
                    // 加载数据
                    var load = function () {
                        var result = MenuNavService.get({id: id});
                        CommonUtils.loading(result, '正在加载数据...', function (data) {
                            data = data.data || {};
                            $scope.bean = data;
                            $scope.bean.parent = {
                                id: data.parentId,
                                name: data.parentName
                            };
                        });
                    };
                    $scope.update = function () {
                        if ($scope.bean.status == 'ACTIVE') {
                            $scope.bean.publishDatetime = new Date().getTime();
                        }
                        var result = MenuNavService.update($scope.bean);
                        CommonUtils.loading(result, '正在更新...', function (data) {
                                if (typeof callback === 'function') {
                                    callback($scope.bean);
                                }
                                modal.hide();
                            }
                        )
                    };
                    load();
                },
                // 查看导航菜单
                // 参数（必须）：导航菜单id
                view: function (id) {
                    var modal = $modal({
                        template: CommonUtils.contextPathURL('/app/base/resource/nav/nav-tpl.html'),
                        backdrop: true
                    });
                    var $scope = modal.$scope;
                    $scope.bean = {};
                    $scope.pageType = 'view';
                    init.call($scope);
                    // 加载数据
                    var load = function () {
                        var result = MenuNavService.get({id: id});
                        CommonUtils.loading(result, '正在加载数据...', function (data) {
                            data = data.data || {};
                            $scope.bean = data;
                            $scope.bean.parent = {
                                id: data.parentId,
                                name: data.parentName
                            };
                            $('input,textarea,select', '.modal .modal-body').attr('disabled', 'disabled');
                        });
                    };
                    load();
                }
            }
        }
    );
})();