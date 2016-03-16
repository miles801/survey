/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (angular, $) {
    var app = angular.module("base.org", [
        'ngResource',
        'eccrm.angular'
    ]);

    app.service('OrgService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/base/org/:method'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 将指定的一组组织机构从临时机构表转移到当前系统的机构表中
            // 必须参数：
            //      tmpOrgId:临时组织机构id，多个值使用逗号进行分隔
            // 可选参数：
            //      orgId：要转移到哪个机构
            transformFromTmpOrg: {method: 'POST', params: {method: 'transformFromTmpOrg'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            //根据id查询机构员工视图信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},
            addPostion: {method: 'GET', params: {method: 'addPostion', id: '@id'}, isArray: false},

            // 查询当前id下的所有子节点（包括自己）
            // 返回分页对象：{data:{total,data:[{}]}}
            allChildren: {method: 'GET', params: {method: 'children', id: '@id'}, isArray: false},

            // 维护时用：查询所有的菜单，并组装成树
            // 返回：{data : [{id,name,children:[]}]}
            tree: {method: 'POST', params: {method: 'tree'}, isArray: false},

            //分页查询，返回{total:,data:[{},{}]}
            query: {method: 'POST', params: {method: 'query', limit: '@limit', start: '@start'}, isArray: false},

            // 查询直接子节点
            // 可选参数：id(当前节点id，即查询该id下面的直接子节点）
            // 如果没有传递id参数，则默认查询根节点
            queryChildren: {method: 'GET', params: {method: 'queryChildren'}, isArray: false},

            // 查询直接子节点
            // 注意：该方法只会查询状态为有效的数据
            // 可选参数：id(当前节点id，即查询该id下面的直接子节点）
            // 如果没有传递id参数，则默认查询根节点
            queryValidChildren: {method: 'GET', params: {method: 'queryValidChildren'}, isArray: false},

            // 带权限的分页查询
            // 可选参数：Organization所有属性
            // 注意：该方法使用的是GET请求方式，所以高级查询参数在传递之前都需要进行两次编码（防止中文乱码的问题）
            permissionPageQuery: {
                method: 'GET',
                params: {method: 'permissionPageQuery', limit: '@limit', start: '@start'},
                isArray: false
            },

            // 查询当前登录用户被授权的所有系统
            // 返回的数据集合中只包含busiTypeId、busiTypeName两个字段（即系统信息）
            permissionPersonalParams: {method: 'GET', params: {method: 'permissionPersonalParams'}, isArray: false},

            // 带权限的高级查询
            // 可选参数：Organization所有属性
            // 注意：该方法使用的是GET请求方式，所以高级查询参数在传递之前都需要进行两次编码（防止中文乱码的问题）
            permissionQuery: {method: 'GET', params: {method: 'permissionQuery'}, isArray: false},

            // 带权限的根组织机构的查询
            // 返回的数据中，只有根
            permissionRootQuery: {method: 'GET', params: {method: 'permissionRootQuery'}, isArray: false},

            //分页查询，返回{total:,data:[{},{}]}
            queryAll: {method: 'POST', params: {method: 'queryAll', limit: '@limit', start: '@start'}, isArray: false},

            //查询有效数据
            queryEffective: {method: 'POST', params: {method: 'trees'}, isArray: false},
            //根据id字符串（使用逗号分隔多个值），删除对应的机构员工视图，成功则返回{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });
    // 废弃（请参考Org）
    app.service("OrgTree", function (OrgService, $q, CommonUtils) {
        return {
            init: function (v) {
                var defer = $q.defer();
                OrgService.queryEffective(function (data) {
                    defer.resolve(data.data);
                });
                return defer.promise;
            },
            // 获得动态组织机构树的配置（适用于ztree-single指令的配置）
            // 参数：
            // onClick[function]：点击节点时需要触发的回调，该函数接收一个被选中的节点
            dynamicTree: function (onClick) {
                return {
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = OrgService.queryChildren();
                            CommonUtils.loading(promise, '加载机构树...', function (data) {
                                defer.resolve(data.data || []);
                            });
                        });
                    },
                    async: function (treeNode, callback) {
                        return CommonUtils.promise(function (defer) {
                            var promise = OrgService.queryChildren({id: treeNode.id});
                            CommonUtils.loading(promise, '加载机构树...', function (data) {
                                callback(data.data || []);
                            });
                        });
                    },
                    click: onClick
                }
            },

            // 动态获取组织机构树，一般用于左侧树显示
            // 参数：
            //   onClick[function]：点击时的回调，接收参数为(事件对象，树id,当前节点)
            dynamicTree2: function (onClick) {
                return {
                    view: {showIcon: false},
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: {
                        onExpand: function (event, treeId, treeNode) {
                            // 展示时动态加载数据
                            var obj = this.getZTreeObj(treeId);
                            if (!(treeNode.children && treeNode.children.length > 0)) {
                                var promise = OrgService.queryChildren({id: treeNode.id});
                                CommonUtils.loading(promise, '加载机构数据...', function (data) {
                                    treeNode.children = data.data || [];
                                    // 刷新树
                                    obj.refresh();
                                });
                            }
                        },
                        onClick: onClick
                    }
                }
            }
        };
    });

    app.service("OrgAllTree", function (OrgService, $q) {
        var i = 0;
        return {
            init: function (v) {
                var defer = $q.defer();
                OrgService.tree(function (data) {
                    defer.resolve(data);
                });
                return defer.promise;
            }
        };
    });

    app.service('MenuConstant', function (CommonUtils, Parameter) {
        var status;
        var types;
        return {
            types: function (callback) {
                if (types === undefined) {
                    Parameter.systemItems('SP_MENU_TYPE', function (data) {
                        types = data.data || [];
                        callback(types);
                    });
                } else {
                    callback(types);
                }
            },
            status: function (callback) {
                if (status === undefined) {
                    Parameter.systemItems('SP_COMMON_STATE', function (data) {
                        status = data.data || [];
                        callback(status);
                    });
                } else {
                    callback(status);
                }
            },
            defaults: {
                show: true,
                type: "2",//功能操作
                order: 0,
                status: "ACTIVE",//正常状态
                creatorId: CommonUtils.loginContext().id,
                creatorName: CommonUtils.loginContext().username,
                createdDatetime: new Date().getTime()
            }
        }
    });

    // 提供组织机构动态树的功能
    app.service('Org', function (CommonUtils, OrgService) {
        return {
            // 获得动态组织机构树的配置（适用于ztree-single指令的配置）
            // Notice: 该方法只会返回状态为有效的数据
            // 参数：
            // onClick[function]：点击节点时需要触发的回调，该函数接收一个被选中的节点
            pick: function (onClick) {
                return {
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = OrgService.queryValidChildren();
                            CommonUtils.loading(promise, '加载机构树...', function (data) {
                                defer.resolve(data.data || []);
                            });
                        });
                    },
                    async: function (treeNode, callback) {
                        return CommonUtils.promise(function (defer) {
                            var promise = OrgService.queryValidChildren({id: treeNode.id});
                            CommonUtils.loading(promise, '加载机构树...', function (data) {
                                callback(data.data || []);
                            });
                        });
                    },
                    click: onClick
                }
            },
            // 获得带权限的动态组织机构树的配置（适用于ztree-single指令的配置）
            // Notice: 该方法只会返回状态为有效且被授权的数据
            // 参数：
            // onClick[function]：点击节点时需要触发的回调，该函数接收一个被选中的节点
            permissionPick: function (onClick) {
                return {
                    data: function () {
                        return CommonUtils.promise(function (defer) {
                            var promise = OrgService.permissionRootQuery();
                            CommonUtils.loading(promise, '加载机构树...', function (data) {
                                data = data.data || [];
                                defer.resolve(data || []);
                            });
                        });
                    },
                    async: function (treeNode, callback) {
                        return CommonUtils.promise(function (defer) {
                            var promise = OrgService.queryValidChildren({id: treeNode.id});
                            CommonUtils.loading(promise, '加载机构树...', function (data) {
                                callback(data.data || []);
                            });
                        });
                    },
                    click: onClick
                }
            },


            // 用于在指定地方动态展示一个动态的组织机构树
            // 必须参数：
            //  scope:
            //  id：树要显示的地方
            // 可选参数：
            //  onClick：选中节点后要触发的函数，接收一个node对象
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
                                var promise = OrgService.queryValidChildren({id: treeNode.id});
                                CommonUtils.loading(promise, '加载机构数据...', function (data) {
                                    treeNode.children = data.data || [];
                                    treeNode['_hasQuery'] = true;
                                    // 刷新树
                                    obj.refresh();
                                });
                            }
                        },
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

                var promise = OrgService.queryValidChildren();
                var defer = CommonUtils.defer();
                CommonUtils.loading(promise, '加载机构树...', function (data) {
                    var treeObj = $.fn.zTree.init($("#" + id), setting, data.data || []);
                    defer.resolve(treeObj);
                });
                return defer.promise;
            },

            // 用于在指定地方动态展示一棵带数据权限的动态组织机构树
            // 必须参数：
            //  scope:
            //  id：树要显示的地方
            // 可选参数：
            //  onClick：选中节点后要触发的函数，接收一个node对象
            permissionTree: function (scope, id, onClick) {
                var setting = {
                    view: {showIcon: false},
                    data: {
                        simpleData: {
                            enable: true,
                            pIdKey: 'parentId'
                        }
                    },
                    callback: {
                        onExpand: function (event, treeId, treeNode) {
                            if (treeNode['_hasQuery'] == undefined) {
                                // 展示时动态加载数据
                                var obj = this.getZTreeObj(treeId);
                                var promise = OrgService.queryValidChildren({id: treeNode.id});
                                CommonUtils.loading(promise, '加载机构数据...', function (data) {
                                    treeNode.children = data.data || [];
                                    treeNode['_hasQuery'] = true;
                                    // 刷新树
                                    obj.refresh();
                                });
                            }
                        },
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

                setting = angular.extend(scope.setting ? scope.setting : {}, setting);

                var promise = OrgService.permissionRootQuery();
                var defer = CommonUtils.defer();
                CommonUtils.loading(promise, '加载机构树...', function (data) {
                    data = data.data || [];
                    var treeObj = $.fn.zTree.init($("#" + id), setting, data);
                    defer.resolve(treeObj);
                });
                return defer.promise;
            },


            // 用于在指定地方动态展示一棵带数据权限的动态组织机构树（多选部门）
            // 必须参数：
            //  scope:
            //  id：树要显示的地方
            permissionCheckTree: function (scope, id) {
                var setting = {
                    view: {showIcon: false},
                    data: {
                        simpleData: {
                            enable: true,
                            pIdKey: 'parentId'
                        }
                    }
                };

                setting = angular.extend(scope.setting ? scope.setting : {}, setting);

                setting.callback = setting.callback ? setting.callback : {};
                setting.callback.onExpand = function (event, treeId, treeNode) {
                    if (treeNode['_hasQuery'] == undefined) {
                        // 展示时动态加载数据
                        var obj = this.getZTreeObj(treeId);
                        var promise = OrgService.queryValidChildren({id: treeNode.id});
                        CommonUtils.loading(promise, '加载机构数据...', function (data) {
                            treeNode.children = data.data || [];
                            treeNode['_hasQuery'] = true;
                            // 刷新树
                            obj.refresh();
                        });
                    }
                };

                var promise = OrgService.permissionRootQuery();
                var defer = CommonUtils.defer();
                CommonUtils.loading(promise, '加载机构树...', function (data) {
                    data = data.data || [];
                    var treeObj = $.fn.zTree.init($("#" + id), setting, data);
                    defer.resolve(treeObj);
                });
                return defer.promise;
            },


            // 用于在指定地方动态展示一个动态的组织机构树（一般用于维护界面）
            // 该方法域tree方法实现方式一样，只是在调用的后台方法上有区别（修改时注意同步修改）
            // 必须参数：
            //  scope:
            //  id：树要显示的地方
            // 可选参数：
            //  onClick：选中节点后要触发的函数，接收一个node对象
            allTree: function (scope, id, onClick) {
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
                                var promise = OrgService.queryChildren({id: treeNode.id});
                                CommonUtils.loading(promise, '加载机构数据...', function (data) {
                                    treeNode.children = data.data || [];
                                    treeNode['_hasQuery'] = true;
                                    // 刷新树
                                    obj.refresh();
                                });
                            }
                        },
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

                var promise = OrgService.queryChildren();
                var defer = CommonUtils.defer();
                CommonUtils.loading(promise, '加载机构树...', function (data) {
                    var treeObj = $.fn.zTree.init($("#" + id), setting, data.data || []);
                    defer.resolve(treeObj);
                });
                return defer.promise;
            }
        };
    });


})(angular, jQuery);
