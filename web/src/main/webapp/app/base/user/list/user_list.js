/**
 * Created by shenbb on 2014-02-01 16:45:10.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.base.user.list', [
        'eccrm.base.user',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('UserListController', function ($window, $scope, User, CommonUtils, UserConstant, AlertFactory, ModalFactory) {
        //定义变量
        UserConstant.status(function (data) {
            $scope.userStatus = data;
            $scope.userStatus.unshift({value: undefined, name: '全部'});
        });
        UserConstant.type(function (data) {
            $scope.userType = data;
            $scope.userType.unshift({value: undefined, name: '全部'});
        });

        var defaults = {
            orderBy: 'code', reverse: false,
            type: "OFFICIAL"//正式用户
        };

        $scope.add = function () {
            CommonUtils.addTab({
                title: '新增用户',
                url: '/base/user/add'
            });
        };
        // 重置查询条件
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
        };
        $scope.reset();
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                return CommonUtils.promise(function (defer) {
                    var result = User.query(param);
                    CommonUtils.loading(result, '加载用户列表...', function (data) {
                        data = data.data || {total: 0};
                        $scope.users = data;
                        defer.resolve(data);
                    });
                });
            },
            finishInit: function () {
                this.query();
            }
        };


        // 查询
        $scope.query = function () {
            $scope.pager.query();
        };


        /**
         * 删除/注销用户
         */
        $scope.deleteOrCancel = function (user) {//删除
            // 验证状态
            if (user && user.status === 'CANCELED') {
                AlertFactory.error($scope, '用户已注销，操作不可用!');
                return false;
            }
            var id = user && user.id;
            ModalFactory.confirm({
                scope: $scope,
                content: '用户注销/删除后，将不可用，请确认!',
                callback: function () {
                    if (!id) {
                        var items = [];
                        angular.forEach($scope.items, function (v) {
                            items.push(v.id);
                        });
                        if (!items || items.length < 1) {
                            return;
                        }
                        id = items.join(',');
                    }
                    var result = User.deleteByIds({ids: id});
                    AlertFactory.handle($scope, result, function () {
                        AlertFactory.success($scope, null, '删除/注销成功!');
                        $scope.query();
                    });
                }
            });
        };

        /**
         * 更新
         */
        $scope.update = function (user) {
            /*if (user.status === 'CANCELED') {
             AlertFactory.error($scope, '用户已注销，操作不可用!');
             return false;
             }*/
            CommonUtils.addTab({
                title: '更新用户',
                url: 'base/user/modify?id=' + user.id,
                onUpdate: $scope.query
            });
        };

        /**
         * 用户明细
         * @param id
         */
        $scope.detail = function (id) {
            CommonUtils.addTab({
                title: '用户明细',
                url: '/base/user/detail?id=' + id
            });
        };

        /**
         * 重置密码
         */
        $scope.resetPassword = function () {//重置密码
            ModalFactory.confirm({
                scope: $scope,
                content: '重置密码后将会使用默认密码,请确认!',
                callback: function () {
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.id);
                    });
                    var result = User.resetPassword({ids: items.join(',')});
                    AlertFactory.handle($scope, result, function () {
                        AlertFactory.success($scope, '密码重置成功!');
                    });
                }
            });
        };

    });
})(window, angular, jQuery);
