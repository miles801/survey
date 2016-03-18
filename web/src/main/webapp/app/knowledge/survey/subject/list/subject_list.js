(function (window, angular, $) {
    var app = angular.module('knowledge.survey.subject.list', [
        'knowledge.survey.subject',
        'knowledge.survey.category',
        'knowledge.survey.subject.modal',
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree'
    ]);
    app.controller('SubjectListCtrl', function ($scope, CommonUtils, Subject, SubjectCategoryTree, ModalFactory, SubjectModal, SubjectService) {

        $scope.condition = {};

        // 题目分类树
        var initTree = function () {
            SubjectCategoryTree.validTree($scope, 'treeDemo', function (node) {
                $scope.condition.categoryId = node.id;
                $scope.query();
            });
        };

        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                return CommonUtils.promise(function (defer) {
                    var promise = SubjectService.pageQuery(param);
                    CommonUtils.loading(promise, '加载中...', function (data) {
                        data = data.data || {total: 0};
                        defer.resolve(data);
                        $scope.beans = data;
                    });
                });
            }
        };
        // 查询数据
        $scope.query = function () {
            $scope.pager.query();
        };

        // 添加
        $scope.add = function () {
            SubjectModal.add(null, $scope.query);
        };

        // 修改
        $scope.modify = function (id, status) {
            SubjectModal.modify({id: id}, $scope.query);
        };

        // 明细
        $scope.view = function (id) {
            SubjectModal.view({id: id});
        };

        // 删除
        $scope.remove = function (id, status) {
            ModalFactory.remove($scope, function () {
                if (!id) {
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.id);
                    });
                    id = items.join(',');
                }
                var promise = SubjectService.deleteByIds({ids: id});
                CommonUtils.loading(promise, '删除中...', $scope.query);
            });
        };


        // 初始化题型
        Subject.type(function (data) {
            $scope.type = data;
            $scope.type.unshift({name: '全部'});
        });

        // 初始化状态
        Subject.status(function (data) {
            data.push({name: '全部'});
            $scope.status = data;
        });

        // 初始化树
        initTree();
    });

})(window, angular, jQuery);