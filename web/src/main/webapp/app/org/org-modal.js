/**
 * dependencies:
 */


(function (angular, window) {
    var app = angular.module('base.org.modify.modal', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'base.orgtemp',
        'eccrm.angular.ztree',
        'eccrm.base.region',
        'wbs.org.business.code',
        'base.org'
    ]);
    app.factory('OrgModal', function ($modal, OrgTempService, OrgTree, AlertFactory, CommonUtils, ModalFactory, OrgAllTree, Org, OrgService, OrgConstant, RegionPicker, RegionService) {
            var common = function (options) {
                var defaults = {
                    scope: null,//必选项
                    pageType: 'view',// 必须
                    callback: null,//点击确定后要执行的函数
                    afterShown: null//模态对话框显示完成后要执行的函数
                };
                options = angular.extend({}, defaults, options);
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/org/template/orgModal-edit.tpl.html')
                });
                var $scope = modal.$scope;
                $scope.pageType = options.pageType;


                $scope.ztreeOptions = {
                    data: OrgAllTree.init().then(function (data) {
                        data = data.data || [];
                        if (data !== undefined) {
                            return data;
                        } else {
                            data = {id: 0, name: "组织机构"};
                            return data;
                        }


                    }),
                    multi: true,
                    click: function (node) {
                        $scope.condition.parentId = node.id;
                        $scope.condition.parentName = node.name;
                    }
                };

                // 清除所选的城市
                $scope.clearRegion = function () {
                    $scope.condition.businessAreaName = null;
                    $scope.condition.businessArea = null;
                };

                // 初始化行政区域树
                $scope.regionOptions = {
                    // 初识数据加载
                    data: function (callback) {
                        return CommonUtils.promise(function (defer) {
                            RegionService.tree({root: true}, function (data) {
                                data = data.data || [];
                                defer.resolve(data);
                            });
                        });
                    },
                    multi: true,
                    async: function (node, callback) {
                        var pid = node.id;
                        RegionService.tree({parentId: pid}, function (data) {
                            callback.call(node, data.data || []);
                        });
                    },
                    click: function (data) {
                        $scope.condition.businessArea = data.id;
                        $scope.condition.businessAreaName = data.name;
                    }
                };

                // 清除所选的机构
                $scope.clearOrg = function () {
                    $scope.condition.parentId = null;
                    $scope.condition.parentName = null;
                };

                // 机构类型
                OrgConstant.orgType(function (orgType) {
                    $scope.orgTypes = orgType;
                });
                // 系统
                OrgConstant.bussitypeId(function (bussitypeId) {
                    $scope.busiTypeIds = bussitypeId;
                });
                return $scope;
            };
            return {

                add: function (options, callback) {
                    callback = callback || options.callback;
                    var $scope = common(angular.extend(options, {pageType: 'add'}));
                    $scope.condition = {
                        isStatus: true,
                        isOrgs: true,
                        parent: null,
                        createdDatetime: new Date().getTime(),
                        creatorName: CommonUtils.loginContext().employeeName,
                        modifiedDatetime: new Date().getTime()
                    };

                    // 保存
                    $scope.save = function () {
                        if ($scope.condition != null) {
                            if ($scope.condition.isStatus == true) {
                                $scope.condition.status = "1";
                            } else {
                                $scope.condition.status = "0";
                            }
                            if ($scope.condition.isOrgs == true) {
                                $scope.condition.isOrg = 1;
                            } else {
                                $scope.condition.isOrg = 0;
                            }
                            $scope.condition.createdDatetime = moment(new Date().getTime()).format('YYYY-MM-DD HH:mm:ss');
                            OrgService.save($scope.condition, function (data) {
                                //更新成功
                                if (data && data['success'] == true) {
                                    $scope.$hide();
                                    //保存失败
                                } else {
                                    $scope.alert = {
                                        message: '保存失败！[' + (data['fail'] || data['error'] || '') + ']',
                                        type: 'danger'
                                    };
                                }
                                angular.isFunction(callback) ? callback.call($scope) : null;
                            });
                        }
                    }
                },

                modify: function (options, callback) {
                    if (!options.id) {
                        alert('更新机构时，没有获得机构ID!');
                        return false;
                    }
                    callback = callback || options.callback;
                    var $scope = common(options);
                    $scope.pageType = 'modify';
                    $scope.condition = {parent: null, status: 'ACTIVE', parentId: null, isOrg: 0};
                    OrgService.get({id: options.id}, function (data) {
                        data = data.data || [];
                        $scope.condition = data;
                        if ($scope.condition.status == "1") {
                            $scope.condition.isStatus = true;
                        }
                        if ($scope.condition.isOrg == 1) {
                            $scope.condition.isOrgs = true;
                        }
                        if (!$scope.condition.createdDatetime) {
                            $scope.condition.modifiedDatetime = moment($scope.condition.createdDatetime).format('YYYY-MM-DD HH:mm:ss')
                        }
                        if (!$scope.condition.modifiedDatetime) {
                            $scope.condition.modifiedDatetime = moment(new Date().getTime()).format('YYYY-MM-DD HH:mm:ss')
                        }
                    });

                    $scope.clearOrg = function () {
                        $scope.condition.parentId = null;
                        $scope.condition.parentName = null;
                    };

                    $scope.update = function () {
                        if ($scope.condition != null) {
                            if ($scope.condition.isStatus == true) {
                                $scope.condition.status = "1";
                            } else {
                                $scope.condition.status = "0";
                            }
                            if ($scope.condition.isOrgs == true) {
                                $scope.condition.isOrg = 1;
                            } else {
                                $scope.condition.isOrg = 0;
                            }

                            OrgService.update($scope.condition, function (data) {
                                //更新成功
                                if (data && data['success'] == true) {
                                    $scope.$hide();
                                    //保存失败
                                } else {
                                    $scope.alert = {
                                        message: '更新失败！[' + (data['fail'] || data['error'] || '') + ']',
                                        type: 'danger'
                                    };
                                }
                                angular.isFunction(callback) ? callback.call($scope) : null;
                            });
                        }
                    }

                },

                // 查看明细
                view: function (id) {
                    if (!id) {
                        alert('查看机构时，没有获得机构ID!');
                        return false;
                    }
                    var $scope = common({});
                    $scope.pageType = 'view';
                    $scope.condition = {};
                    var promise = OrgService.get({id: id}, function (data) {
                        data = data.data || [];
                        $scope.condition = data;
                        if ($scope.condition.status == "1") {
                            $scope.condition.isStatus = true;
                        }
                        if ($scope.condition.isOrg == 1) {
                            $scope.condition.isOrgs = true;
                        }
                        if (!$scope.condition.createdDatetime) {
                            $scope.condition.modifiedDatetime = moment($scope.condition.createdDatetime).format('YYYY-MM-DD HH:mm:ss')
                        }
                        if (!$scope.condition.modifiedDatetime) {
                            $scope.condition.modifiedDatetime = moment(new Date().getTime()).format('YYYY-MM-DD HH:mm:ss')
                        }
                    });
                    CommonUtils.loading(promise, '加载中...');


                },
                // 从临时机构表中选择机构，同步到本地机构表
                // 功能描述：
                //  打开弹出层，左侧为临时机构树，右侧为本系统机构树
                //  从左侧机构树中选择树节点，然后选择本系统中的上级机构树（可以不选），点击转移按钮后
                //  会将选择的节点数据同步到本系统的机构下
                addOrgFromTemp: function (options, callback) {
                    var defaults = {
                        callback: null//点击确定后要执行的函数
                    };

                    options = angular.extend({}, defaults, options);
                    var modal = $modal({
                        backdrop: 'static',
                        template: CommonUtils.contextPathURL('app/org/template/orgTemp-org.tpl.html')
                    });
                    var that = this;
                    callback = callback || options.callback;
                    var scope = modal.$scope;
                    // 临时机构树
                    var tempOrgTree;
                    var tempOrgTreeOptions = {
                        view: {
                            showIcon: false
                        },
                        check: {
                            enable: true,
                            chkStyle: "checkbox",
                            chkboxType: {"Y": "s", "N": "ps"}
                        },
                        data: {
                            simpleData: {enable: true}
                        },
                        callback: {
                            onExpand: function (event, treeId, treeNode) {
                                var obj = this.getZTreeObj(treeId);
                                var checked = obj.getNodeByParam("id", treeNode.id, null).checked;
                                if (!(treeNode.children && treeNode.children.length > 0)) {
                                    var promise = OrgTempService.queryValidChildren({id: treeNode.id});
                                    CommonUtils.loading(promise, '加载机构数据...', function (data) {
                                        data = data.data || [];
                                        treeNode.children = data;
                                        if (checked) {
                                            if (treeNode.children != null && treeNode.children.length > 0) {
                                                for (var i = 0; i < treeNode.children.length; i++) {
                                                    var childrenNode = treeNode.children[i];
                                                    if (childrenNode != null) {
                                                        obj.checkNode(childrenNode, true, false);
                                                    }
                                                }
                                            }
                                        }
                                        // 刷新树
                                        obj.refresh();

                                    });
                                }

                            }
                        }
                    };

                    // 本地机构树
                    var orgTree;
                    var orgTreeOptions = {
                        view: {
                            showIcon: false
                        },
                        check: {
                            enable: false
                        },
                        data: {
                            simpleData: {enable: true}
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
                            }
                        }
                    };

                    // 初始化临时组织机构树（同步过来的未处理的数据）
                    var initTempOrgTree = function () {
                        var promise = OrgTempService.queryValidChildren();
                        CommonUtils.loading(promise, '加载组织机构树...', function (data) {
                            data = data.data || [];
                            tempOrgTree = $.fn.zTree.init($("#treeDemoOrgTemp"), tempOrgTreeOptions, data);
                        });
                    };

                    // 初始化本地组织机构树
                    var initOrgTree = function () {
                        OrgService.queryChildren(function (data) {
                            data = data.data || [];
                            orgTree = $.fn.zTree.init($("#treeDemoOrg"), orgTreeOptions, [
                                {name: '组织机构', children: data || [], open: true}
                            ]);
                        });
                    };

                    // 选择本地机构上级树的配置
                    scope.OrgztreeOptions = Org.pick(function (node) {
                        scope.parentOrgName = node.name;
                        scope.parentOrgId = node.id;
                    });
                    // 转移
                    scope.transform = function () {
                        var nodes = tempOrgTree.getCheckedNodes(true);
                        if (nodes.length < 1) {
                            CommonUtils.errorDialog('请选择要转移的机构!');
                            return false;
                        }
                        ModalFactory.confirm({
                            scope: scope,
                            content: '<b>是否确认转移当前选择的机构</b>到客诉服务平台，请确认!'
                        }, function () {
                            var parentId = scope.parentOrgId;

                            // 规则
                            // 如果勾选了”半选“，则连同半选节点一起迁移，只是不包含子节点
                            // 如果没有勾选“半选”，则跳过半选的节点
                            var checkedTmpOrg = [];     // 被选中的临时机构树中的节点
                            var parentOrgIds = [];
                            angular.forEach(nodes || [], function (o) {

                                // 是上级节点
                                if (o.isParent == true) {
                                    // 没展开被勾选，或者展开了且勾选了全部
                                    if (o.check_Child_State == -1 || o.check_Child_State == 2) {
                                        checkedTmpOrg.push(o.id);
                                    }
                                } else {
                                    var pId = o.pId || o.parentId;
                                    // 如果父id已经被添加，则跳过当前节点
                                    if ($.inArray(pId, checkedTmpOrg) == -1) {
                                        checkedTmpOrg.push(o.id);
                                    }
                                }
                            });
                            var promise = OrgService.transformFromTmpOrg({
                                orgId: parentId,
                                tmpOrgId: checkedTmpOrg.join(',')
                            });
                            CommonUtils.loading(promise, '转移中...', function (data) {
                                if (data.success) {
                                    modal.hide();
                                    angular.isFunction(callback) && callback();
                                }
                            });
                        });
                    };

                    // 模态对话框显示后要执行的操作
                    ModalFactory.afterShown(modal, function () {
                        initTempOrgTree();
                        initOrgTree();
                    });
                }

            }
        }
    )
    ;
})(angular, window);