/**
 * Created by chenl on 2014-10-14 15:56:00.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.position.positionAllot.list', ['eccrm.position.positionAllot',
        'com.wanda.positionAllot.modal',
        'eccrm.base.param',
        'eccrm.angular',
        'eccrm.angularstrap']);
    app.controller('PositionAllotListController', function ($scope, CommonUtils, AlertFactory, ModalFactory, positionAllotConstant, Debounce, Parameter, positionAllotConstant, PositionAllotModal, $window, PositionAllotService) {
        //回到上一个页面
        $scope.back = function () {
            $window.history.back();
        }
        //回到上一个页面
        $scope.add = function () {
            PositionAllotModal.add({scope: $scope}, function (data) {
            });
        }
        // 系统
        positionAllotConstant.busiType(function (data) {
            $scope.busiTypes = data;
        });
        // 机构类型
        positionAllotConstant.orgType(function (data) {
            $scope.orgTypes = data;
        });
        /* var setting = {
         view: {showIcon: false},
         data: {
         simpleData: {enable: true}
         },
         callback: {
         onClick: function (event, treeId, treeNode) {
         var obj = this.getZTreeObj(treeId);
         //防止重复点击
         if ($scope.current && $scope.current.id == treeNode.id) return;
         if(treeNode.cascadeTypeCode !=null){
         Parameter.systemItems(treeNode.cascadeTypeCode, function (dataParam) {
         dataParam = dataParam.data || {total: 0};
         if (dataParam != null && dataParam.length > 0) {
         for (var i = 0; i < dataParam.length; i++) {
         dataParam[i].flag = true;
         }
         if(treeNode.children != null && treeNode.children.length > 0){
         treeNode.children = treeNode.children.concat(dataParam);
         }else{
         treeNode.children =dataParam;
         }
         obj.refresh();
         }
         });
         }

         if (treeNode.flag!=undefined && treeNode.flag) {
         $scope.query(treeNode.type,treeNode.cascadeTypeCode);
         } else{
         $scope.query(treeNode.type);
         }

         }
         }
         };
         $scope.current = {};
         var initTree = function () {
         positionAllotConstant.busiType(function (data) {
         if(data != null && data.length>0){

         $.fn.zTree.init($("#treeDemo"), setting, data);
         }else{
         $.fn.zTree.init($("#treeDemo"), setting, [

         {name: '系统', children: data || [],open:true}
         ]);
         }
         });
         //初始化菜单

         }
         initTree();*/

        var defaults = {
            orderBy: 'createdDatetime',
            reverse: 'false'
        };
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
        }
        $scope.reset();
        $scope.pager = {
            fetch: function () {
                if ($scope.condition.positionName == "") {
                    $scope.condition.positionName = null;
                }
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                return CommonUtils.promise(function (defer) {
                    PositionAllotService.query(param, function (dataParam) {
                        dataParam = dataParam.data || {total: 0};
                        $scope.positionAllots = dataParam
                        defer.resolve(dataParam);
                    });
                });
//                }
                return $scope.positions;
            }
        }
        //查询数据
        $scope.current = {};
        $scope.query = function () {
            $scope.pager.query();
        }
        $scope.$watch('condition', function (value, oldValue) {
            Debounce.delay($scope.query, 400);
        }, true);

        $scope.remove = function (id) {
            //判断id是通过点击没条记录后的删除图标（会传递id）还是点击的上面删除按钮
            if (id == undefined) {
                var ids = new Array();
                angular.forEach($scope.items || [], function (v) {
                    ids.push(v.id);
                });
                for (var i = 0; i < ids.length; i++) {
                    if (ids[i] != 'undefined' && ids[i] != null && ids[i] != "") {
                        if (i == 0) {
                            id = ids[i];
                        } else {
                            id = id + "," + ids[i];
                        }

                    }
                }
            }
            //删除
            art.dialog({
                content: '是否删除岗位分类，请确认！',
                ok: function () {
                    var result = PositionAllotService.deleteByIds({ids: id});
                    AlertFactory.handle($scope, result, function () {
                        $scope.query()
                        AlertFactory.success('删除成功!', -1);
                    });
                    return true;
                },
                cancelVal: '关闭',
                cancel: true
            });
        };
    });
})(window, angular, jQuery);
