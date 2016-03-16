/**
 * Created by chenl on 2014-10-15 14:47:37.
 */
(function (angular, $) {
    var app = angular.module("eccrm.base.employee", [
        'ngResource',
        'eccrm.base.param'
    ]);

    var query = {method: 'query', start: '@start', limit: '@limit', orderBy: '@orderBy', reverse: '@reverse'};
    app.service('EmployeeService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/base/employee/:method/:id'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@picture'}, isArray: false},
            //更新
            update: {method: 'PUT', params: {method: 'update', attachmentIds: '@picture'}, isArray: false},

            //根据id查询人员管理信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},
            exists: {method: 'GET', params: {method: 'exists', extensionNumber: '@extensionNumber'}, isArray: false},
            getRTX: {method: 'GET', params: {method: 'RTX', extensionNumber: '@extensionNumber'}, isArray: false},

            //分页查询，返回{total:,data:[{},{}]}
            pageQuery: {method: 'POST', params: {method: 'page', limit: '@limit', start: '@start'}, isArray: false},

            // 带权限的分页查询
            permissionPageQuery: {
                method: 'POST',
                params: {method: 'permissionPageQuery', limit: '@limit', start: '@start'},
                isArray: false
            },

            query: {method: 'POST', params: query, isArray: false},

            // 查询有效的员工：正式、实习、调动中
            queryValid: {
                method: 'POST',
                params: {method: 'queryValid', limit: '@limit', start: '@start'},
                isArray: false
            },
            //根据id字符串（使用逗号分隔多个值），删除对应的人员管理，成功则返回{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });
    app.service('EmployeeConstant', ['Parameter', function (Parameter) {
        return {
            // 性别
            sex: function (callback) {
                Parameter.businessItems('BP_SEX', function (data) {
                    callback(data.data || []);
                });
            },
            // 证件类型
            idType: function (callback) {
                Parameter.businessItems('BP_CERTPAPER', function (data) {
                    callback(data.data || []);
                });
            },
            // 民族
            nation: function (callback) {
                Parameter.businessItems('BP_NATION', function (data) {
                    callback(data.data || []);
                });
            },
            // 国家
            country: function (callback) {
                Parameter.businessItems('BP_COUNTRY', function (data) {
                    callback(data.data || []);
                });
            },
            // 政治面貌
            zzmm: function (callback) {
                Parameter.businessItems('BP_ZZMM', function (data) {
                    callback(data.data || []);
                });
            },
            // 婚姻状况
            marriage: function (callback) {
                Parameter.businessItems('BP_MARRIAGE', function (data) {
                    callback(data.data || []);
                });
            },
            // 学历
            xueli: function (callback) {
                Parameter.businessItems('BP_EDU', function (data) {
                    callback(data.data || []);
                });
            },
            // 学位
            xuewei: function (callback) {
                Parameter.businessItems('BP_XW', function (data) {
                    callback(data.data || []);
                });
            },
            // 工作类型
            workType: function (callback) {
                Parameter.businessItems('BP_EMPTYPE', function (data) {
                    callback(data.data || []);
                });
            },
            // 职务
            duty: function (callback) {
                Parameter.businessItems('BP_ZHIW', function (data) {
                    callback(data.data || []);
                });
            },
            // 状态
            status: function (callback) {
                Parameter.businessItems('BP_YGZT', function (data) {
                    callback(data.data || []);
                });
            }

        }
    }]);
})(angular, jQuery);
