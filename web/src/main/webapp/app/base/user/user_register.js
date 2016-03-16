(function (window, angular, $) {
    var app = angular.module('eccrm.base.user.register', [
        'eccrm.base.user',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, User, AlertFactory) {
        // 保存用户
        // 保存员工
        // 指定机构
        $scope.user = {};

        $scope.ok = function () {
            // 验证密码是否一致
            if ($scope.user.password !== $scope.user.password2) {
                AlertFactory.error(null, '两次输入的密码不一致!请重新输入!');
                return;
            }
            $scope.form.$setValidity('committed', false);
            var beans = angular.extend({}, $scope.user);
            delete beans.password2;
            beans.password = $.md5(beans.password);
            var promise = User.register(beans, function () {
                AlertFactory.success('注册成功!');
            }, function () {
                $scope.form.$setValidity('committed', true);
            });

            CommonUtils.loading(promise);

        };
    });
})(window, angular, jQuery);