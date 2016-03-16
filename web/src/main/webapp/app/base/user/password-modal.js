/**
 * Created by miles on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  usergroup.js
 */


(function (angular, window) {
    var app = angular.module('eccrm.base.user.password.modal', [
        'eccrm.base.user',
        'eccrm.base.passwordpolicy'
    ]);
    app.factory('PasswordModal', function ($modal, User, AlertFactory, PasswordPolicyService, $q, Debounce, PasswordPolicy, $filter, CommonUtils) {
            return {
                modifyPwd: function (cfg, callback) {
                    var defaults = {
                        scope: null,
                        callback: null//点击确定后要执行的函数
                    };
                    cfg = angular.extend({}, defaults, cfg);
                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    var modal = $modal({
                        backdrop: 'static',
                        scope: scope,
                        template: CommonUtils.contextPathURL('/app/base/user/template/password-modal-edit.tpl.html')
                    });
                    var $scope = modal.$scope;

                    $scope.ok = function () {
                        if ($scope.form.$invalid) {
                            AlertFactory.error($scope, '不合法的表单信息!', $scope.form.$error.join(','));
                            return;
                        }
                        var promise = User.updatePassword({
                            oldPwd: $.md5($scope.oldPassword),
                            newPwd: $.md5($scope.password)
                        }, function (data) {
                            if (data.success) {
                                callback = cfg.callback || callback();
                                if (callback && angular.isFunction(callback)) {
                                    callback();
                                }
                                $scope.$hide();
                            } else {
                                AlertFactory.error($scope, data.message, '密码修改失败!');
                            }
                        });
                        CommonUtils.loading(promise);
                    };
                }
            }
        }
    )
    ;
})(angular, window);