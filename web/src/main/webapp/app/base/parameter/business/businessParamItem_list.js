/**
 * Created by miles on 13-11-25.
 */
(function (angular, $) {
    var app = angular.module('eccrm.base.parameter.item.list', [
        'eccrm.base.param',
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.directive.ztree',
        'eccrm.base.parameter.modal'
    ]);
    app.controller('ParameterItemListController', function ($scope, Debounce, ModalFactory, BusinessParamItem, BusinessParamType, AlertFactory, BusinessParamItemModal, $q, CommonUtils) {
        $scope.condition = {};
        var ztreeObj;
        $scope.pager = {
            fetch: function (callback) {
                var param = angular.extend({}, {
                    start: $scope.pager.start || 0,
                    limit: $scope.pager.limit || 10
                }, $scope.condition);
                var defer = $q.defer();
                var result = BusinessParamItem.query(param);
                AlertFactory.handle($scope, result, function (data) {
                    $scope.parameters = data.data;
                    defer.resolve(data.data);
                });
                return defer.promise;
            }
        };

        //查询数据
        $scope.queryItems = function () {
            Debounce.delay($scope.pager.query, 0);
        };

        $scope.cancelOrDelete = function (id) {
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
                var promise = BusinessParamItem.deleteByIds({ids: id});
                CommonUtils.loading(promise, '删除中...', $scope.initTree);
            });
        };

        //初始化ztree树（必须在文档加载后执行）
        var setting = {
            view: {showIcon: false},
            data: {
                simpleData: {enable: true}
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    if (treeNode === $scope.current) return;
                    if (treeNode.isParent && treeNode.open === false) {
                        ztreeObj.expandNode(treeNode, true, true, true);
                    }
                    if (!treeNode.code) return;
                    $scope.$apply(function () {
                        $scope.condition.type = treeNode.code;
                        $scope.current = treeNode;
                        $scope.queryItems();
                    });
                }
            }
        };
        var initTree = $scope.initTree = function () {
            var result = BusinessParamType.queryOther();
            AlertFactory.handle($scope, result, function (data) {
                data = data.data || [];
                var tree = [
                    {name: '业务参数', open: true}
                ];
                if (data && data.length > 0) {
                    $scope.condition.type = data[0].code;
                    tree[0].children = data;
                    $scope.queryItems();
                }
                ztreeObj = $.fn.zTree.init($("#treeDemo"), setting, tree);
            })
        };
        initTree();

        //新增
        $scope.add = function () {
            if (!$scope.condition.type) {
                AlertFactory.error('请选择参数类型!');
                return false;
            }
            BusinessParamItemModal.add({scope: $scope, parameterType: $scope.current}, $scope.pager.load);
        };

        //更新
        $scope.modify = function (id) {
            BusinessParamItemModal.modify({scope: $scope, id: id}, $scope.pager.load);
        };

        //查看
        $scope.view = function (id) {
            BusinessParamItemModal.view({scope: $scope, id: id});
        };

    })
})(angular, jQuery);