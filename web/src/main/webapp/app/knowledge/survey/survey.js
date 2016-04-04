/**
 * 试卷
 * Created by Michael on 2015/3/27.
 */
(function (angular) {
    var app = angular.module("eccrm.knowledge.survey", [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.param'
    ]);

    /**
     * 试卷
     */
    app.service('SurveyService', function ($resource, CommonUtils) {

        return $resource(CommonUtils.contextPathURL('/survey/:method'), {}, {

            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 导入指定题型的题目
            importSubject: {
                method: 'POST',
                params: {method: 'import', attachmentIds: '@attachmentIds', type: '@type'},
                isArray: false
            },

            // 发布
            // 必须参数：
            //  id：
            publish: {method: 'POST', params: {method: 'publish', id: '@id'}, isArray: false},

            // 根据id查询新增试卷信息
            get: {method: 'GET', params: {id: '@id', method: 'get'}, isArray: false},

            //分页查询，返回{total:,data:[{},{}]}
            pageQuery: {
                method: 'POST',
                params: {method: 'pageQuery', limit: '@limit', start: '@start'},
                isArray: false
            },

            // 根据id字符串（使用逗号分隔多个值）删除
            // 必须参数：
            //  ids：使用逗号分隔的多个id字符串
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // -------------------------------------- 关联题目操作 --------------------

            // 添加题目
            // 必须参数：
            //  surveyId：
            //  subjectIds:题目的id列表，多个id使用逗号进行分隔
            addSubjects: {method: 'POST', params: {method: 'addSubjects'}, isArray: false},

            // 查询指定试卷的所有题目
            // 必须参数：
            //  surveyId：试卷id
            querySubjects: {method: 'POST', params: {method: 'querySubjects', surveyId: '@surveyId'}, isArray: false},

            // 查询指定试卷的所有题目，并同时返回题目的所有选项
            // 必须参数
            //  surveyId：试卷ID
            querySubjectWithItems: {
                method: 'GET',
                params: {method: 'querySubjectWithItems', surveyId: '@surveyId'},
                isArray: false
            },

            // 给指定试卷的指定题目设置为第一题
            // 必须参数：
            //  surveyId:试卷ID
            //  subjectId:题目ID
            setToFirst: {
                method: 'POST',
                params: {method: 'setToFirst', surveyId: '@surveyId', subjectId: '@subjectId'},
                isArray: false
            },

            // 更新题目在试卷的序号
            // 接收参数：{id：'试卷题目id',sequenceNo:1}
            updateSubjectSequence: {
                method: 'POST',
                params: {method: 'updateSubjectSequence'},
                isArray: false
            },
            // 移除题目
            // 必须参数：
            //  surveyId：试卷ID
            //  subjectIds：题目ID（多个值使用逗号进行分隔）
            deleteSubjects: {
                method: 'DELETE',
                params: {method: 'deleteSubjects', surveyId: '@surveyId', subjectIds: '@subjectIds'},
                isArray: false
            },

            // 获取所有个人可注册的试卷
            canregister: {method: 'GET', params: {method: 'canregister'}, isArray: false},
            // 试卷注册
            // 必须参数：原始试卷的ID
            register: {method: 'POST', params: {method: 'register', id: '@id'}, isArray: false},

            // 获取所有个人未完成的试卷
            unfinished: {method: 'GET', params: {method: 'unfinished'}, isArray: false},

            // 答题时，获取指定试卷的下一题
            // 必须参数：注册试卷的ID
            nextsubject: {method: 'GET', params: {method: 'nextsubject', id: '@id'}, isArray: false},

            // 答题
            // 必须参数：原始试卷的ID、答案
            answer: {method: 'POST', params: {method: 'answer'}, isArray: false},

            // 获取所有个人已经完成的试卷
            finished: {method: 'GET', params: {method: 'finished'}, isArray: false},

            // 获取个人指定试卷的答题情况
            score: {method: 'GET', params: {method: 'score', id: '@id'}, isArray: false}

        })
    });

    /**
     * 用户答题结果
     */
    app.service('SurveyAnswerService', function ($resource, CommonUtils) {

        return $resource(CommonUtils.contextPathURL('/survey/answer/:method'), {}, {

            // 批量保存
            batchSave: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 查询用户的答题结果
            // 必须参数：
            //  surveyId：试卷ID
            //  businessId：业务ID
            //  userId：答题人ID
            queryResult: {
                method: 'GET',
                params: {method: 'result', surveyId: '@surveyId', businessId: '@businessId', userId: '@userId'},
                isArray: false
            },
            // 查询指定试卷的所有答题
            // 必须参数：
            //  surveyId：试卷ID
            queryBySurveyId: {method: 'GET', params: {method: 'queryBySurveyId', surveyId: '@surveyId'}, isArray: false}

        })
    });

    /**
     * 提供参数的接口
     */
    app.service('Survey', function (ParameterLoader) {
        return {
            status: function (callback) {
                ParameterLoader.loadSysParam('SP_SURVEY_STATUS', callback, '加载数据字典...');
            }
        };
    });


    /**
     * 提供弹出层服务
     */
    app.service('SurveyModal', function ($modal, CommonUtils, ModalFactory, AlertFactory, SurveyService) {
        return {
            /**
             * 单选试卷
             * 配置项
             *  callback:推荐项,function，选择一个试卷后的回调，该方法接收一个参数，为被选择的试卷对象
             * @param options
             */
            pickOne: function (options) {
                var modal = $modal({
                    template: CommonUtils.contextPathURL('/app/knowledge/survey/template/survey-pickone-modal.html')
                });
                var $scope = modal.$scope;

                $scope.query = function () {
                    $scope.pager.query();
                };

                // 预览
                $scope.preview = function (id) {
                    CommonUtils.addTab({
                        title: '预览试卷',
                        url: "/survey/preview?id=" + id
                    });
                };

                // 选定
                $scope.confirm = function () {
                    var callback = options && options.callback;
                    if (angular.isFunction(callback)) {
                        callback($scope.selected);
                    }
                    $scope.$hide();
                };

                // 初始化分页插件
                $scope.pager = {
                    fetch: function () {
                        // 只查询已发布的试卷
                        var param = angular.extend({status: 'PUBLISHED'}, $scope.condition, {
                            start: this.start,
                            limit: this.limit
                        });
                        return CommonUtils.promise(function (defer) {
                            var promise = SurveyService.pageQuery(param);
                            CommonUtils.loading(promise, '加载中...', function (data) {
                                data = data.data || {total: 0};
                                defer.resolve(data);
                                $scope.beans = data;
                            });
                        });
                    }
                };

                /**
                 * 初始化
                 */
                var init = function () {

                };
                ModalFactory.afterShown(modal, init);
            }
        }
    });
})(angular);
