/**
 * Created by chenl on 2014-10-21 11:44:21.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.base.contactMethod.list', ['eccrm.base.contactMethod',
        'eccrm.angular',
        'eccrm.angularstrap']);
    app.controller('ContactMethodListController', function ($scope, $window, ContactMethodService) {
        //初始化参数
        //查询数据
        $scope.query = function () {
            $scope.pager.query();
        }
        //回到上一个页面
        $scope.back = function () {
            $window.history.back();
        }
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {start: this.start, limit: this.limit}, $scope.condition);
                $scope.contactMethods = ContactMethodService.pageQuery(param);
                return $scope.contactMethods;
            }
        }

    });
})(window, angular, jQuery);