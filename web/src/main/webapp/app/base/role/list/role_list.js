/**
 * Created by chenl  on 13-11-5.
 */
(function (angular) {
    var app = angular.module('eccrm.base.role.list', [
        'eccrm.base.role',
        'eccrm.base.role.modal',
        'eccrm.base.menu.modal',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('RoleListController', function ($scope, RoleService, RoleResourceService, ModalFactory, AlertFactory, RoleModal, RoleConstant, CommonUtils, MenuModal) {
        RoleConstant.status(function (data) {
            $scope.RoleStatus = data;
        });
        //初始化参数
        var defaults = {
            status: 'ACTIVE'
        };
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
        };

        $scope.add = function () {
            RoleModal.add({scope: $scope}, $scope.query);
        };
        $scope.modify = function (id) {
            RoleModal.modify({scope: $scope, id: id}, $scope.query);
        };
        $scope.view = function (id) {
            RoleModal.view({scope: $scope, id: id});
        };

        $scope.grant = function (id) {
            MenuModal.dbTreePicker({
                title: '资源授权',
                defaultChecked: function () {
                    return CommonUtils.promise(function (defer) {
                        var result = RoleResourceService.queryByRole({roleId: id});
                        AlertFactory.handle(null, result, function (data) {
                            data = data.data || [];
                            var items = [];
                            angular.forEach(data, function (v) {
                                items.push(v.id);
                            });
                            defer.resolve(items);
                        });
                    });
                }
            }, function (items) {
                var ids = [];
                angular.forEach(items, function (v) {
                    ids.push(v.id);
                });
                var result = RoleResourceService.save({
                    roleId: id,
                    resourceIds: ids.join(',')
                });
                AlertFactory.handle(null, result);
            });
        };
        $scope.remove = function (id) {
            ModalFactory.remove($scope, function () {
                if (!id && $scope.items && $scope.items.length > 0) {
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.id);
                    });
                    id = items.join(',');
                }
                var result = RoleService.deleteByIds({ids: id});
                AlertFactory.handle($scope, result, $scope.query);
            });
        };
        //初始化页面
        $scope.reset();

        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {
                    start: $scope.pager.start || 0,
                    limit: $scope.pager.limit || 10
                }, $scope.condition);
                if (!param.status || param.status == '0') delete param.status;
                return CommonUtils.promise(function (defer) {
                    var result = RoleService.query(param);
                    AlertFactory.handle($scope, result, function (data) {
                        $scope.roles = data.data || [];
                        defer.resolve($scope.roles);
                    });
                });
            }
        };

        //查询数据
        $scope.query = function () {
            ($scope.pager.query || $scope.pager.fetch)();
        };

        var destroy = $scope.$watch('condition', function (value, oldValue) {
            CommonUtils.delay($scope.query, 400);
        }, true);

        $scope.$on('$destroy', destroy);
    });
})(angular);

