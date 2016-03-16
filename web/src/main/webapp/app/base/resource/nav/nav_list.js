/**
 * Created by miles on 13-11-25.
 */
(function (angular, $) {
    var app = angular.module('eccrm.base.nav.list', [
        'eccrm.base.nav',
        'eccrm.base.nav.modal',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('NavListCtrl', function ($scope, MenuNavService, MenuNavModal, MenuNavConstant, CommonUtils) {
        //定义变量
        $scope.pager = {
            fetch: function () {
                var param = {start: this.start || 0, limit: this.limit || 15};
                return CommonUtils.promise(function (defer) {
                    CommonUtils.loading(defer, '加载菜单导航...');
                    MenuNavService.pageQuery(param, function (data) {
                        data = data.data;
                        $scope.beans = data;
                        defer.resolve(data);
                    });
                });
            },
            finishInit: function () {
                this.query();
            }
        };

        //定义方法
        $scope.query = function () {
            $scope.pager.query();
        };
        $scope.remove = function (menuId) {
        };
        $scope.add = function () {
            MenuNavModal.add($scope.query);
        };
        $scope.modify = function (id) {
            MenuNavModal.update(id, $scope.query);
        };
        $scope.view = function (id) {
            MenuNavModal.view(id);
        };
    })
})(angular, jQuery);