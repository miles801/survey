/**
 * 需要依赖：
 * parameter.js
 * ztree.js
 */
(function (angular) {
    var app = angular.module("knowledge.survey.category", [
        'ngResource',
        'eccrm.angular',
        'eccrm.base.param'
    ]);
    app.service('SubjectCategoryService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/survey/category/:method'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            //根据id查询新增题目信息
            get: {method: 'GET', params: {id: '@id', method: 'get'}, isArray: false},

            //根据id字符串（使用逗号分隔多个值），删除对应的新增题目，成功则返回{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            query: {method: 'POST', params: {method: 'query'}, isArray: false},

            // 分页查询
            pageQuery: {
                method: 'POST',
                params: {method: 'pageQuery', start: '@start', limit: '@limit'},
                isArray: false
            },
            // 分页查询孩子节点（所有孩子节点，包括自身和隔代节点）
            // 必须参数：
            //  id：要从哪个节点开始查
            pageQueryChildren: {
                method: 'POST',
                params: {method: 'pageQueryChildren', id: '@id', start: '@start', limit: '@limit'},
                isArray: false
            },

            // 返回整棵树，包含所有状态的数据，一般用于维护界面
            tree: {method: 'GET', params: {method: 'tree'}, isArray: false},

            // 返回有效树，一般用于选择界面
            validTree: {method: 'GET', params: {method: 'validTree'}, isArray: false}
        })
    });

    app.service('SubjectCategory', function (CommonUtils, ParameterLoader) {
        return {
            status: function (callback) {
                ParameterLoader.loadSysParam('SP_COMMON_STATE', callback, '加载数据字典...');
            }
        }
    });

    // 题库分类树
    app.service("SubjectCategoryTree", function (SubjectCategoryService, CommonUtils) {
        return {
            /**
             * 题库分类树，用于ztree-single
             */
            pick: function (options) {
                return {
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = SubjectCategoryService.validTree();
                            CommonUtils.loading(promise, '数据加载中...', function (data) {
                                defer.resolve(data.data || []);
                            });
                        });
                    },
                    click: angular.isFunction(options) ? options : options.onClick
                };
            },
            validTree: function (scope, id, onClick) {
                var setting = {
                    view: {showIcon: false},
                    data: {
                        simpleData: {
                            enable: true,
                            pIdKey: 'parentId'
                        }
                    },
                    callback: {
                        onClick: function (event, treeId, treeNode) {
                            var obj = this.getZTreeObj(treeId);
                            if (angular.isFunction(onClick)) {
                                scope.$apply(function () {
                                    onClick.call(obj, treeNode);
                                });
                            }
                        }
                    }
                };

                var promise = SubjectCategoryService.validTree();
                var defer = CommonUtils.defer();
                CommonUtils.loading(promise, '数据加载中...', function (data) {
                    var treeObj = $.fn.zTree.init($("#" + id), setting, data.data || []);
                    defer.resolve(treeObj);
                });
                return defer.promise;
            },
            tree: function (scope, id, onClick) {
                var setting = {
                    view: {showIcon: false},
                    data: {
                        simpleData: {
                            enable: true,
                            pIdKey: 'parentId'
                        }
                    },
                    callback: {
                        onClick: function (event, treeId, treeNode) {
                            var obj = this.getZTreeObj(treeId);
                            if (angular.isFunction(onClick)) {
                                scope.$apply(function () {
                                    onClick.call(obj, treeNode);
                                });
                            }
                        }
                    }
                };

                var promise = SubjectCategoryService.tree();
                var defer = CommonUtils.defer();
                CommonUtils.loading(promise, '数据加载中...', function (data) {
                    var treeObj = $.fn.zTree.init($("#" + id), setting, data.data || []);
                    defer.resolve(treeObj);
                });
                return defer.promise;
            }
        };
    });

})(angular);