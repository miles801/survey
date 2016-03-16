/**
 * Created by miles on 13-11-25.
 */
(function (angular, $) {
    var app = angular.module('eccrm.base.region.list', [
        'eccrm.base.region',
        'eccrm.base.region.modal'
    ]);
    app.controller('RegionListController', function ($scope, RegionService, RegionModal, ModalFactory, AlertFactory, CommonUtils) {
        $scope.condition = {};
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {
                    start: $scope.pager.start || 0,
                    limit: $scope.pager.limit || 10
                }, $scope.condition);
                return CommonUtils.promise(function (defer) {
                    var promise = RegionService.query(param);
                    CommonUtils.loading(promise, '加载中...', function (data) {
                        data = data.data || {total: 0, data: []};
                        $scope.regions = data;
                        defer.resolve(data);
                    });
                });
            }
        };

        // 新增
        $scope.add = function () {
            RegionModal.add({scope: $scope}, initTree);
        };

        // 修改
        $scope.modify = function (id) {
            RegionModal.modify({scope: $scope, id: id}, initTree);
        };

        // 查看
        $scope.view = function (id) {
            RegionModal.view({scope: $scope, id: id});
        };

        // 删除
        $scope.deleteOrCancel = function (id) {
            ModalFactory.remove($scope, function () {
                if (!id) {
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.id);
                    });
                    if (!items || items.length < 1) {
                        AlertFactory.error($scope, '请选择至少一条数据后再进行删除/注销操作!', '错误操作!');
                        return;
                    }
                    id = items.join(',');
                }
                var result = RegionService.deleteByIds({ids: id});
                CommonUtils.loading(result, '删除中...', initTree);
            });
        };


        //查询数据
        $scope.query = function () {
            $scope.pager.query();
        };

        //初始化ztree树（必须在文档加载后执行）
        var setting = {
            view: {showIcon: false},
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    $scope.condition.parentId = treeNode.id;
                    $scope.current = treeNode;
                    $scope.query();
                },
                onExpand: function (event, treeId, treeNode) {
                    var obj = this.getZTreeObj(treeId);
                    if (!(treeNode.children && treeNode.children.length > 0)) {
                        RegionService.tree({parentId: treeNode.id}, function (data) {
                            // 过滤数据，当层级达到市级时，不再展开下级节点
                            var children = data.data || [];
                            angular.forEach(children, function (v) {
                                if (v.type === 2) {
                                    v.isParent = false;
                                }
                            });
                            treeNode.children = children;
                            obj.refresh();
                        });
                    }
                }
            }
        };
        var initTree = function () {
            RegionService.tree({root: true}, function (data) {
                data = data.data || [];
                //默认加载第一个菜单的数据到列表页面
                if (data && data.length > 0) {
                    $scope.condition.path = data[0].path;
                } else {
                    delete $scope.condition.path;
                }
                if (!data || data.length == 0) {
                    data = [
                        {name: '行政区域'}
                    ];
                }
                var tree = $.fn.zTree.init($("#treeDemo"), setting, data);
                $scope.query();
                // 展开第一级菜单（手动触发expand事件）
                tree.expandNode(tree.getNodes()[0], true, true, true, true);
            })
        };
        initTree();
    })
})(angular, jQuery);