/**
 * <p>
 *     依赖：eccrm-ztree-all.js
 * </p>
 * Created by Michael on 2014/10/26.
 */
(function () {
    var app = angular.module('eccrm.base.employee.modal', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree'
    ]);

    // 访问员工的后台服务
    app.service('EmployeeService', ['$resource', 'CommonUtils', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/base/employee/:method:_tmp'), {}, {
            pageQuery: {method: 'POST', params: {method: 'query', start: '@start', limit: '@limit'}, isArray: false},

            // 查询有效的员工：正式、实习、调动中
            queryValid: {
                method: 'POST',
                params: {method: 'queryValid', limit: '@limit', start: '@start'},
                isArray: false
            }
        });
    }]);

    // 提供员工选择相关的弹出层
    app.service('EmployeeModal', ['$modal', 'CommonUtils', 'EmployeeService', 'AlertFactory',
        function ($modal, CommonUtils, EmployeeService, AlertFactory) {
            return {
                /**
                 * 选择一个员工
                 * @param options 配置项
                 * @param callback 回调
                 */
                pickEmployee: function (options, callback) {
                    var modal = $modal({
                        template: CommonUtils.contextPathURL('/app/employee/template/modal-employee.ftl.html'),
                        backdrop: 'static'
                    });
                    var $scope = modal.$scope;
                    options = options || {};
                    $scope.condition = angular.extend({}, options.condition);
                    callback = callback || options.callback;

                    // 分页对象
                    $scope.pager = {
                        limit: 5,
                        fetch: function () {
                            return CommonUtils.promise(function (defer) {
                                var obj = angular.extend({}, $scope.condition, options.condition, {
                                    start: $scope.pager.start,
                                    limit: $scope.pager.limit
                                });
                                var promise = EmployeeService.queryValid(obj);
                                CommonUtils.loading(promise, '加载中...', function (data) {
                                    data = data.data || {};
                                    $scope.emps = data;
                                    defer.resolve(data.total);
                                }, $scope);
                            });
                        }
                    };

                    $scope.orgTree = CommonUtils.defer();
                    // 组织机构
                    var orgDefer = CommonUtils.lazyLoad(app, 'base.org', CommonUtils.contextPathURL('/app/org/org.js'));
                    orgDefer.then(function (injector) {
                        var Org = injector.get('Org');
                        var o = Org.pick(function (node) {
                            $scope.condition.orgId = node.id;
                            $scope.condition.orgName = node.name;
                        });
                        $scope.orgTree.resolve(o);
                    });

                    // 清空查询条件
                    $scope.clear = function () {
                        $scope.condition = {};
                    };

                    $scope.clearOrg = function () {
                        $scope.condition.orgId = null;
                        $scope.condition.orgName = null;
                    };

                    // 查询
                    $scope.query = function () {
                        $scope.pager.query();
                    };

                    // 点击确认
                    $scope.confirm = function () {
                        if (angular.isFunction(callback)) {
                            callback.call($scope, $scope.selected);
                            modal.hide();
                        }
                    }
                },

                /**
                 * 多选员工
                 * @param options 配置项
                 * @param callback 回调
                 */
                pickMultiEmployee: function (options, callback) {
                    var modal = $modal({
                        template: CommonUtils.contextPathURL('/app/employee/template/modal-employee-multi.ftl.html'),
                        backdrop: 'static'
                    });
                    var $scope = modal.$scope;
                    options = options || {};
                    $scope.condition = angular.extend({}, options.condition);
                    callback = callback || options.callback;

                    // 分页对象
                    $scope.pager = {
                        limit: 10,
                        fetch: function () {
                            return CommonUtils.promise(function (defer) {
                                var obj = angular.extend({}, $scope.condition, options.condition, {
                                    start: $scope.pager.start,
                                    limit: $scope.pager.limit
                                });
                                var promise = EmployeeService.queryValid(obj);
                                CommonUtils.loading(promise, '加载中...', function (data) {
                                    data = data.data || {};
                                    $scope.emps = data;
                                    defer.resolve(data.total);
                                }, $scope);
                            });
                        }
                    };

                    $scope.orgTree = CommonUtils.defer();
                    // 组织机构
                    var orgDefer = CommonUtils.lazyLoad(app, 'base.org', CommonUtils.contextPathURL('/app/org/org.js'));
                    orgDefer.then(function (injector) {
                        var Org = injector.get('Org');
                        var o = Org.pick(function (node) {
                            $scope.condition.orgId = node.id;
                            $scope.condition.orgName = node.name;
                        });
                        $scope.orgTree.resolve(o);
                    });

                    // 清空查询条件
                    $scope.clear = function () {
                        $scope.condition = {};
                        $scope.orgName = null;
                    };

                    $scope.clearOrg = function () {
                        $scope.condition.orgId = null;
                        $scope.orgName = null;
                    };

                    // 查询
                    $scope.query = function () {
                        $scope.pager.query();
                    };

                    // 点击确认
                    $scope.confirm = function () {
                        if (angular.isFunction(callback)) {
                            callback.call($scope, $scope.items);
                            modal.hide();
                        }
                    }
                }
            }
        }]);
})();