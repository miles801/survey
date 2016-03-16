/**
 * Created by miles on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  angularstrap-wrap.js
 *  role.js
 *
 */


(function (angular, window) {
    var app = angular.module('eccrm.base.role.modal', [
        'eccrm.base.role',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    // 角色编辑模态对话框
    app.factory('RoleModal', function ($modal, RoleConstant, RoleService, AlertFactory, ModalFactory, CommonUtils) {
            var common = function (options) {
                var defaults = {
                    scope: null,//必选项
                    callback: null,//点击确定后要执行的函数
                    afterShown: null//模态对话框显示完成后要执行的函数
                };
                options = angular.extend({}, defaults, options);
                var scope = options.scope;
                if (!scope) throw '使用模态对话框时必须指定scope!';
                var modal = $modal({scope: scope, template: 'app/base/role/template/role-edit-modal.ftl.html'});
                var $scope = modal.$scope;
                RoleConstant.status(function (data) {
                    $scope.status = data;
                });
                angular.isFunction(options.afterShown) ? ModalFactory.afterShown(modal, options.afterShown) : null;
                return $scope;
            };

            return {
                add: function (options, callback) {
                    callback = callback || options.callback;
                    var $scope = common(options);
                    $scope.pageType = 'add';
                    $scope.role = {
                        creatorName: CommonUtils.loginContext().username,
                        status: 'ACTIVE',
                        createdDatetime: new Date().getTime()
                    };
                    $scope.save = function (createNew) {
                        var result = RoleService.save($scope.role);
                        AlertFactory.handle($scope, result, function (data) {
                            if (createNew == true) {
                                $scope.role.name = undefined;
                                $scope.role.code = undefined;
                                $scope.role.description = undefined;
                            } else {
                                angular.isFunction(callback) ? callback.call($scope, data.id) : null;
                                $scope.$hide();
                            }
                        });
                    };
                },
                modify: function (options, callback) {
                    if (!options.id) throw '更新角色时，没有获得角色ID!';
                    callback = callback || options.callback;
                    var $scope = common(options);
                    $scope.pageType = 'modify';
                    RoleService.get({id: options.id}, function (data) {
                        $scope.role = data.data || {};
                    });
                    $scope.update = function () {
                        var result = RoleService.update($scope.role);
                        AlertFactory.handle($scope, result, function (data) {
                            angular.isFunction(callback) ? callback.call($scope, data.id) : null;
                            $scope.$hide();
                        });
                    };
                },
                view: function (options, callback) {
                    if (!options.id) throw '查看角色时，没有获得角色ID!';
                    callback = callback || options.callback;
                    options.afterShown = function () {
                        $('input,textarea,select', '.modal').attr('disabled', 'disabled');
                    };
                    var $scope = common(options);
                    $scope.pageType = 'view';
                    RoleService.get({id: options.id}, function (data) {
                        $scope.role = data.data || {};
                    });
                }
            }
        }
    );

    // 角色选择器
    app.factory('RolePickerModal', function ($modal, RoleService, AlertFactory, ModalFactory, CommonUtils) {
        var common = function (options) {
            var defaults = {
                scope: null,//必选项
                callback: null//点击确定后要执行的函数
            };
            options = angular.extend({}, defaults, options);
            var scope = options.scope;
            if (!scope) throw '使用模态对话框时必须指定scope!';
            var templateUrl = options.templateUrl || 'app/base/role/template/role-picker.tpl.html';
            var modal = $modal({scope: scope, template: templateUrl});
            var $scope = modal.$scope;
            $scope.condition = {};
            $scope.query = function () {
                var result = RoleService.queryValid({
                    name: CommonUtils.encode($scope.condition.name),
                    code: CommonUtils.encode($scope.condition.code),
                    userId: options.userId,
                    groupId: options.groupId
                });
                AlertFactory.handle($scope, result, function (data) {
                    $scope.beans = data.data || [];
                    $scope.items = [];// 每次请求到新数据后清空已选择的数据
                });

            };
            return $scope;
        };

        return {
            // 选择单个角色
            single: function () {

            },

            // 选择多个角色
            multi: function (options, callback) {
                var $scope = common(options);
                callback = callback || options.callback;
                $scope.confirm = function () {
                    angular.isFunction(callback) ? callback.call($scope, $scope.items) : null;
                    $scope.$hide();
                };
                $scope.query();
            },
            // 查看角色列表
            view: function (options) {
                options.templateUrl = 'app/base/role/template/role-view.tpl.html';
                var $scope = common(options);
                $scope.beans = options.data;
            }
        }
    });
})(angular, window);