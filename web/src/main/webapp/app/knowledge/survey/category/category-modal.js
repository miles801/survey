(function (angular, window) {
    var app = angular.module('knowledge.survey.category.modal', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.angular.ztree',
        'knowledge.survey.category'
    ]);

    app.factory('SubjectCategoryModal', function ($modal, CommonUtils, SubjectCategoryService, SubjectCategory, SubjectCategoryTree, AlertFactory, ModalFactory) {
            var common = function (pageType, id) {
                var modal = $modal({
                    backdrop: 'static',
                    template: CommonUtils.contextPathURL('app/knowledge/survey/category/template/libraryCategory_add.tpl.html')
                });

                var $scope = modal.$scope;
                $scope.beans = {};

                SubjectCategory.status(function (data) {
                    $scope.status = data;
                });
                // 上级
                $scope.ztreeOptions = SubjectCategoryTree.pick(function (node) {
                    $scope.beans.parentId = node.id;
                    $scope.beans.parentName = node.name;
                });

                // 清除选择的上级
                $scope.clear = function () {
                    $scope.beans.parentId = null;
                    $scope.beans.parentName = null;
                };
                // 根据ID加载
                var load = function (callback) {
                    if (!id) {
                        alert('没有获得ID!');
                        return false;
                    }
                    var promise = SubjectCategoryService.get({id: id}, function (data) {
                        $scope.beans = data.data || {};
                    });
                    CommonUtils.loading(promise, '加载中...', callback);
                };

                $scope.pageType = pageType || 'view';


                ModalFactory.afterShown(modal, function () {
                    if (pageType == 'modify') {
                        load();
                    } else if (pageType == 'view') {
                        load(function () {
                            $('.modal form').find('input,textarea,select,.btn').attr('disabled', 'disabled');
                        });
                    }
                });
                return $scope;

            };
            return {
                add: function (callback) {
                    var $scope = common('add');
                    var defaults = {
                        status: 'ACTIVE',
                        sequenceNo: 1
                    };
                    angular.extend($scope.beans, defaults);
                    $scope.save = function (createNew) {
                        var promise = SubjectCategoryService.save($scope.beans, function (data) {
                            if (data && data.success) {
                                if (createNew) {
                                    // 重置表单
                                    $scope.beans = angular.extend({}, $scope.beans, {
                                        name: '',
                                        description: '',
                                        sequenceNo: ++$scope.beans.sequenceNo
                                    });
                                    return;
                                }
                                if (angular.isFunction(callback)) {
                                    callback();
                                }
                                $scope.$hide();
                            }
                        });
                        CommonUtils.loading(promise, '保存中...');

                    };


                },
                modify: function (id, callback) {
                    var $scope = common('modify', id);
                    // 更新
                    $scope.update = function () {
                        var promise = SubjectCategoryService.update($scope.beans, function (data) {
                            if (data && data.success) {
                                if (angular.isFunction(callback)) {
                                    callback();
                                }
                                $scope.$hide();
                            }
                        });
                        CommonUtils.loading(promise, '更新中...');
                    };
                },
                view: function (id) {
                    common('view', id);
                }
            }
        }
    )
    ;
})(angular, window);