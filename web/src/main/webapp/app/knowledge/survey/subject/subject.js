/**
 * Created by liuxq on 2014-06-04 17:29:54.
 */
(function (angular) {
    var app = angular.module("knowledge.survey.subject", [
        'ngResource',
        'eccrm.angular'
    ]);

    app.service('SubjectService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/survey/subject/:method'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 根据id查询新增题目信息
            // 必须参数
            //  id
            get: {method: 'GET', params: {id: '@id', method: 'get'}, isArray: false},

            // 查询指定题目的选项
            // 必须参数:
            //  id:题目id
            queryItems: {method: 'GET', params: {id: '@id', method: 'items'}, isArray: false},


            // 根据id字符串（使用逗号分隔多个值），删除对应的新增题目
            // 必须参数：
            //  ids：使用逗号进行分隔的多个id字符串
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 分页查询，返回{total:,data:''}
            pageQuery: {method: 'POST', params: {method: 'query', start: '@start', limit: '@limit'}, isArray: false},

        })
    });


    app.service('Subject', function (ParameterLoader) {
        return {
            // 题型
            type: function (callback) {
                ParameterLoader.loadSysParam('SP_SURVEY_SUBJECT_TYPE', callback);
            },

            // 状态
            status: function (callback) {
                ParameterLoader.loadSysParam('SP_COMMON_STATE', callback);
            }

        }
    });
})(angular);