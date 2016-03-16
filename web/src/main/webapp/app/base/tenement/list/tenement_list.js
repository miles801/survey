/**
 * Created by miles on 13-11-25.
 */
(function (angular, $) {
    var app = angular.module('eccrm.base.tenement.list', [
        'eccrm.base.tenement',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('TenementListController', function ($scope, CommonUtils, $window, TenementService, ModalFactory, AlertFactory, Debounce, TenementConstant) {
        //初始化参数
        $scope.TenementStatus = TenementConstant.status;
        var defaults = {
            status: 1
        };
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
        };
        //初始化分页信息
        $scope.reset();
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                $scope.tenements = TenementService.query(param);
                AlertFactory.handle($scope, $scope.tenements);
                return $scope.tenements;
            },
            finishInit: function () {
                this.fetch();
            }
        };

        $scope.query = function () {
            CommonUtils.delay($scope.pager.query, 0);
        };
        $scope.add = function () {
            $window.location.href = "base/tenement/add";
        };
        //删除
        $scope.remove = function (tenementId) {
            ModalFactory.remove($scope, function () {
                if (!tenementId) {
                    if (!$scope.items || $scope.items.length < 1) return;
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.id);
                    });
                    tenementId = items.join(',');
                }
                var result = TenementService.deleteByIds({ids: tenementId});
                AlertFactory.handle($scope, result, function (data) {
                    $scope.query();
                    AlertFactory.success($scope, null, '删除成功!');
                });
            });
        };
        //暂停
        $scope.pause = function (tenementId) {
            ModalFactory.confirm({
                scope: $scope,
                content: '暂停'
            }, function () {
                var result = TenementService.pause({id: tenementId});
                AlertFactory.handle($scope, result, function () {
                    $scope.pager.load();
                    AlertFactory.success($scope, null, '成功暂停!');
                })
            });
        };


        //关闭
        $scope.close = function (tenementId) {
            ModalFactory.confirm({
                scope: $scope,
                content: '关闭'
            }, function () {
                var result = TenementService.close({id: tenementId});
                AlertFactory.handle($scope, result, function () {
                    $scope.pager.load();
                    AlertFactory.success($scope, null, '成功关闭!');
                })
            });
        };

        var destroy = $scope.$watch('condition', function (value, oldvalue) {
            if (value === oldvalue) return;
            Debounce.delay($scope.query, 400);
        }, true);
        $scope.$on('$destroy', destroy);
    })
})(angular, jQuery);

