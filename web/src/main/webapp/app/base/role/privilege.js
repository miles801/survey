/**
 * Created by miles on 2014/8/2.
 */
(function () {
    var app = angular.module('eccrm.base.grant', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.role',
        'eccrm.base.role.modal',
        'eccrm.base.user',
        'eccrm.base.usergroup'
    ]);
    app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/group', {templateUrl: 'app/base/role/template/grant-group.tpl.html', controller: 'GroupGrantCtrl'})
            .when('/user', {templateUrl: 'app/base/role/template/grant-use.tpl.html', controller: 'UserGrantCtrl'})
            .otherwise({redirectTo: '/group'});
    }]);
    app.controller('PrivilegeCtrl', function ($scope) {
        $scope.grantRoutes = [
            {url: 'group', name: '用户组授权', active: true},
            {url: 'user', name: '用户授权'}
        ];
    });

    var ztreeDefaultSetting = {
        view: {showIcon: false},
        data: {
            simpleData: {enable: true}
        }
    };
    // 用户组授权Controller
    app.controller('GroupGrantCtrl', function ($scope, Group, ModalFactory, RoleGroupService, AlertFactory, RolePickerModal) {
            var setting = angular.extend({
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        var groupId = treeNode.id;
                        if (!treeNode.id || $scope.groupId == groupId) return;
                        event.preventDefault();
                        // 查询角色
                        $scope.$apply(function () {
                            $scope.groupId = groupId;
                            $scope.query();
                        });
                    }
                }
            }, ztreeDefaultSetting);
            var initTree = function () {
                var result = Group.validTree();
                AlertFactory.handle($scope, result, function (data) {
                    data = data.data || [];
                    $.fn.zTree.init($("#treeDemo"), setting, [
                        {name: '用户组', children: data, open: true}
                    ]);
                });
            };


            $scope.groupId = '';
            $scope.grant = function () {// 授权
                RolePickerModal.multi({scope: $scope, groupId: $scope.groupId}, function (items) {
                    var ids = [];
                    angular.forEach(items, function (v) {
                        ids.push(v.id);
                    });
                    var result = RoleGroupService.save({groupId: $scope.groupId, roleIds: ids.join(',')});
                    AlertFactory.handle($scope, result, $scope.query);
                });
            };

            $scope.remove = function () { // 取消授权
                ModalFactory.confirm({
                    scope: $scope,
                    content: '<b>取消授权</b>后不可恢复,请确认?'
                }, function () {
                    var ids = [];
                    angular.forEach($scope.items, function (v) {
                        ids.push(v.id);
                    });
                    if (ids.length < 1) return;
                    var result = RoleGroupService.deleteByIds({ids: ids.join(',')});
                    AlertFactory.handle($scope, result, $scope.query);
                });
            };

            $scope.query = function () {
                if (!$scope.groupId) return;
                var result = RoleGroupService.query({groupId: $scope.groupId});
                AlertFactory.handle($scope, result, function (data) {
                    $scope.beans = data.data || [];
                });
            };
            initTree();


        }
    )
    ;

    // 用户授权Controller
    app.controller('UserGrantCtrl', function ($scope, User, CommonUtils, AlertFactory, RoleUserService, RolePickerModal) {
        $scope.condition = {};
        $scope.pager = {
            fetch: function () {
                return CommonUtils.promise(function (defer) {
                    var result = User.queryValid($scope.condition);
                    AlertFactory.handle($scope, result, function (data) {
                        data = data.data || {};
                        defer.resolve(data);
                        $scope.beans = data;
                    });
                });
            }
        };
        $scope.query = function () {
            $scope.pager.fetch();
        };

        $scope.grant = function (userId) {
            RolePickerModal.multi({scope: $scope, userId: userId}, function (items) {
                var ids = [];
                angular.forEach(items, function (v) {
                    ids.push(v.id);
                });
                if (ids.length < 1) return;
                var result = RoleUserService.save({userId: userId, roleIds: ids.join(',')});
                AlertFactory.handle($scope, result);
            });
        };

        $scope.viewGrant = function (userId) {
            var result = RoleUserService.query({userId: userId});
            AlertFactory.handle($scope, result, function (data) {
                RolePickerModal.view({scope: $scope, data: data.data});
            });
        };
    });

})();