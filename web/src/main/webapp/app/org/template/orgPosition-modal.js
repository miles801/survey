/**
 * Created by liuxq on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  usergroup.js
 */


(function (angular, window) {
    var app = angular.module('base.orgPosition.add.modal', ['mgcrea.ngStrap',
        'eccrm.position.position','base.org.position','eccrm.directive.ztree',
        'eccrm.angular', 'eccrm.angularstrap']);
    app.factory('orgPositionaddModal', function ($modal,$filter,Debounce, AlertFactory,CommonUtils, ModalFactory,CompileTree,PositionService,OrgPositionService) {
            var common = function (options) {
                var defaults = {
                    scope: null,//必选项
                    callback: null,//点击确定后要执行的函数
                    afterShown: null//模态对话框显示完成后要执行的函数
                };
                options = angular.extend({}, defaults, options);
                var scope = options.scope;
                if (!scope) throw '使用模态对话框时必须指定scope!';

                var modal = $modal({scope: scope, template: CommonUtils.contextPathURL('app/org/template/orgPosition_select.tpl.html')});

                var $scope = modal.$scope;
                return $scope;
            };
            return {
                select: function (options, callback) {
                    if (!options.id) throw '添加员工时没有获得机构ID';
                    var orgId=options.id;
                    callback = callback || options.callback;
                    var $scope = common(options);
                    $scope.pageType = 'modify';
                    $scope.bean={};
                    $scope.condition=[];
                    $scope.positoinlist=[];
                    $scope.query = function(){
                         /* PositionService.query({}, function (data) {
                         data=data.data || {};
                         $scope.positionAllotd = data.data;
                         });*/
                        var promise =  PositionService.query();
                        CommonUtils.loading(promise, '加载岗位数据...', function (dataParam) {
                            dataParam = dataParam.data || {total: 0};
                            $scope.positionAllotd=dataParam.data
                        });
                    }
                    $scope.reset = function(){
                        $scope.beans.classify = null;
                    }

                    $scope.ClassifyztreeOptions = {
                        data: CompileTree.init,
                        click: function (node) {
                            $scope.beans.classify = {id: node.id, name: node.name};
                            var promise = PositionService.queryAll({id: node.id});
                            CommonUtils.loading(promise, '加载岗位数据...', function (dataParam) {
                                dataParam = dataParam.data || {total: 0};
                                $scope.positionAllotd=dataParam.data
                            });
                        }
                    }
                    $scope.close = function () {
                        $scope.$hide();
                    }
                    $scope.positionAllots = [];
                    $scope.selectOne = function () {
                        if (!$scope.itemd || !$scope.itemd.length)return;
                        for (var j = 0; j < $scope.itemd.length; j++) {
                            var itemdId = $scope.itemd[j].id
                            for (var i = 0; i < $scope.positionAllotd.length; i++) {
                                var ids = $scope.positionAllotd[i].id;
                                if (ids == itemdId) {
                                    $scope.positionAllots.push($scope.positionAllotd[i])
                                    $scope.positionAllotd.splice(i, 1);
                                }
                            }
                        }
                        $scope.itemd = [];
                        $scope.items = [];
                    }
                    $scope.deleteOne = function () {
                        if (!$scope.items || !$scope.items.length)return;
                        for (var j = 0; j < $scope.items.length; j++) {
                            var itemsId = $scope.items[j].id
                            for (var i = 0; i < $scope.positionAllots.length; i++) {
                                var ids = $scope.positionAllots[i].id;
                                if (ids == itemsId) {
                                    $scope.positionAllotd.push($scope.positionAllots[i])
                                    $scope.positionAllots.splice(i, 1);
                                }
                            }
                        }
                        $scope.itemd = [];
                        $scope.items = [];
                    }
                    $scope.selectAll = function () {
                        if ($scope.positionAllots != null) {
                            for (var i = 0; i < $scope.positionAllotd.length; i++) {
                                $scope.positionAllots.push($scope.positionAllotd[i])
                            }
                        } else {
                            $scope.positionAllots = tmp.data;
                        }
                        $scope.positionAllotd = [];
                        $scope.itemd = [];
                        $scope.items = [];
                    }
                    $scope.deleteAll = function () {
                        if ($scope.positionAllotd != null) {
                            for (var i = 0; i < $scope.positionAllots.length; i++) {
                                $scope.positionAllotd.push($scope.positionAllots[i])
                            }
                        } else {
                            $scope.positionAllotd = tmp.data;
                        }
                        $scope.positionAllots = [];
                        $scope.itemd = [];
                        $scope.items = [];
                    }
                    /*$scope.addAllToRight=function(){
                        $scope.condition=[];
                        for(var i=0;i<$scope.positoinlist.length;i++){
                            $scope.condition.push({orgId:orgId,roleId:$scope.positoinlist[i].id,name:$scope.positoinlist[i].name,
                                shortName:$scope.positoinlist[i].name,roleType:$scope.positoinlist[i].roleType});
                        }
                        $scope.positoinlist =null;
                    }
                    $scope.addToRight=function(){

                            if (!$scope.oitems || $scope.oitems.length < 1){
                                AlertFactory.error($scope, null, '请选选择岗位!');
                                return;
                            }
                        angular.forEach($scope.oitems || [], function (v, index) {
                            $scope.condition.push({orgId:orgId,roleId:v.id,name:v.name,
                                shortName:v.shortName,roleType:v.roleType});
                            $scope.positoinlist.splice(v, 1);

                        });


                    }

                    $scope.removeFromRight=function(){

                                if (!$scope.items || $scope.items.length < 1){
                                    AlertFactory.error($scope, null, '请选选择岗位!');
                                    return;
                                }
                        angular.forEach($scope.items || [], function (v, index) {
                            $scope.positoinlist.push({id:v.roleId,name:v.name,
                                shortName:v.shortName,roleType:v.roleType});
                        });
                        angular.forEach($scope.oitems || [], function (v, index) {
                                    (function (t, m) {
                                        for (var i = 0; i < $scope.condition.length; i++) {
                                            if ($scope.condition[i].roleId == t.id) {
                                                $scope.condition.splice(i, 1);
                                            }
                                        }
                                    })(v, index);
                                });

                    }
                    $scope.removeAllRight=function(){
                        $scope.positoinlist=[];
                        for(var i=0;i<$scope.condition.length;i++){
                            //console.dir($scope.condition[i]);

                            $scope.positoinlist.push({id:$scope.condition[i].roleId,name:$scope.condition[i].name,
                                shortName:$scope.condition[i].shortName,roleType:$scope.condition[i].roleType});
                        }
                        $scope.condition =null;
                    }*/
                    $scope.save = function () {
                        for(var i=0;i<$scope.positionAllots.length;i++){
                            $scope.condition.push({orgId:orgId,roleId:$scope.positionAllots[i].id,name:$scope.positionAllots[i].name,
                                shortName:$scope.positionAllots[i].name,roleType:$scope.positionAllots[i].roleType});
                        }
                        if($scope.condition!=null) {
                            angular.forEach($scope.condition || [], function (v, index) {
                                OrgPositionService.save(v, function (data) {
                                        //更新成功
                                        if (data && data['success'] == true) {
                                            $scope.$hide();
                                            //保存失败
                                        } else {
                                            AlertFactory.error($scope,  '批量保存失败！[' + (data['fail'] || data['error'] || '') + ']');
                                        }
                                    angular.isFunction(callback) ? callback.call($scope) : null;
                                });
                            });
                        }
                    }

                }
            }
        }
    )
    ;
})(angular, window);