(function (window, angular, $) {
    var app = angular.module('knowledge.survey.subject.list', [
        'knowledge.survey.subject',
        'eccrm.base.param',
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.knowledge.survey',
        'knowledge.survey.subject.modal'
    ]);
    app.controller('SubjectListCtrl', function ($scope, CommonUtils, Subject, ModalFactory, SubjectService, SubjectModal, SurveyService) {
        // 试卷
        var surveyId = $('#surveyId').val();
        if (!surveyId) {
            CommonUtils.errorDialog('没有获取到试卷ID!');
            return false;
        }
        // 页面
        var pageType = $('#pageType').val();

        // 查询数据
        $scope.query = function () {
            var promise = SurveyService.querySubjects({surveyId: surveyId});
            CommonUtils.loading(promise, '加载中...', function (data) {
                data = data.data || [];
                $scope.beans = data;
            });
        };
        // 添加
        $scope.add = function () {
            var options = {surveyId: surveyId};
            SubjectModal.pick(options, function (data) {
                if (!data || data.length < 1) {
                    return;
                }
                var ids = [];
                angular.forEach(data, function (o) {
                    ids.push(o.id);
                });
                var promise = SurveyService.addSubjects({surveyId: surveyId, subjectIds: ids.join(',')});
                CommonUtils.loading(promise, '添加中...', $scope.query);
            });
        };

        // 改变了需要的
        $scope.sequenceChanged = false;

        // 设置下一题
        $scope.setNextSubject = function () {

        };

        // 设置为第一题
        $scope.setToFirst = function (subjectId) {
            ModalFactory.confirm({scope: $scope, keywords: '一个试卷只能有一个第一题,设置后,其他已经被设置成第一题的将会被取消,请确认!'}, function () {
                var promise = setToFirst.setToFirst({surveyId: subjectId, subjectId: subjectId});
                CommonUtils.loading(promise, '更新中...', $scope.query);
            });
        };


        // 删除
        $scope.remove = function (subjectId) {
            ModalFactory.remove($scope, function () {
                if (!subjectId) {
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.subjectId);
                    });
                    subjectId = items.join(',');
                }
                var promise = SurveyService.deleteSubjects({surveyId: surveyId, subjectIds: subjectId});
                CommonUtils.loading(promise, '删除中...', $scope.query);
            });
        };

        /**
         * 题目明细
         * @param subjectId
         */
        $scope.view = function (subjectId) {
            SubjectModal.view({id: subjectId});
        };


        var changed = {};
        // 上调
        $scope.up = function (index) {
            var bean = $scope.beans[index];
            bean.sequenceNo = (bean.sequenceNo || 0) + 1;
            changed[bean.id] = bean.sequenceNo;
            $scope.sequenceChanged = true;
        };

        // 下调
        $scope.down = function (index) {
            var bean = $scope.beans[index];
            bean.sequenceNo = (bean.sequenceNo || 0) - 1;
            changed[bean.id] = bean.sequenceNo;
            $scope.sequenceChanged = true;
        };

        // 保存
        $scope.save = function () {
            var promise = SurveyService.updateSubjectSequence(changed);
            CommonUtils.loading(promise, '保存中...', $scope.query);
        };

        $scope.query();


    });
})(window, angular, jQuery);