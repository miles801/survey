/**
 * Created by CODE GENERATOR on 2014-03-07 13:49:36.
 */
(function (angular) {
    var app = angular.module("eccrm.user.loginlog", [
        'ngResource',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.service('LoginLogService', function ($resource) {
        return $resource('base/user/loginlog/:method', {}, {
            query: {method: 'POST', params: {method: 'query', start: '@start', limit: '@limit', orderBy: 'createdDatetime', reverse: true}, isArray: false},

            // 查询所有在线用户
            // 支持高级查询
            // 成功返回:{success:true,data:[]}
            onlineUsers: {method: 'POST', params: {method: 'onlineUsers'}, isArray: false}
        })
    });

    app.controller('LoginLogCtrl', function ($scope, LoginLogService, Debounce, $locale) {
        $scope.condition = {};
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                $scope.logs = LoginLogService.query(param);
                return $scope.logs;
            }
        };
        $scope.query = function () {
            $scope.pager.query();
        };
        var destroy = $scope.$watch('condition', function (value, oldValue) {
            Debounce.delay($scope.query, 400);
        }, true);

        $scope.$on('$destroy', destroy);
    });

    app.filter('logoutType', function () {
        return function (value) {
            if (!value) return '';
            if (value === 1) {
                return "正常退出";
            } else if (value === 2) {
                return "超时";
            } else if (value === 3) {
                return "强制下线";
            } else if (value == 4) {
                return "重新登录";
            } else if (value === 5) {
                return "系统关闭";
            } else {
                return "未知类型";
            }
        }
    }).filter('loginLogStatus', function () {
        return function (value) {
            if (!value) return "在线";
            return "离线";
        }
    });
})(angular);
