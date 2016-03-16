/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (angular, $) {
    var app = angular.module("eccrm.position.position", [
        'ngResource',
        'eccrm.base.param'
    ]);
    app.service('PositionService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/position/:method/:id'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            //更新
            update: {method: 'PUT', params: {method: 'update'}, isArray: false},

            //根据id查询岗位管理信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},
            exists: {method: 'GET', params: {method: 'exists', name: '@name'}, isArray: false},
            queryByOrgId: {method: 'queryByOrgId', params: {id: '@id'}, isArray: false},

            queryAll: {
                method: 'POST',
                params: {
                    method: 'query',
                    id: '@id',
                    start: '@start',
                    limit: '@limit',
                    orderBy: '@orderBy',
                    reverse: '@reverse'
                },
                isArray: false
            },
            query: {
                method: 'POST',
                params: {method: 'query', start: '@start', limit: '@limit', orderBy: '@orderBy', reverse: '@reverse'},
                isArray: false
            },

            // 查询某一个分类下所有有效的岗位
            // 必须参数：
            //  type：岗位所属分类的id
            // 返回数据：
            //  {success:true,data:[]}
            queryValidByType: {method: 'GET', params: {method: 'queryValidByType', type: '@type'}, isArray: false},

            //根据id字符串（使用逗号分隔多个值），删除对应的岗位管理，成功则返回{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });
    app.service('ClassifyService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/position/classify/:method'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            //根据id查询岗位管理信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},
            queryByOrgId: {method: 'queryByOrgId', params: {id: '@id'}, isArray: false},
            queryAll: {
                method: 'POST',
                params: {
                    method: 'query',
                    id: '@id',
                    start: '@start',
                    limit: '@limit',
                    orderBy: '@orderBy',
                    reverse: '@reverse'
                },
                isArray: false
            },
            query: {
                method: 'POST',
                params: {method: 'query', start: '@start', limit: '@limit', orderBy: '@orderBy', reverse: '@reverse'},
                isArray: false
            },

            //根据id字符串（使用逗号分隔多个值），删除对应的岗位管理，成功则返回{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 查询分类，并组装成树形，只查询状态为有效的数据
            tree: {method: 'GET', params: {method: 'tree'}, isArray: false}
        })
    });

    app.service('PositionTree', function (ClassifyService, PositionService, CommonUtils) {
        return {
            // 用于在指定地方动态展示一个岗位树
            // 必须参数：
            //  scope:
            //  id：树要显示的地方
            // 可选参数：
            //  onClick：选中节点后要触发的函数，接收一个node对象，仅在点击到岗位时有效
            tree: function (scope, id, onClick) {
                var setting = {
                    view: {showIcon: false},
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: {
                        onExpand: function (event, treeId, treeNode) {
                            if (treeNode['_hasQuery'] == undefined) {
                                // 展示时动态加载数据
                                var obj = this.getZTreeObj(treeId);
                                var promise = PositionService.queryValidByType({type: treeNode.id});
                                CommonUtils.loading(promise, '加载岗位数据...', function (data) {
                                    treeNode.children = data.data || [];
                                    treeNode['_hasQuery'] = true;
                                    // 刷新树
                                    obj.refresh();
                                });
                            }
                        },
                        onClick: function (event, treeId, treeNode) {
                            if (treeNode.isPositionType == true) {
                                return false;
                            }
                            var obj = this.getZTreeObj(treeId);
                            if (angular.isFunction(onClick)) {
                                scope.$apply(function () {
                                    onClick.call(obj, treeNode);
                                });
                            }
                        }
                    }
                };

                var promise = ClassifyService.tree();
                var defer = CommonUtils.defer();
                CommonUtils.loading(promise, '加载岗位分类...', function (data) {
                    data = data.data || [];
                    angular.forEach(data, function (o) {
                        o.click = '';
                        o.isParent = o.isParent || o.hasPosition;
                        o.isPositionType = true;
                    });
                    var treeObj = $.fn.zTree.init($("#" + id), setting, data || []);
                    defer.resolve(treeObj);
                });
                return defer.promise;
            }
        };
    });
    app.service("CompileTree", function (ClassifyService, $q) {
        return {
            init: function (v) {
                var context = this;
                if (v) {
                    var default_data = {
                        orderBy: 'seqNo',
                        reverse: 'false',
                        status: "1"
                    };
                } else {
                    var default_data = {
                        orderBy: 'seqNo',
                        reverse: 'false'
                    };
                }
                var defer = $q.defer();
                ClassifyService.tree(function (data) {
                    data = data.data ||[];
                    defer.resolve(data);
                });
                return defer.promise;
            }
        };
    });
    app.service('positionConstant', ['Parameter', function (Parameter) {
        return {
            // 系统
            busiType: function (callback) {
                Parameter.businessItems('BP_YETAI', function (data) {
                    callback(data.data || []);
                });
            },
            // 岗位类型
            roleType: function (callback) {
                Parameter.systemItems('SYS_GWLX', function (data) {
                    callback(data.data || []);
                });
            },
            // 状态
            status: function (callback) {
                Parameter.systemItems('SP_COMMON_STATE', function (data) {
                    callback(data.data || []);
                });
            }

        }
    }]);
})(angular, jQuery);
