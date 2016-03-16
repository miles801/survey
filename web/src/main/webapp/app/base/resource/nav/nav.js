/**
 * Created by miles on 13-11-25.
 */
(function (angular) {
    var app = angular.module("eccrm.base.nav", [
        'ngResource',
        'eccrm.base.param'// 参数
    ]);
    app.service('MenuNavService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/base/nav/:method'), {}, {
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@attachmentIds'}, isArray: false},//保存

            update: {method: 'PUT', params: {method: 'update', attachmentIds: '@attachmentIds'}, isArray: false},//更新

            // 批量删除/注销
            //返回：{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 根据ID查询对象的信息
            // 返回{data:{}}
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},//根据id查询对象

            // 分页
            // 返回：{data:{total:0,data:[]}}
            pageQuery: {method: 'GET', params: {method: 'query', start: '@start', limit: '@limit'}, isArray: false},//根据id查询对象

            // 查询所有有效的导航菜单（启用状态）
            // 返回：{data:[]}
            queryValid: {method: 'GET', params: {method: 'queryValid'}, isArray: false},
            // 查询所有被授权且处于启用状态的导航菜单
            // 返回：{data:[]}
            queryAccreditValid: {method: 'GET', params: {method: 'queryValid'}, isArray: false}
        })
    });
    app.service('MenuNavConstant', function (CommonUtils, ParameterLoader) {
        return {
            types: function (callback) {
                ParameterLoader.loadSysParam('SP_NAV_TYPE', callback, '加载数据字典:导航菜单类型');
            },
            status: function (callback) {
                ParameterLoader.loadSysParam('SP_COMMON_STATE', callback, '加载数据字典:状态');
            },
            defaults: {
                sequenceNo: 0,
                status: 'ACTIVE',
                creatorId: CommonUtils.loginContext().id,
                creatorName: CommonUtils.loginContext().username,
                createdDatetime: new Date().getTime()
            }
        }
    });

})(angular);
