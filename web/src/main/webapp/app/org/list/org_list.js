/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (window, angular, $) {

    var app = angular.module('base.org.list', [
        'base.org',
        'base.org.modify.modal',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('OrgListController', function ($scope, CommonUtils, AlertFactory, ModalFactory,
                                                  OrgModal, OrgService, OrgTree, Org, OrgConstant, RegionService) {

        $scope.orglist = {};


        // 添加机构
        $scope.addOrg = function () {
            OrgModal.add({scope: $scope}, function () {
                initTree();
            });

        };

        // 从临时机构表中选择机构同步到本地机构表
        $scope.addOrgFromTemp = function () {
            OrgModal.addOrgFromTemp({scope: $scope, callback: initTree}, function () {
                initTree();
            });

        };

        // 给机构添加岗位
        $scope.addPositionByOrgId = function () {
            var id = $scope.id;
            if (!id) {
                AlertFactory.error($scope, null, '请先点击机构!');
                return;
            }
            var promise = OrgService.addPostion({id: id});
            CommonUtils.loading(promise, '智能匹配中...', function (data) {
                if (data.error) {
                    AlertFactory.error($scope, data.error, '匹配失败!');
                } else {
                    AlertFactory.success($scope, null, '匹配成功!');
                    initTree();
                }
            });
        };


        // 修改
        $scope.modify = function (id) {
            OrgModal.modify({scope: $scope, id: id}, function () {
                initTree();
            });

        };


        $scope.remove = function (menuId) {
            //删除
            art.dialog({
                content: '机构将删除或状态将变为注销状态，请确认！',
                ok: function () {
                    var result = OrgService.deleteByIds({ids: menuId}, function () {
                        initTree();
                        AlertFactory.success($scope, null, '状态更改成功!');
                    });
                    return true;
                },
                cancelVal: '关闭',
                cancel: true
            });

        };
        $scope.query = function (id) {
            OrgService.queryChildren({id: id}, function (data) {
                data = data.data || [];
                $scope.beans = data;
            });
        };


        $scope.ztreeOptions = OrgTree.dynamicTree(function (node) {
            $scope.beans.parentId = node.id;
            $scope.beans.parentName = node.name;
        });

        // 清除所选的城市
        $scope.clearRegion = function () {
            $scope.beans.businessAreaName = null;
            $scope.beans.businessArea = null;
        };

        // 初始化行政区域树
        $scope.regionOptions = {
            // 初识数据加载
            data: function (callback) {
                return CommonUtils.promise(function (defer) {
                    RegionService.tree({root: true}, function (data) {
                        data = data.data || [];
                        defer.resolve(data);
                    });
                });
            },
            multi: true,
            async: function (node, callback) {
                var pid = node.id;
                RegionService.tree({parentId: pid}, function (data) {
                    callback.call(node, data.data || []);
                });
            },
            click: function (data) {
                $scope.beans.businessArea = data.id;
                $scope.beans.businessAreaName = data.name;
            }
        };

        // 清除所选的机构
        $scope.clearOrg = function () {
            $scope.beans.parentId = null;
            $scope.beans.parentName = null;
        };

        // 机构类型
        OrgConstant.orgType(function (orgType) {
            $scope.orgTypes = orgType;
        });
        // 系统
        OrgConstant.bussitypeId(function (bussitypeId) {
            $scope.busiTypeIds = bussitypeId;
        });

        $scope.update = function () {
            var beans = $scope.beans;
            beans.status = beans.isStatus == true ? "1" : "0";
            beans.isOrg = beans.isOrgs == true ? 1 : 0;

            var promise = OrgService.update(beans);
            CommonUtils.loading(promise, '更新中...', function (data) {
                if (data && data.success === true) {
                    AlertFactory.success($scope, '更新成功!');
                } else {
                    AlertFactory.error($scope, '更新失败!');
                }
            });
        };

        var initTree = function () {
            Org.permissionTree($scope, 'treeDemo', function (treeNode) {
                $scope.id = treeNode.id;
                var promise = OrgService.get({id: treeNode.id});
                CommonUtils.loading(promise, '加载中...', function (data) {
                    data = data.data || [];
                    var beans = $scope.beans = data;
                    if (beans.status == "1") {
                        beans.isStatus = true;
                    }
                    if (beans.isOrg == 1) {
                        beans.isOrgs = true;
                    }
                });
            });
        };

        //执行方法
        initTree();

    });
})(window, angular, jQuery);