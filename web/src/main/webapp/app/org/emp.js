/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (angular) {
    var app = angular.module("eccrm.organi.Emp", ['ngResource', 'mgcrea.ngStrap', 'eccrm.angularstrap']);
    app.service('EmpService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/base/employee/:method'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            saveByTem: {method: 'POST', params: {method: 'saveByTem'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            //根据id查询机构员工视图信息
            get: {method: 'GET', params: {id: '@id'}, isArray: false},
            queryByOrgId: {method: 'GET', params: {method: 'queryByOrgId', id: '@id'}, isArray: true},
            queryByRuleId: {
                method: 'GET',
                params: {method: 'queryByRuleId', id: '@id', orgId: '@orgId'},
                isArray: false
            },

            //分页查询，返回{total:,data:[{},{}]}
            query: {method: 'POST', params: {method: 'query', limit: '@limit', start: '@start'}, isArray: false},
            querys: {method: 'POST', params: {method: 'querys', limit: '@limit', start: '@start'}, isArray: false},

            //根据id字符串（使用逗号分隔多个值），删除对应的机构员工视图，成功则返回{success:true}
            deleteByIds: {method: 'POST', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });
})(angular);
