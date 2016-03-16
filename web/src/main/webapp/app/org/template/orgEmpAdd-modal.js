/**
 * Created by liuxq on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  usergroup.js
 */


(function (angular, window) {
    var app = angular.module('base.org.empAdd.modal', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'base.org',// 组织机构
        'eccrm.organi.Emp',// 员工
        'base.position.emp' // 岗位员工
    ]);
    app.factory('orgEmpAddModal', function ($modal, AlertFactory, CommonUtils, ModalFactory, OrgTree, OrgService, EmpService, PositionEmpService) {

            return {
                add: function (options, callback) {
                    if (!options.orgId) throw '添加员工时，没有获得机构ID!';
                    if (!options.positionId) throw '添加员工是，没有获得岗位ID!';
                    var orgId = options.orgId;
                    var positionId = options.positionId;
                    callback = callback || options.callback;

                    var defaults = {
                        scope: null,//必选项
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    };
                    options = angular.extend({}, defaults, options);
                    var scope = options.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    var modal = $modal({
                        scope: scope,
                        template: CommonUtils.contextPathURL('app/org/template/orgEmpAdd_select.tpl.html')
                    });
                    var $scope = modal.$scope;

                    $scope.pageType = 'add';

                    var foo = {
                        creatorName: CommonUtils.loginContext().username,
                        status: '0',
                        createdDatetime: new Date().getTime()
                    };
                    $scope.condition = angular.extend({}, foo);
                    $scope.emplist = angular.extend({});
                    var setting = OrgTree.dynamicTree2(function (event, treeId, treeNode) {
                        $scope.query(treeNode.id);
                    });
                    var param = angular.extend({}, {start: this.start, limit: this.limit});

                    var initTree = function () {
                        var promise = OrgService.queryChildren();
                        CommonUtils.loading(promise, '加载组织机构...', function (data) {
                            data = data.data || [{name: '组织机构'}];
                            $.fn.zTree.init($(".modal #treeDemos"), setting, data);
                        });

                    };

                    $scope.close = function () {
                        $scope.$hide();
                    };
                    $scope.query = function (id) {
                        if (id === undefined) {
                            AlertFactory.error(modal.$scope, '错误的查询条件，id不能为空!');
                            return;
                        }
                        $scope.condition.id = id;
                        $scope.pager.query();

                    };
                    $scope.pager = {
                        limit: 10,
                        fetch: function () {
                            var param = angular.extend({}, $scope.condition, {
                                start: $scope.pager.start || 0,
                                limit: $scope.pager.limit || 10
                            });
                            return CommonUtils.promise(function (defer) {
                                EmpService.queryByOrgId({id: $scope.condition.id}, function (data) {
                                    data = data.data || {total: 0, data: []};
                                    $scope.emps = data;
                                    defer.resolve(data);
                                });
                                CommonUtils.loading(defer, '加载员工列表...');
                            });
                        }
                    };
                    $scope.search = function () {
                        CommonUtils.delay($scope.pager.query, 0);
                    };
                    $scope.reset = function () {
                        $scope.condition = angular.extend({}, foo);
                    };
                    $scope.save = function () {
                        var ids = [];
                        angular.forEach($scope.eitems || [], function (o) {
                            ids.push(o.id);
                        });
                        var promise = PositionEmpService.batchSave({
                            orgId: orgId,
                            positionId: positionId,
                            empId: ids.join(',')
                        });
                        CommonUtils.loading(promise, '正在保存...', function () {
                            angular.isFunction(callback) ? callback.call($scope) : null;
                            $scope.$hide();
                        });

                    };
                    //模态对话框显示后要执行的操作
                    ModalFactory.afterShown(modal, initTree);
                }
            }
        }
    )
    ;
})(angular, window);