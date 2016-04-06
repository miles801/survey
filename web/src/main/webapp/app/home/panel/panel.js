(function (window, angular, $) {
    //获取模块
    var app = angular.module("eccrm.panel.base.list", [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.employee',
        'eccrm.base.param',
        'eccrm.base.user',
        'eccrm.knowledge.survey'    // 试卷
    ]);


    //
    app.controller('BaseCtrl', function ($http, User, EmployeeService, $scope, CommonUtils, AsideFactory, ModalFactory, AlertFactory, SurveyService, ParameterLoader) {
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

                // 获取所有的IP
                if ($scope.beans.positionCode === 'SYS') {
                    $scope.ips = [];
                    ParameterLoader.loadBusinessParam('IP', function () {
                        $scope.ips = arguments[0];
                    });

                    // 定时查询在线考试的用户列表
                    var repeatQuery = function () {
                        SurveyService.queryOnline(function (online) {
                            setTimeout(repeatQuery, 15000);
                            online = online.data || [];
                            angular.forEach($scope.ips || [], function (ip) {

                                var isOnline = false;
                                for (var i = 0; i < online.length; i++) {
                                    if (ip.value == online[i].ip) {
                                        isOnline = true;
                                        ip.nowId = online[i].id;
                                        break;
                                    }
                                }
                                ip.isOnline = isOnline;
                                // 下线则表示刚答题结束
                                if (!isOnline && ip.nowId) {
                                    SurveyService.score({id: ip.nowId}, function (data) {
                                        ip.nowId = null;
                                        data = data.data || {};
                                        AsideFactory.info({
                                            title: '通知',
                                            content: ip.name + '(' + data.empName + ')答完题了，分数为:' + data.score + '!'
                                        });
                                    });
                                }

                            });
                        });
                    };
                    repeatQuery();
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
