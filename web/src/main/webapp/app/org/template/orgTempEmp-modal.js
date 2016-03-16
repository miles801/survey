/**
 * Created by liuxq on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  usergroup.js
 */


(function (angular, window) {
    var app = angular.module('base.org.tempEmp', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',// ztree
        'base.orgtemp'// temp org
    ]);

    // 从组织机构中选择有效的员工（数据来自于临时表）
    app.factory('OrgTempEmp', function ($modal, AlertFactory, CommonUtils, ModalFactory, OrgTempService, EmpTempService) {
        return {
            // 从组织机构（临时表）中选择员工（临时表）
            // 返回被选中的员工的集合
            // 配置参数：options（{}），可配置项如下
            // callback:function(data){}  点击确定后，获得被选中的员工列表
            pick: function (options) {

                // 实现思路
                // 1、初始化时，只显示左侧树，点击树节点查询对应的员工信息
                // 2、输入查询条件，过滤出符合条件的组织机构树，然后点击树节点查询对应的员工信息
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/org/template/orgTempEmp_select.tpl.html')
                });
                var $scope = modal.$scope;
                $scope.condition = {};

                $scope.orgTree = {
                    // 初识数据加载
                    data: function (ck) {
                        return CommonUtils.promise(function (defer) {
                            OrgTempService.queryValidChildren(function (data) {
                                data = data.data || [];
                                defer.resolve(data);
                            });
                        });
                    },
                    // 异步数据使用方法
                    // callback一定要调用，否则树将不会被刷出来
                    async: function (node, ck) {
                        var pid = node.id;
                        OrgTempService.queryValidChildren({id: pid}, function (data) {
                            ck.call(node, data.data || []);
                        });
                    },
                    click: function (node) {
                        $scope.orgName = node.name;
                        $scope.condition.orgID = node.id;
                    }
                };
                $scope.pager = {
                    limit: 10,
                    fetch: function () {
                        var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                        $scope.emps = {};
                        return CommonUtils.promise(function (defer) {
                            var promise = EmpTempService.queryValid(param);
                            CommonUtils.loading(promise, '查询中...', function (data) {
                                data = data.data || {total: 0, data: []};
                                defer.resolve(data);
                                $scope.emps = data;
                            });
                        });
                    }
                };

                $scope.clear = function () {
                    $scope.condition.orgID = null;
                    $scope.orgName = null;
                };
                // query temp emp
                $scope.query = function () {
                    $scope.pager.query();
                };

                // confirm
                $scope.confirm = function () {
                    if (options && angular.isFunction(options.callback)) {
                        var items = $scope.items;
                        options.callback(items);
                    }
                    $scope.$hide();
                };
            },
            // 从组织机构（临时表）中选择员工（临时表）
            // 返回被选中的员工的集合
            // 配置参数：options（{}），可配置项如下
            // callback:function(data){}  点击确定后，获得被选中的员工列表
            onlyPick: function (options) {

                // 实现思路
                // 1、初始化时，只显示左侧树，点击树节点查询对应的员工信息
                // 2、输入查询条件，过滤出符合条件的组织机构树，然后点击树节点查询对应的员工信息
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/org/template/orgTempEmpOnly_select.tpl.html')
                });
                var $scope = modal.$scope;
                $scope.condition = {};

                $scope.orgTree = {
                    // 初识数据加载
                    data: function (ck) {
                        return CommonUtils.promise(function (defer) {
                            OrgTempService.queryValidChildren(function (data) {
                                data = data.data || [];
                                defer.resolve(data);
                            });
                        });
                    },
                    // 异步数据使用方法
                    // callback一定要调用，否则树将不会被刷出来
                    async: function (node, ck) {
                        var pid = node.id;
                        OrgTempService.queryValidChildren({id: pid}, function (data) {
                            ck.call(node, data.data || []);
                        });
                    },
                    click: function (node) {
                        $scope.orgName = node.name;
                        $scope.condition.orgID = node.id;
                    }
                };
                $scope.pager = {
                    limit: 10,
                    fetch: function () {
                        var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                        $scope.emps = {};
                        return CommonUtils.promise(function (defer) {
                            var promise = EmpTempService.queryValid(param);
                            CommonUtils.loading(promise, '查询中...', function (data) {
                                data = data.data || {total: 0, data: []};
                                defer.resolve(data);
                                $scope.emps = data;
                            });
                        });
                    }
                };

                $scope.clear = function () {
                    $scope.condition.orgID = null;
                    $scope.orgName = null;
                };
                // query temp emp
                $scope.query = function () {
                    $scope.pager.query();
                };

                // confirm
                $scope.confirm = function () {
                    if (options && angular.isFunction(options.callback)) {
                        var items = angular.extend({}, $scope.selected);
                        options.callback(items);
                    }
                    $scope.$hide();
                };
            }
        }
    });
})(angular, window);