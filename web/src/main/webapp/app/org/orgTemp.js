/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (angular) {
    var app = angular.module("base.orgtemp", [
        'ngResource',
        'eccrm.angular'
    ]);

    app.service('OrgTempService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/base/orgTemp/:method'), {}, {

            // 根据id查询机构员工视图信息
            // 必须参数：
            // id：组织机构的uuid，对应属性为mdpGuId
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 查询指定机构下的子组织机构
            // 可选参数：
            // id：组织机构的orgId。如果该参数为空，则表示查询第一级组织机构
            queryChildren: {method: 'GET', params: {method: 'queryChildren', id: '@id'}, isArray: false},

            // 查询指定机构下状态为有效的子组织机构
            // 可选参数：
            // id：组织机构的orgId。如果该参数为空，则表示查询第一级组织机构
            queryValidChildren: {method: 'GET', params: {method: 'validChildren', id: '@id'}, isArray: false}
        });
    });

})(angular);
