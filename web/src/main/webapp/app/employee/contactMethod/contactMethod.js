/**
 * Created by chenl on 2014-10-21 11:44:21.
 */
(function (angular) {
    var app = angular.module("eccrm.base.contactMethod", ['ngResource', 'mgcrea.ngStrap', 'eccrm.angularstrap']);
    app.service('ContactMethodService', function ($resource) {
        return $resource('contactMethod/:id/:method', {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            //根据id查询联络方式信息
            get: {method: 'GET', params: {id: '@id'}, isArray: false},

            //分页查询，返回{total:,data:[{},{}]}
            pageQuery: {method: 'POST', params: {method: 'page', limit: '@limit', start: '@start'}, isArray: false},

            //根据id字符串（使用逗号分隔多个值），删除对应的联络方式，成功则返回{success:true}
            deleteByIds: {method: 'POST', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });
})(angular);
