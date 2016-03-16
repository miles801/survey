/**
 * Created by liuxq on 2014-06-04 17:29:54.
 */
(function (window, angular, $) {
    var app = angular.module('knowledge.survey.category.list', [
        'knowledge.survey.category',
        'knowledge.survey.category.modal',
        'eccrm.angular',
        'eccrm.angular.ztree',
        'eccrm.angularstrap'
    ]);
    app.controller('SubjectCategoryListController', function ($scope, CommonUtils, SubjectCategoryService, ModalFactory, AlertFactory, SubjectCategoryTree, SubjectCategoryModal) {
        //初始化参数
        $scope.library = {};
        $scope.current = {};
        $scope.condition = {};
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {
                    start: this.start,
                    limit: this.limit
                });
                return CommonUtils.promise(function (defer) {
                    var promise = SubjectCategoryService.pageQueryChildren(param);
                    CommonUtils.loading(promise, '加载中...', function (data) {
                        defer.resolve(data.data || 0);
                        $scope.beans = data.data || {total: 0};
                    })
                });
            }
        };
        //查询数据
        $scope.query = function (id) {
            if (!id && !$scope.condition.id) {
                return;
            }
            $scope.condition.id = id || $scope.condition.id;
            $scope.pager.query();
        };
        //初始化ztree树（必须在文档加载后执行）

        var initTree = function () {
            SubjectCategoryTree.tree($scope, 'treeDemo', function (node) {
                $scope.query(node.id);
            });
        };
        initTree();

        $scope.remove = function (id) {
            //删除
            ModalFactory.remove($scope, function () {
                if (!id) {
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.id);
                    });
                    id = items.join(',');
                }
                var result = SubjectCategoryService.deleteByIds({ids: id});
                AlertFactory.handle($scope, result, function () {
                    initTree();
                    AlertFactory.success($scope, null, '删除成功!');
                });
            });
        };


        // 添加
        $scope.add = function () {
            SubjectCategoryModal.add(initTree);
        };
        // 修改
        $scope.modify = function (id) {
            SubjectCategoryModal.modify(id, initTree);
        };

        // 明细
        $scope.detail = function (id) {
            SubjectCategoryModal.view(id);
        };
    });

})(window, angular, jQuery);