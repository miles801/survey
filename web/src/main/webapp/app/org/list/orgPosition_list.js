/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (window, angular, $) {

    var app = angular.module('base.orgPosition.list', [
        'base.org',
        'base.org.position',
        'base.orgPosition.add.modal',
        'eccrm.position.position',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('OrgPositionListController', function ($scope, Debounce, CommonUtils, AlertFactory, ModalFactory,
                                                          PositionService, orgPositionaddModal, OrgService, OrgTree, OrgPositionService, positionConstant, Org) {

        var defaults = {orgNames: null, id: '0'};
        $scope.orglist = angular.extend({}, defaults);
        var default_con = {id: null};
        $scope.condition = angular.extend({}, default_con);
        $scope.conditions = angular.extend({}, default_con);
        positionConstant.roleType(function (data) {
            $scope.roleTypes = data;
        });
        positionConstant.status(function (data) {
            $scope.statuss = data;
        });

        // 获取树的配置
        var setting = OrgTree.dynamicTree2(function (event, treeId, treeNode) {
            $scope.query(treeNode.id);
        });

        $scope.addPosition = function () {
            if (!$scope.condition.id) {
                AlertFactory.error($scope, null, "请选择机构");
            }
            orgPositionaddModal.select({scope: $scope, id: $scope.condition.id}, function (data) {
                $scope.pager.query($scope.condition.id)
            });
        };

        //删除
        $scope.del = function () {
            $scope.serchbean = {};
            $scope.serchbean.orgId = $scope.condition.id;
            art.dialog({
                content: '是否删除岗位，请确认！',
                ok: function () {
                    if (!$scope.items || $scope.items.length < 1) return;
                    angular.forEach($scope.items || [], function (v, index) {
                        (function (t, m) {
                            $scope.serchbean.roleId = t.id;
                            var params = angular.extend({}, $scope.serchbean);
                            OrgPositionService.deleteByRoleId(params, function (data) {
                                if (data && data.success) {
                                    $scope.query($scope.condition.id);
                                } else {
                                    AlertFactory.error($scope, "删除失败！");
                                }
                            });

                        })(v, index);
                    });
                    return true;

                },
                cancelVal: '关闭',
                cancel: true
            });
        };
        $scope.remove = function (id) {
            //删除
            $scope.serchbean = {};
            $scope.serchbean.roleId = id;
            $scope.serchbean.orgId = $scope.condition.id;
            var params = angular.extend({}, $scope.serchbean);
            art.dialog({
                content: '是否删除岗位，请确认！',
                ok: function () {
                    OrgPositionService.deleteByRoleId(params);
                    $scope.query($scope.condition.id);
                    return true;

                },
                cancelVal: '关闭',
                cancel: true
            });
        };
        $scope.query = function (id) {
            if (id === undefined) {
                return;
            }
            $scope.condition.id = id;
            $scope.condition.orgId = id;
            $scope.pager.query();
        };

        // 查询岗位
        $scope.queryPosition = function () {
            var param = angular.extend({}, $scope.conditions, {start: this.start, limit: this.limit});
            CommonUtils.promise(function (defer) {
                var result = PositionService.query(param, function (data) {
                    data = data.data;
                    $scope.beans = data.data;
                    defer.resolve(data.data);
                });
            })
        };

        // 定义分页对象
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {orgId: $scope.condition.id}, {
                    start: $scope.pager.start,
                    limit: $scope.pager.limit
                });
                if (!param.orgId) {
                    CommonUtils.errorDialog('查询时，没有指定机构ID!');
                    return false;
                }
                return CommonUtils.promise(function (defer) {
                    var result = OrgPositionService.pageQueryByOrgId(param, function (data) {
                        data = data.data || [];
                        $scope.beans = data;
                        defer.resolve(data);
                    });
                });
            }
        };

        // 初始化左侧组织机构树（仅显示有效树）
        var initTree = function () {
            Org.tree($scope, 'treeDemo', function (treeNode) {
                $scope.query(treeNode.id);
            });
        };
        initTree();

    });
})(window, angular, jQuery);