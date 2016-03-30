(function (window, angular, $) {
    //获取模块
    var app = angular.module("eccrm.panel.base.list", [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.employee',
        'eccrm.base.user',
        'eccrm.knowledge.survey'    // 试卷
    ]);


    //
    app.controller('BaseCtrl', function ($http, User, EmployeeService, $scope, CommonUtils, ModalFactory, AlertFactory, SurveyService) {
        $scope.beans = {};
        $scope.isShow = false;
        var id = $('#userId').val();
        // 加载员工信息
        var loadEmployee = function () {
            var promise = EmployeeService.get({id: id}, function (data) {
                data = data.data || {};
                $scope.beans = data;
                var imageId = data.picture;
                if (imageId) {
                    $('#imageId').html('<img src="' + CommonUtils.contextPathURL('/attachment/view?id=' + imageId) + '" width="150" height="180">');
                } else {
                    $('#imageId').html('<img src="' + CommonUtils.contextPathURL('/style/standard/images/default_tx.png') + '" width="150" height="180">');
                }
            });
            CommonUtils.loading(promise);
        };

        loadEmployee();


        // 可注册试卷
        $scope.canRegisterData = [];
        $scope.queryCanRegisterSurvey = function () {
            var promise = SurveyService.canregister(function (data) {
                $scope.canRegisterData = data.data || [];
            });
            CommonUtils.loading(promise);
        };
        $scope.queryCanRegisterSurvey();

        // 已注册未完成试卷
        $scope.registerData = [];
        $scope.queryUnfinishSurvey = function () {
            var promise = SurveyService.unfinished(function (data) {
                $scope.registerData = data.data || [];
            });
            CommonUtils.loading(promise);
        };
        $scope.queryUnfinishSurvey();


        $scope.view = function () {

        };
        // 注册
        $scope.register = function (surveyId) {
            ModalFactory.confirm({
                scope: $scope,
                content: '是否确定参加这次考试,参加后将无法取消,请确认?',
                callback: function () {
                    var promise = SurveyService.register({id: surveyId}, function (data) {
                        AlertFactory.success('注册成功!');
                        $scope.queryUnfinishSurvey();
                        $scope.queryCanRegisterSurvey();
                    });
                    CommonUtils.loading(promise);
                }
            });
        };

        $scope.answer = function (id, surveyId) {
            window.location.href = CommonUtils.contextPathURL('/survey/preview?answer=true&id=' + surveyId + '&surveyReportId=' + id)
        };
    });


})(window, angular, jQuery);
