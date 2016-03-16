/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.org.orgEmpAdd', ['base.orgtemp','eccrm.angular', 'eccrm.angularstrap']);
    app.controller('OrgEmpAddController', function ($scope, AlertFactory,CommonUtils, ModalFactory,OrgTempService,EmpTempService) {
        var foo = {

        }
        $scope.condition = angular.extend({}, foo);
        var data = [
            {"id": 1, "name": "商业规划院","pid":"0" ,"open": true
            }, {"id": 11, "name": "总经办","pid":"1" ,"open": true
            }, {"id": 12, "name": "综合管理中心","pid":"1" ,"open": true
            },
            {"id": 2, "name": "万达学院", "pid":"1"
            }
        ];
        var setting = {
            view: {showIcon: false},
            data: {
                simpleData: {enable: true}
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    $scope.query(treeNode.id);
                }
            }
        };
        var param = angular.extend({},{start: this.start, limit: this.limit});
        var initTree = function () {
            var result = OrgTempService.query(param);
            AlertFactory.handle($scope, result, function (data) {
                data = data.data || [];
                //初始化菜单
                $.fn.zTree.init($("#treeDemos"), setting, [
                    {id: 0, name: '机构组织', children: data || [], open: true}
                ]);
            });

        }
        initTree();
        $scope.query = function (id) {
            if (id === undefined) {
                AlertFactory.error($scope, '错误的查询条件，id不能为空!');
                return;
            }
            var param = angular.extend({},{id: id}, {start: this.start, limit: this.limit});
            return CommonUtils.promise(function (defer) {
                var result = EmpTempService.queryByOrgId(param);
                AlertFactory.handle($scope, result, function (data) {
                    data = data.data;
                    $scope.beans = data.data;
                    defer.resolve(data);

                });
            });
        }
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: $scope.pager.start, limit: $scope.pager.limit});
                return CommonUtils.promise(function (defer) {
                    var result = EmpTempService.query(param);
                    AlertFactory.handle($scope, result, function (data) {
                        data = data.data;
                        $scope.beans = data;
                        defer.resolve(data);
                    });
                });
            }
        };
        $scope.search = function () {
            CommonUtils.delay($scope.pager.query, 0);
        }
        $scope.reset = function () {
            $scope.condition = angular.extend({}, foo);
        }
        var seleted=[];
        $scope.save = function () {
            if ($scope.items.length >0) {
                var items = [];
                angular.forEach($scope.items, function (v) {
                    items.push(v.id);
                });
                seleted.push(items);
                if (angular.isFunction(callback)) {
                    callback.call(that, seleted);
                }
                $scope.$hide();
            } else {
                AlertFactory.success($scope, null, '请选择一个员工!');
            }

        }

    });
})(window, angular, jQuery);