/**
 * Created by miles on 2014/6/27.
 */
(function (angular) {
    var app = angular.module('eccrm.base.passwordpolicy.edit', [
        'eccrm.base.passwordpolicy'
    ]);
    app.controller('PasswordPolicyController', function ($scope, PasswordPolicyService, AlertFactory, PasswordPolicy, CommonUtils) {
        //定义变量

        $scope.effectivePeriod = PasswordPolicy.effectivePeriod;
        $scope.expireHandler = PasswordPolicy.expireHandler;

        //定义方法
        var checkChain = [function () {
            var result = PasswordPolicy.checkPassword($scope.pwd, $scope.pwd.defaultPassword);
            if (!result.success) {
                AlertFactory.error($scope, result.message);
                this.reject(result.message);
                return;
            }
            this.resolve();
        }];
        $scope.update = function () {
            CommonUtils.chain(checkChain, function () {
                var result = PasswordPolicyService.update($scope.pwd);
                AlertFactory.handle($scope, result, function () {
                    AlertFactory.success($scope, null, '更新成功!');
                });
            });
        };

        $scope.save = function () {
            CommonUtils.chain(checkChain, function () {
                var result = PasswordPolicyService.save($scope.pwd);
                AlertFactory.handle($scope, result, function () {
                    AlertFactory.success($scope, null, '更新成功!');
                });
            });
        };


        // 加载密码策略相关信息
        var promise = PasswordPolicyService.get(function (data) {
            if (data && data.id) {//有密码策略，则进行更新
                $scope.pwd = data;
            } else { //没有密码策略，则保存
                var defaults = {
                    modifiedDatetime: new Date().getTime(),
                    limitType: 1,
                    creatorId: CommonUtils.loginContext().id,
                    creatorName: CommonUtils.loginContext().username,
                    minLength: 3,
                    maxLength: 16,
                    effectivePeriod: 1,
                    expireHandler: 1
                };
                $scope.pwd = angular.extend({}, defaults);
            }
        });
        CommonUtils.loading(promise, '加载密码策略信息...');

    });
})(angular);