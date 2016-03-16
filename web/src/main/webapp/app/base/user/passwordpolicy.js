/**
 * Created by shenbb on 2014-02-01 20:15:20.
 */
(function (angular) {
    var app = angular.module("eccrm.base.passwordpolicy", [
        'ngResource',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.service('PasswordPolicyService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('base/user/passwordpolicy/:id/:method'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},
            //根据id查询账户信息
            get: {method: 'GET', params: {method: 'get'}, isArray: false}
        })
    });
    app.service('PasswordPolicy', function () {
            return {
                effectivePeriod: [
                    {code: 1, name: '一周'},
                    {code: 2, name: '一个月'},
                    {code: 3, name: '三个月'},
                    {code: 0, name: '永久有效'}
                ],
                expireHandler: [
                    {code: 1, name: '强制修改'}
                ],
                nextEndDate: function (effectivePeriod, date) {
                    date = date || new Date().getTime();
                    var day = 1000 * 60 * 60 * 24;//一天
                    if (effectivePeriod === 0) {
                        return -1;
                    } else if (effectivePeriod == 1) {
                        return date + 7 * day;
                    } else if (effectivePeriod === 2) {
                        return date + 30 * day;
                    } else if (effectivePeriod == 3) {
                        return date + 90 * day;
                    } else {
                        throw '无效的类型!';
                    }
                },
                checkPassword: function (policy, password) {
                    var result = {success: false, message: ''};
                    if (!policy) {
                        result.message = '没有获得密码策略对象';
                        return result;
                    }
                    if (password === undefined) {
                        result.message = '没有获得将被校验的密码';
                        return result;
                    }
                    //长度校验
                    if (password.length < policy.minLength || 0) {
                        result.message = '密码不足' + policy.minLength + '位';
                        return result;
                    }
                    if (password.length > policy.maxLength) {
                        result.message = '密码超出' + policy.maxLength + '位';
                        return result;
                    }
                    //必须包含字母
                    if (policy.limitType === 2) {
                        if (!/.*[a-zA-Z]+.*/.test(password)) {
                            result.message = '密码必须包含字母';
                            return result;
                        }
                    }
                    result.success = true;
                    return result;
                }
            }
        }
    );

})(angular);
