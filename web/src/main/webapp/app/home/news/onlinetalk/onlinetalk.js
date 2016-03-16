/**
 * Created by lzm on 2014-03-06 10:16:03.
 */

(function (angular) {
    var app = angular.module("eccrm.home.onlinetalk", ['ngResource']);
    app.service('OnlinetalkService', function ($resource,CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/operating/operatingOnlineContact/:method/:id'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@attachmentIds'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update', id: '@id'}, isArray: false},
            //根据id查询白名单管理信息
            get: {method: 'GET', params: {id: '@id'}, isArray: false},
            //分页查询，返回{total:,data:[{},{}]}
            page: {method: 'POST', params: {method: 'page'}, isArray: false},
            //根据id字符串（使用逗号分隔多个值），删除对应的白名单管理，成功则返回{success:true}
            deleteByIds: {method: 'POST', params: {method: 'delete', ids: '@ids'}, isArray: false},
            queryUserReceiveList: {method: 'POST', params: {method: 'queryUserReceiveList', id: '@id'}, isArray: false},
            //收件箱
            queryInbox: {method: 'POST', params: {method: 'queryInbox', start: '@start', limit: '@limit'}, isArray: false},
            //发件箱
            queryLetter: {method: 'POST', params: {method: 'queryLetter', start: '@start', limit: '@limit'}, isArray: false},
            //注销
            updateStatus: {method: 'POST', params: {method: 'updateStatus', ids: '@ids'}, isArray: false},
            reply: {method: 'POST', params: {method: 'reply', id: '@id'}, isArray: false},
            saveReply: {method: 'POST', params: {method: 'saveReply'}, isArray: false},
            replyForwarding: {method: 'POST', params: {method: 'replyForwarding', id: '@id'}, isArray: false},
            detailReplyForwarding: {method: 'POST', params: {method: 'detailReplyForwarding', id: '@id'}, isArray: false},
            //查询所有的账户信息
            queryAllUsers: {method: 'GET', params: {method: 'queryUsers'}, isArray: false},
            //查询全部数据，组装成树
            tree: {method: 'GET', params: {method: 'tree'}, isArray: true},
            queryAllUsergroup: {method: 'POST', params: {method: 'queryAllUsergroup'}, isArray: false}
        })
    });
    app.service('OnlineLogService', function ($resource,CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/operating/operatingOnlineContactReadLog/:method/:id'), {}, {
            //保存阅读记录
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            saveRead: {method: 'GET', params: {method: 'saveRead', id: '@id'}, isArray: false},
            //更新
            update: {method: 'POST', params: {method: 'update', id: '@id'}, isArray: false},
            //根据id查询
            get: {method: 'GET', params: {id: '@id'}, isArray: false},
            getLog: {method: 'GET', params: {method: 'getLog', id: '@id'}, isArray: false},
            //分页查询，返回{total:,data:[{},{}]}
            page: {method: 'POST', params: {method: 'page'}, isArray: false},
            //根据id字符串（使用逗号分隔多个值），删除对应的白名单管理，成功则返回{success:true}
            deleteByIds: {method: 'POST', params: {method: 'delete', ids: '@ids'}, isArray: false},
            //保存回复记录
            saveReply: {method: 'POST', params: {method: 'saveReply', attachmentIds: '@attachmentIds'}, isArray: false}
        })
    });
    app.service('CommunicateConstant', ['Parameter', function (Parameter) {
        return {
            // 类型
            type: function (callback) {
                Parameter.businessItems('NEWS_TYPE', function (data) {
                    callback(data.data || []);
                });
            },
            // 状态
            status: function (callback) {
                Parameter.systemItems('SP_ONLINE_STATUS', function (data) {
                    callback(data.data || []);
                });
            }
        }
    }]);
})(angular);
