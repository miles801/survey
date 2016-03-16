/**
 * Created by miles on 13-11-25.
 */
(function (angular, $) {
    var app = angular.module('eccrm.base.menu.list', [
        'eccrm.base.menu',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('MenuListController', function ($scope, MenuService, MenuUrl, CommonUtils, ModalFactory) {
        //定义变量
        var treeObj;        // ztree对象
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {id: $scope.current.id}, {start: this.start, limit: this.limit});
                if (!param.id) return;
                return CommonUtils.promise(function (defer) {
                    CommonUtils.loading(defer, '加载菜单...');
                    MenuService.allChildren(param, function (data) {
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
                    //防止重复点击
                    if ($scope.current && $scope.current.id == treeNode.id) return;
                    $scope.pager.start = 0;
                    $scope.pager.currentPage = 1;
                    $scope.query(treeNode.id);
                    event.preventDefault();
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
        var initTree = function () {
            // 记录当前被选中的节点
            var node = treeObj && treeObj.getSelectedNodes()[0];

            var result = MenuService.tree(function (data) {
                data = data.data || [];
                if (data[0] || node) {
                    $scope.query((node && node.id) || data[0].id);//默认加载第一个菜单的数据到列表页面
                } else {
                    data = [
                        {name: '系统功能'}
                    ];
                }
                // 移除icon属性，防止与zTree的icon属性冲突
                CommonUtils.deleteAttr(data, "icon");

                // 初始化菜单树
                treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);

                // 回显之前的节点
                if (node) {
                    treeObj.expandNode(node, true, true, true);
                    treeObj.selectNode(node);
                    $scope.query(node.id);
                    // 展开上级
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
                }
            });
            CommonUtils.loading(result, '加载菜单树...');
        };
        $scope.remove = function (menuId, status) {
            var key;
            if (status === 'INACTIVE') {
                key = '删除当前节点时，<span style="color:red;">会级联删除所有的子菜单</span>，请确认该操作!'
            } else if (status === 'ACTIVE') {
                key = '注销当前节点时，<span style="color:red;">会级联注销所有的子菜单</span>，请确认该操作!'
            }
            //删除
            ModalFactory.confirm({
                scope: $scope,
                content: key
            }, function () {
                var promise = MenuService.deleteByIds({ids: menuId});
                CommonUtils.loading(promise, '删除中...', initTree);
            });
        };
        $scope.add = function () {
            CommonUtils.addTab({
                title: '新建菜单',
                url: '/base/menu/add',
                onUpdate: initTree
            });
        };
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新菜单',
                url: '/base/menu/modify?id=' + id,
                onUpdate: initTree
            });
        };
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '菜单明细',
                url: '/base/menu/detail?id=' + id
            });
        };

        //执行方法
        initTree();
    })
})(angular, jQuery);