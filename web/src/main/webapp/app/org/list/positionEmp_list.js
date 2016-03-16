/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.positoin.positoinEmp.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'base.org',
        'eccrm.organi.Emp',
        'base.org.position',
        'base.position.emp',
        'eccrm.angular.ztree',
        'eccrm.base.employee.modal' // 员工弹出层
    ]);
    app.controller('PositionEmpListController', function ($scope, OrgPosition, AlertFactory, ModalFactory, CommonUtils, OrgService, EmpService, OrgPositionService, OrgTree, PositionEmpService, EmployeeModal) {
        //初始化参数
        $scope.condition = {};
        $scope.beans = {};


        //回到上一个页面
        $scope.back = CommonUtils.back;


        $scope.remove = function (id) {
            ModalFactory.remove($scope, function () {
                // 获取ID
                if (!id) {
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.id);
                    });
                    id = items.join(',');
                }
                var promise = PositionEmpService.deleteRelations({
                    positionId: $scope.condition.positionId,
                    orgId: $scope.condition.orgId,
                    empIds: id
                });
                CommonUtils.loading(promise, '删除中...', $scope.query);
            });
        };

        /**
         * 添加员工到机构岗位下
         */
        $scope.addEmp = function () {
            EmployeeModal.pickMultiEmployee({}, function (emps) {
                // 获得所有员工的id
                var empIds = [];
                angular.forEach(emps || [], function (emp) {
                    empIds.push(emp.id);
                });
                // 保存关联关系
                var promise = PositionEmpService.batchSave({
                    orgId: $scope.condition.orgId,
                    positionId: $scope.condition.positionId,
                    empIds: empIds.join(',')
                });
                CommonUtils.loading(promise, '保存中...', $scope.query);
            });
        };
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                return CommonUtils.promise(function (defer) {
                    CommonUtils.loading(defer, '数据加载中...');
                    PositionEmpService.queryEmp(param, function (data) {
                        data = data.data || {total: 0};
                        $scope.beans = data;
                        defer.resolve(data);
                    });
                }, 500);
            }
        };


        //查询数据
        $scope.current = {};
        $scope.query = function () {
            $scope.pager.query();
        };


        // 初始化组织机构岗位树
        OrgPosition.permissionTree($scope, 'treeDemo', function (node) {
            $scope.condition.positionId = node.id;
            $scope.condition.orgId = node.pId || node.parentId;
            $scope.query();
        })

    });
})(window, angular, jQuery);