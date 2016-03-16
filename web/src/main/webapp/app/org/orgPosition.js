/**
 * 机构岗位
 * <p>
 * 需要依赖
 *  1、org.js
 *  2、eccrm-ztree-all.js
 * </p>
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (angular, $) {
    var app = angular.module("base.org.position", [
        'ngResource',
        'eccrm.angular',
        'eccrm.angularstrap',
        'base.org',
        'eccrm.angular.ztree'
    ]);


    app.service('OrgPositionService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/wanda/orgPosition/:method'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},
            //根据id查询机构员工视图信息
            get: {method: 'GET', params: {id: '@id'}, isArray: false},

            // 根据组织机构id查询对应的所有的岗位信息
            // 必须参数：orgId
            // 返回：{success:true,data:{}}
            // data数据模型：{id,name}
            queryByOrgId: {method: 'GET', params: {method: 'queryPositionByOrgId', id: '@orgId'}, isArray: false},


            // 根据组织机构id分页查询对应的所有的岗位信息
            // 必须参数：orgId
            // 返回：{success:true,data:{total:0,data:[]}}
            // data数据模型：分页对象
            pageQueryByOrgId: {method: 'GET', params: {method: 'pageQueryByOrgId', orgId: '@orgId'}, isArray: false},

            queryByOrgIdforTree: {method: 'GET', params: {method: 'queryByOrgIdforTree', id: '@id'}, isArray: false},
            queryByOrgIdData: {method: 'GET', params: {method: 'queryByOrgIdData', id: '@id'}, isArray: false},
            //分页查询，返回{total:,data:[{},{}]}
            query: {method: 'POST', params: {method: 'query', limit: '@limit', start: '@start'}, isArray: false},

            //根据id字符串（使用逗号分隔多个值），删除对应的机构员工视图，成功则返回{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 查询具有岗位的所有岗位的ID
            // 返回:{success:true,data:[ ]}
            // data的数据模型：岗位id数组
            whoHasPosition: {method: 'GET', params: {method: 'whoHasPosition'}, isArray: false},

            deleteByRoleId: {method: 'POST', params: {method: 'deleteByRoleId'}, isArray: false}
        })
    });


    // 主要提供组织机构岗位的相关功能
    // 例如：组织机构岗位树
    app.service('OrgPosition', function (CommonUtils, OrgService, OrgPositionService, ZtreeModal) {
            var dynamicTree = function (options) {
                var defaults = {
                    scope: null,         // 必须项
                    id: null,            // 必须项
                    callback: null,      // 必须项，点击节点后的回调
                    permission: false    // 可选项，是否使用数据权限进行过滤
                };
                options = angular.extend({}, defaults, options);
                var id = options.id;
                if (!id) {
                    CommonUtils.artDialog('错误', '没有获得要显示树的容器的ID!');
                    return false;
                }
                var hasPositionOrgDefer = CommonUtils.defer();
                var hasPositionOrgIds;
                // 给数组中的每个元素都设置_hasChild或者_hasPosition属性
                var wrapData = function (data) {
                    angular.forEach(data || [], function (o) {
                        // 设置为组织机构的标识
                        o['_isOrg'] = true;
                        o.icon = CommonUtils.contextPathURL('/vendor/zTree/img/diy/1_close.png');
                        // 设置标识，标识有孩子节点
                        if (o.isParent) {
                            o['_hasChild'] = true;
                        }
                        // 设置标识，标识有岗位
                        if ($.inArray(o.id, hasPositionOrgIds || []) > -1) {
                            o.isParent = true;
                            o['_hasPosition'] = true;
                        }
                    });
                };
                var setting = {
                    view: {showIcon: true},
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
                                treeNode.children = treeNode.children || [];

                                // 加载孩子节点
                                var orgDefer = CommonUtils.defer();
                                if (treeNode['_hasChild']) {
                                    var promise = OrgService.queryValidChildren({id: treeNode.id});
                                    CommonUtils.loading(promise, '加载机构数据...', function (data) {
                                        data = data.data || [];
                                        wrapData(data || []);
                                        var children = treeNode.children;
                                        angular.forEach(data, function (o) {
                                            // 如果已经存在则不需要再添加
                                            for (var i = 0; i < children.length; i++) {
                                                if (o.id == children[i].id) {
                                                    return;
                                                }
                                            }
                                            children.push(o);
                                        });
                                        treeNode['_hasQuery'] = true;
                                        //
                                        orgDefer.resolve(true);
                                        // 刷新树
                                        obj.refresh();
                                    });
                                } else {
                                    orgDefer.resolve(true);
                                }
                                // 加载岗位
                                if (treeNode['_hasPosition']) {
                                    var positionPromise = OrgPositionService.queryByOrgId({orgId: treeNode.id});
                                    CommonUtils.loading(positionPromise, '加载岗位数据...', function (data) {
                                        var children = treeNode.children;
                                        data = data.data || [];
                                        orgDefer.promise.then(function () {
                                            angular.forEach(data || [], function (o) {
                                                o.icon = CommonUtils.contextPathURL('/vendor/zTree/img/diy/1_open.png');
                                                children.push(o);
                                                // 刷新树
                                                obj.refresh();
                                                treeNode['_hasQuery'] = true;
                                            });
                                        });
                                    });
                                }
                            }
                        },
                        onClick: function (event, treeId, treeNode) {
                            // 如果点击的是组织机构，则不执行操作
                            if (!treeNode['_isOrg'] && angular.isFunction(options.callback)) {
                                var obj = this.getZTreeObj(treeId);
                                options.scope.$apply(function () {
                                    options.callback.call(obj, treeNode);
                                });
                            }
                        }
                    }
                };

                // 查询所有具有岗位的组织机构
                var who = OrgPositionService.whoHasPosition();
                CommonUtils.loading(who, '加载具有岗位的组织机构...', function (data) {
                    hasPositionOrgDefer.resolve(data.data || []);
                });

                // 查询组织机构的第一级菜单
                var promise = options.permission ? OrgService.permissionRootQuery() : OrgService.queryValidChildren();
                var defer = CommonUtils.defer();
                CommonUtils.loading(promise, '加载机构树...', function (data) {
                    data = data.data || [];
                    hasPositionOrgDefer.promise.then(function (orgIds) {
                        hasPositionOrgIds = orgIds;
                        // 回显具有岗位的组织机构
                        wrapData(data);
                        var treeObj = $.fn.zTree.init($("#" + id), setting, data);
                        defer.resolve(treeObj);
                    });
                });
                return defer.promise;
            };
            return {
                // 用于在指定地方动态展示一个动态的组织机构岗位树
                // 注意，在该树中，机构是不允许点击的,并且，显示的组织机构仅仅为状态为有效的
                // 必须参数：
                //  scope:
                //  id：树要显示的地方
                // 可选参数：
                //  onClick：选中节点后要触发的函数，接收一个node对象
                tree: function (scope, id, onClick) {
                    dynamicTree({scope: scope, id: id, callback: onClick, permission: false});
                },

                // 用于在指定地方动态展示一个动态的组织机构岗位树
                // 其他说明见tree方法
                permissionTree: function (scope, id, onClick) {
                    dynamicTree({scope: scope, id: id, callback: onClick, permission: true});
                },

                // 该方法会打开一个弹出层，然后选择组织机构下的岗位（多选），该方法接收一个配置项
                // 注意：只会查询状态为有效的组织机构
                // 配置项参数如下：{}
                //  必须参数：
                //      callback:点击确定后的回调，接收被选中的所有的节点
                //  可选参数：
                //      ids:[]，被选中的岗位的id
                multiPick: function (options) {
                    var hasPositionOrgDefer = CommonUtils.defer();
                    var hasPositionOrgIds;
                    // 给数组中的每个元素都设置_hasChild或者_hasPosition属性
                    // 这个数据来源于组织机构
                    var wrapData = function (data) {
                        angular.forEach(data || [], function (o) {
                            // 设置为组织机构的标识
                            o['_isOrg'] = true;
                            //o['chkDisabled'] = true;
                            o.icon = CommonUtils.contextPathURL('/vendor/zTree/img/diy/1_close.png');
                            // 设置标识，标识有孩子节点
                            if (o.isParent) {
                                o['_hasChild'] = true;
                            }
                            // 设置标识，标识有岗位
                            if ($.inArray(o.id, hasPositionOrgIds || []) > -1) {
                                o.isParent = true;
                                o['_hasPosition'] = true;
                            }
                        });
                    };

                    // 更新树的节点
                    var updateTree = function (treeObj, treeNode, children) {
                        treeNode['_hasQuery'] = true;
                        if (!children || children.length == 0) {
                            treeNode['chkDisabled'] = true;
                            treeObj.updateNode(treeNode);
                        } else {
                            // 刷新节点
                            treeNode['chkDisabled'] = false;
                            // 刷新节点
                            angular.forEach(children, function (o) {
                                if (!o['_isOrg']) {
                                    o.busType = treeNode.busiTypeId;
                                }
                                treeObj.addNodes(treeNode, o, false);
                            });
                            treeObj.updateNode(treeNode);
                        }
                    };
                    ZtreeModal.doubleTree({
                        title: '选择岗位',
                        initLeft: function () {

                            // 查询所有具有岗位的组织机构
                            var who = OrgPositionService.whoHasPosition();
                            CommonUtils.loading(who, '加载具有岗位的组织机构...', function (data) {
                                hasPositionOrgDefer.resolve(data.data || []);
                            });

                            // 查询组织机构的第一级菜单
                            var promise = OrgService.queryValidChildren();
                            var defer = CommonUtils.defer();
                            CommonUtils.loading(promise, '加载机构树...', function (data) {
                                data = data.data || [];
                                hasPositionOrgDefer.promise.then(function (orgIds) {
                                    hasPositionOrgIds = orgIds;
                                    // 回显具有岗位的组织机构
                                    wrapData(data);
                                    defer.resolve(data);
                                });
                            });
                            return defer.promise;
                        },
                        showSelectAllButton: false,
                        defaultChecked: options.ids,
                        callback: options.callback,
                        expand: function (event, treeId, treeNode) {
                            if (treeNode['_hasQuery'] == undefined) {
                                // 展示时动态加载数据
                                var obj = this.getZTreeObj(treeId);

                                // 加载孩子节点
                                if (treeNode['_hasChild']) {
                                    var promise = OrgService.queryValidChildren({id: treeNode.id});
                                    CommonUtils.loading(promise, '加载机构数据...', function (data) {
                                        data = data.data || [];
                                        var children = [];
                                        wrapData(data);
                                        angular.forEach(data, function (o) {
                                            children.push(o);
                                        });
                                        updateTree(obj, treeNode, children);
                                    });
                                    treeNode['_hasQuery'] = true;
                                }
                                // 加载岗位
                                if (treeNode['_hasPosition']) {
                                    var positionPromise = OrgPositionService.queryByOrgId({orgId: treeNode.id});
                                    CommonUtils.loading(positionPromise, '加载岗位数据...', function (data) {
                                        data = data.data || [];
                                        var children = [];
                                        angular.forEach(data || [], function (o) {
                                            o.icon = CommonUtils.contextPathURL('/vendor/zTree/img/diy/1_open.png');
                                            children.push(o);
                                        });
                                        updateTree(obj, treeNode, children);
                                    });
                                    treeNode['_hasQuery'] = true;
                                }
                            }
                        },
                        resultFilter: function (item) {
                            // 只取岗位数据
                            return !item['_isOrg'];
                        },
                        ztree: {
                            view: {
                                showIcon: true
                            },
                            check: {
                                enable: true
                            },
                            data: {
                                simpleData: {
                                    pIdKey: 'parentId',
                                    enable: true
                                }
                            }
                        }
                    });
                }
            }
        }
    )
    ;

})(angular, jQuery);
