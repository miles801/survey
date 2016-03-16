/**
 * Created by liuxq on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  usergroup.js
 */


(function (angular, window) {
    var app = angular.module('base.org.positionEmp.modal', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'base.org',
        'eccrm.organi.Emp',
        'base.org.position',
        'base.position.emp'
    ]);
    app.factory('positionEmpModal', function ($modal, AlertFactory, CommonUtils, ModalFactory, OrgService, EmpService, OrgPosition, OrgPositionService, OrgTree, PositionEmpService) {
            return {
                getUser: function (options, callback) {

                    callback = callback || options.callback;

                    var defaults = {
                        scope: null,//必选项
                        callback: null,//点击确定后要执行的函数
                        afterShown: null,//模态对话框显示完成后要执行的函数
                        orgId: null,
                        busiId: null
                    };
                    options = angular.extend({}, defaults, options);
                    var scope = options.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    var modal = $modal({
                        scope: scope,
                        template: CommonUtils.contextPathURL('app/org/template/positionEmp_select.tpl.html')
                    });
                    var $scope = modal.$scope;
                    var that = this;
                    var positionid = null;
                    var orgId = null;
                    var number = "0";
                    var parentids = null;
                    var p = null;
                    var treedata = {};
                    $scope.parentNumber = [];
                    $scope.condition = angular.extend({id: null});
                    $scope.beans = angular.extend({}, defaults);
                    $scope.beanse = angular.extend({}, defaults);
                    $scope.pager = {

                        fetch: function () {
                            if ($scope.current.id != null && $scope.current.id != undefined
                                && $scope.current.pid != null && $scope.current.pid != undefined) {
                                var param = angular.extend({}, {
                                    positionId: $scope.current.id,
                                    orgId: $scope.current.pid
                                }, {start: $scope.pager.start || 0, limit: $scope.pager.limit || 10});
                                return CommonUtils.promise(function (defer) {
                                    PositionEmpService.queryEmp(param, function (data) {
                                        data = data.data;
                                        if (data.length != 0) {
                                            $scope.beans = data || {total: 0};
                                        }
                                        defer.resolve(data);
                                    });
                                });
                            }
                        }
                    };
                    //查询数据
                    $scope.current = {};
                    $scope.query = function (id, pid) {
                        $scope.current.id=id;
                        $scope.current.pid=pid;
                        $scope.pager.query();
                    };

                    var initTree = function () {
                        OrgPosition.tree($scope, 'treeDemoy', function (node) {
                            $scope.query(node.id, node.pId || node.parentId);
                        })
                    };


                    $scope.close = function () {
                        $scope.$hide();
                    };

                    $scope.save = function () {
                        var foo = angular.extend({empOrgId: $scope.current.pid}, $scope.selected,{positionId:$scope.current.id});
                        if (angular.isFunction(callback)) {
                            callback.call(this, foo);
                        }
                        $scope.$hide();

                    };
                    // 模态对话框显示后要执行的操作
                    ModalFactory.afterShown(modal, initTree);
                }
            }
        }
    )
    ;
})(angular, window);