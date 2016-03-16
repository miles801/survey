/**
 * Created by chenl on 2014-10-14 15:04:57.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm..position.list', ['eccrm.position.position',
        'eccrm.base.position.modal',
        'eccrm.angular',
        'eccrm.angularstrap']);
    app.controller('PositionListController', function ($scope, ClassifyService, CommonUtils, AlertFactory, ModalFactory, PositionModal, PositionService) {

        // 添加岗位分类
        $scope.addAllot = function () {
            PositionModal.addAllot({scope: $scope}, initTree);
        };

        // 添加岗位
        $scope.addRole = function () {
            PositionModal.addRole({scope: $scope}, $scope.query);
        };


        // 新增
        $scope.add = function () {
            $scope.type = 'add';
            PositionModal.add({scope: $scope, callback: initTree});
        };

        // 更新
        $scope.edit = function (id) {
            $scope.type = 'modify';
            $scope.id = id;
            PositionModal.add({scope: $scope}, function () {
                $scope.query($scope.current.id)
            });
        };

        // 明细
        $scope.detail = function (id) {
            $scope.type = 'detail';
            $scope.id = id;
            PositionModal.add({scope: $scope, callback: initTree});
        };

        //初始化ztree树（必须在文档加载后执行）
        var setting = {
            view: {
                selectedMulti: false
            },
            data: {
                simpleData: {enable: true}
            },
            edit: {
                enable: true,
                removeTitle: '删除',
                showRemoveBtn: true,
                showRenameBtn: true,
                renameTitle: '更改'
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    //防止重复点击
                    if ($scope.current && $scope.current.id == treeNode.id) return;
                    $scope.query(treeNode.id);
                },
                beforeRemove: function (treeId, treeNode) {
                    ModalFactory.confirm({
                        scope: $scope,
                        content: '删除岗位分类“' + treeNode.name + '”后将无法恢复，请确认!',
                        callback: function () {
                            var promise = ClassifyService.deleteByIds({ids: treeNode.id}, function (data) {
                                if (data.success) {
                                    AlertFactory.success(null, '删除成功!');
                                    initTree();
                                }
                            });
                            CommonUtils.loading(promise);
                        }
                    });
                    return false;
                },
                beforeEditName: function (treeId, treeNode) {
                    PositionModal.addAllot({scope: $scope, id: treeNode.id, pageType: 'modify'}, initTree);
                    return false;
                }
            }
        };
        $scope.current = {};
        var initTree = function () {
            ClassifyService.tree(function (data) {
                var id;
                data = data.data || [];
                if (angular.isArray(data) && data.length > 0) {
                    id = data[0].id;
                    $scope.query();
                }
                //初始化菜单
                $.fn.zTree.init($("#treeDemo"), setting, [
                    {name: '岗位类别', children: data || [], open: true}
                ]);

            });
        };
        if ($scope.current.id == null || $scope.current.id == undefined) {

        }
        initTree();

        var defaults = {
            orderBy: 'createdDatetime',
            reverse: 'false'
        };
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
        };
        $scope.reset();
        $scope.pager = {
            fetch: function () {
                if ($scope.current.id != null && $scope.current.id != undefined) {
                    var param = angular.extend({}, $scope.condition, {id: $scope.current.id}, {
                        start: this.start,
                        limit: this.limit
                    });
                    return CommonUtils.promise(function (defer) {
                        PositionService.queryAll(param, function (dataParam) {
                            dataParam = dataParam.data || {total: 0};
                            $scope.positions = dataParam;
                            defer.resolve(dataParam);
                        });
                    });
                } else {
                    var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});

                    return CommonUtils.promise(function (defer) {
                        PositionService.query(param, function (dataParam) {
                            dataParam = dataParam.data || {total: 0};
                            $scope.positions = dataParam
                            defer.resolve(dataParam);
                        });
                    });
                }
            }
        }
        //查询数据
        $scope.current = {};
        $scope.query = function (id) {
            if (id != null && !angular.isFunction(id)) {
                $scope.current.id = id;
            } else {
                $scope.current.id = null;
            }
            $scope.pager.query();
        };

        $scope.remove = function (id) {
            //判断id是通过点击没条记录后的删除图标（会传递id）还是点击的上面删除按钮
            if (id == undefined) {
                var ids = new Array();
                angular.forEach($scope.items || [], function (v) {
                    ids.push(v.id);
                });
                for (var i = 0; i < ids.length; i++) {
                    if (ids[i] != 'undefined') {
                        id = id + ids[i] + ",";
                    }
                }
            }
            //删除
            art.dialog({
                content: '是否删除岗位，请确认！',
                ok: function () {
                    var result = PositionService.deleteByIds({ids: id});
                    AlertFactory.handle($scope, result, function () {
//                            AlertFactory.success('删除成功!',-1);
                        $scope.query($scope.current.id)
                    });
                    return true;
                },
                cancelVal: '关闭',
                cancel: true
            });
        };
    });
})(window, angular, jQuery);