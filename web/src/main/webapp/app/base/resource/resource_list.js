/**
 * Created by miles on 13-11-25.
 */
(function (angular, $) {
    var app = angular.module('eccrm.base.resource.list', [
        'eccrm.base.resource',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('ResourceListCtrl', function ($scope, ResourceService, CommonUtils, ModalFactory) {
        //定义变量
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {id: $scope.current.id}, {
                    start: $scope.pager.start,
                    limit: $scope.pager.limit
                });
                if (!param.id) return;
                return CommonUtils.promise(function (defer) {
                    CommonUtils.loading(defer, '加载资源列表...');
                    ResourceService.allChildren(param, function (data) {
                        data = data.data;
                        $scope.menus = data;
                        defer.resolve(data);
                    });
                });
            }
        };
        $scope.current = {};
        var setting = {
            view: {showIcon: false},
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                }
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    event.preventDefault();
                    //防止重复点击
                    if ($scope.current && $scope.current.id == treeNode.id) return;
                    $scope.pager.start = 0;
                    $scope.pager.currentPage = 1;
                    $scope.query(treeNode.id);
                }
            }
        };

        //定义方法
        $scope.query = function (id) {
            if (id === undefined) {
                return;
            }
            $scope.current.id = id;
            CommonUtils.delay($scope.pager.query, 0);
        };
        var treeObj;
        var initTree = function () {
            var node = treeObj && treeObj.getSelectedNodes()[0];
            var promise = ResourceService.tree(function (data) {
                data = data.data || [{name: '系统资源'}];
                //初始化菜单
                treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);

                // 恢复之前选中的节点
                if (node) {
                    treeObj.expandNode(node, true, true, true);
                    treeObj.selectNode(node);
                    $scope.query(node.id);
                    var repeatExpand = function (pid) {
                        if (pid) {
                            var parentNode = treeObj.getNodeByTId(pid);
                            if (parentNode) {
                                treeObj.expandNode(parentNode, true, true, true);
                                arguments.callee(parentNode.parentTId);
                            }
                        }
                    };
                    repeatExpand(node.parentTId);
                } else {
                    // expand & select first node
                    var firstNode = treeObj.getNodes()[0];
                    $scope.query(firstNode.id);
                    treeObj.expandNode(firstNode, true, true, true);
                    treeObj.selectNode(firstNode);
                }
            });
            CommonUtils.loading(promise, '加载资源树..');
        };
        $scope.remove = function (menuId) {
            //删除
            ModalFactory.remove($scope, function () {
                var promise = ResourceService.deleteByIds({ids: menuId}, initTree);
                CommonUtils.loading(promise, '删除中...');
            });
        };

        $scope.add = function () {
            CommonUtils.addTab({
                title: '新建资源',
                url: '/base/resource/add',
                onUpdate: initTree
            });
        };

        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新资源',
                url: "/base/resource/modify?id=" + id,
                onUpdate: initTree
            });
        };
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '资源明细',
                url: "base/resource/detail?id=" + id
            });
        };
        //执行方法
        initTree();
    })
})(angular, jQuery);