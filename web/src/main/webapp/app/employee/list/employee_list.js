/**
 * Created by chenl on 2014-10-15 14:47:37.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.base.employee.list', ['eccrm.base.employee',
        'eccrm.angular',
        'base.org',
        'eccrm.directive.ztree',
        'eccrm.angularstrap']);
    app.controller('EmployeeListController', function ($scope, EmployeeConstant, OrgTree, CommonUtils, ModalFactory, AlertFactory, $window, Debounce, EmployeeService) {
        $scope.OrgztreeOptions = OrgTree.dynamicTree(function (node) {
            $scope.condition.organization = {id: node.id, name: node.name};
        });
        // 职务
        EmployeeConstant.duty(function (data) {
            $scope.dutys = data;
        });
        // 状态
        EmployeeConstant.status(function (data) {
            $scope.EmpStatus = data;
        });
        var defaults = {
            orderBy: 'createdDatetime',
            reverse: 'false'
        };

        $scope.add = function () {
            CommonUtils.addTab({
                title: '新增员工',
                url: '/base/employee/add',
                onUpdate: $scope.pager.load
            });
        };

        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新员工',
                url: '/base/employee/modify/' + id,
                onUpdate: $scope.pager.load
            });

        };
        $scope.detail = function (id) {
            CommonUtils.addTab({
                title: '查看员工',
                url: '/base/employee/detail/' + id
            });
        };
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
        };
        $scope.reset();
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                return CommonUtils.promise(function (defer) {
                    var promise = EmployeeService.permissionPageQuery(param);
                    CommonUtils.loading(promise, '加载员工列表...', function (data) {
                        data = data.data || {total: 0, data: []};
                        $scope.employees = data;
                        defer.resolve(data);
                    });
                });
            },
            finishInit: function () {
                this.load();
            }
        };
        $scope.query = function () {
            $scope.pager.query();
        };
        $scope.remove = function (id) {
            //判断id是通过点击没条记录后的删除图标（会传递id）还是点击的上面删除按钮
            if (id == undefined) {
                var ids = new Array();
                angular.forEach($scope.items || [], function (v) {
                    ids.push(v.id);
                });
                for (var i = 0; i < ids.length; i++) {
                    if (ids[i] != 'undefined') {
                        id = id + ids[i] + ",";
                    }
                }
            }
            //删除
            art.dialog({
                content: '是否删除员工，请确认！',
                ok: function () {
                    var result = EmployeeService.deleteByIds({ids: id});
                    AlertFactory.handle($scope, result, function () {
                        $scope.query()
                    });
                    return true;
                },
                cancelVal: '关闭',
                cancel: true
            });

        };

    });
})(window, angular, jQuery);