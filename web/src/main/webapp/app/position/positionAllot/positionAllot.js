/**
 * Created by chenl on 2014-10-14 15:56:00.
 */
(function (angular) {
    var app = angular.module("eccrm.position.positionAllot", ['ngResource',
    'eccrm.base.param']);
    app.service('PositionAllotService', function ($resource) {
        return $resource($('#contextPath').val() + '/position/positionParam/:method/:id', {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            //根据id查询岗位管理信息
            get: {method: 'GET', params: {id: '@id'}, isArray: false},

            queryBusiType: {method: 'POST', params: {method: 'queryBusiType', busiType: '@busiType', start: '@start', limit: '@limit', orderBy: '@orderBy', reverse: '@reverse'}, isArray: false},
            queryCasCode: {method: 'POST', params: {method: 'queryCasCode', busiType: '@busiType',orgType: '@orgType', start: '@start', limit: '@limit', orderBy: '@orderBy', reverse: '@reverse'}, isArray: false},
            query: {method: 'POST', params: {method: 'query', start: '@start', limit: '@limit', orderBy: '@orderBy', reverse: '@reverse'}, isArray: false},

            //根据id字符串（使用逗号分隔多个值），删除对应的岗位管理，成功则返回{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });
    app.service('positionAllotConstant', ['Parameter', function (Parameter) {
        return {
            // 系统
            busiType: function (callback) {
                Parameter.businessItems('BP_YETAI', function (data) {
                    callback(data.data || []);
                });
            },
            // 机构类型
            orgType: function (callback) {
                Parameter.businessItems('BP_ORGAN', function (data) {
                    callback(data.data || []);
                });
            }

        }
    }]);
})(angular);
