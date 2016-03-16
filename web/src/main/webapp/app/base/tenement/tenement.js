/**
 * Created by miles on 13-11-25.
 */
(function (angular) {
    var app = angular.module("eccrm.base.tenement", ['ngResource']);
    app.service('TenementService', function ($resource) {
        return $resource('base/tenement/:method', {}, {
            //保存
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@attachmentIds'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},
            //暂停
            pause: {method: 'POST', params: {method: 'pause', id: '@id'}, isArray: false},
            //关闭
            close: {method: 'POST', params: {method: 'close', id: '@id'}, isArray: false},
            //根据id查询租户信息
            get: {method: 'GET', params: {id: '@id', method: 'get'}, isArray: false},
            //根据id查询租户是否存在，返回{exists:true/false}
            getByNo: {method: 'POST', params: {method: 'sn', serialNo: '@serialNo'}, isArray: false},
            //分页查询，返回{total:,data:''}
            query: {method: 'POST', params: {method: 'query'}, isArray: false},
            //根据id字符串（使用逗号分隔多个值），删除对应的租户，成功则返回{success:true}
            deleteByIds: {method: 'POST', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });

    app.service('TenementConstant', function () {
        return {
            status: [
                {code: 1, name: '未启用'},
                {code: 2, name: '启用'},
                {code: 3, name: '暂停'},
                {code: 4, name: '关闭'},
                {code: 5, name: '注销'}
            ]
        }
    });

    app.filter('tenementStatusFilter', function (TenementConstant) {
        return function (value) {
            if (!value) return '';
            for (var i = 0; i < TenementConstant.status.length; i++) {
                if (TenementConstant.status[i].code == value) {
                    return TenementConstant.status[i].name;
                }
            }
            return '未知的状态[' + value + ']';
        }
    })
})(angular);
