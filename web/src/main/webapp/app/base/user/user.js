/**
 * Created by shenbb on 2014-02-01 20:15:20.
 */
(function (angular) {
    var app = angular.module("eccrm.base.user", [
        'ngResource',
        'eccrm.base.param'
    ]);
    app.service('User', function ($resource, CommonUtils) {

        //集合的查询方法都支持使用排序{orderBy:'',reverse:true/false}
        return $resource(CommonUtils.contextPathURL('base/user/:method'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            //更新
            update: {method: 'PUT', params: {method: 'update'}, isArray: false},

            // 注册新用户
            register: {method: 'POST', params: {method: 'register'}, isArray: false},

            //根据id查询账户信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            //检测用户密码是否正确,需要参数{password:''}，账户为当前账户
            checkPassword: {method: 'POST', params: {method: 'check-pwd'}, isArray: false},

            //更改密码，需要参数：{password:'...'}，账户为当前账户
            updatePassword: {method: 'POST', params: {method: 'update-pwd'}, isArray: false},

            //重置密码
            resetPassword: {method: 'POST', params: {method: 'reset-pwd', ids: '@ids'}, isArray: false},

            //删除/注销用户
            deleteByIds: {method: 'DELETE', params: {ids: '@ids', method: 'delete'}, isArray: false},

            //判断指定名称的用户是否存在
            hasName: {method: 'GET', params: {method: 'hasName', name: '@name'}, isArray: false},

            // 查询所有有效的用户
            // 返回数据：{success:true,data:{total:1,data:[]}}
            queryValid: {method: 'POST', params: {method: 'queryValid'}, isArray: false},

            //分页查询，返回data数据为：{total:,data:[]}
            query: {method: 'POST', params: {method: 'query', start: '@start', limit: '@limit'}, isArray: false},

            updatePwd: {method: 'POST', params: {method: 'updatePwd'}, isArray: false}
        })
    });
    app.service('UserConstant', ['Parameter', function (Parameter) {
        return {
            // 用户的状态
            status: function (callback) {
                Parameter.systemItems('SP_USER_STATE', function (data) {
                    callback(data.data || []);
                });
            },
            // 用户类型
            type: function (callback) {
                Parameter.systemItems('SP_USER_TYPE', function (data) {
                    callback(data.data || []);
                });
            },
            // 性别
            genders: function (callback) {
                Parameter.businessItems('BP_SEX', function (data) {
                    callback(data.data || []);
                });
            }
        }
    }]);

})(angular);
