/**
 * 试卷答题、预览页面
 * 通过pageType来判断页面的类型
 * Created by Michael on 2015/3/27.
 */
(function (angular) {
    var app = angular.module("eccrm.knowledge.survey.preview", [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.param',
        'eccrm.knowledge.survey'
    ]);
    app.controller('SurveyPreviewCtrl', function ($scope, CommonUtils, AlertFactory, SurveyService) {
        // 试卷ID
        var surveyId = $('#surveyId').val();
        if (!surveyId) {
            AlertFactory.error($scope, '没有获得试卷ID!', '页面初始化失败');
            return false;
        }
        var surveyName = $('#surveyName').val();
        // 业务类型
        var businessId = $('#businessId').val();
        var userId = $('#userId').val();
        var userName = $('#userName').val();
        var userType = $('#userType').val();
        var batchId = $('#batchId').val();
        // 页面类型
        var pageType = $('#pageType').val();
        if ("ANSWER|PREVIEW|VIEW".indexOf(pageType) === -1) {
            AlertFactory.error($scope, '错误的页面类型!', '页面初始化失败');
            return false;
        }
        var surveyReportId;
        if (pageType == 'ANSWER') {
            surveyReportId = $('#surveyReportId').val();
            if (!surveyReportId) {
                AlertFactory.error($scope, '没有获取试卷的ID!', '页面初始化失败');
                return false;
            }
        }

        $scope.pageType = pageType;

        // 保存所有的题目
        var subjects = null;

        /**
         * 验证并获得表单中的值！
         * 如果验证通过，则返回表单的数据对象
         * 如果验证不通过，则返回错误信息
         */
        var validate = function () {
            var subject = $scope.subjects[0];
            var answer = subject.answer;
            if (subject.subjectType == 2) {
                var checked = [];
                angular.forEach(subject.items, function (o) {
                    if (o.isSelected == true) {
                        checked.push(o.id);
                    }
                })
                answer = checked.join(',');
            }

            if (!answer) {
                return '请填写答案后再提交!';
            }
            //
            var data = {
                id: subject.surveyReportDetailId,
                answer: answer
            };
            return data;
        };

        /**
         * 可以下一页的条件
         * 多页显示 && 不是最后一页
         */
        $scope.next = function () {
            $scope.beans.splice(0, 1, subjects[++$scope.index]);
        };

        /**
         * 可以上一页的条件
         * 多页显示 && 不是第一页
         */
        $scope.prev = function () {
            $scope.beans.splice(0, 1, subjects[--$scope.index]);
        };

        // 提交答案
        $scope.commitAnswer = function () {
            var data = validate();
            if (typeof data === 'string') {
                CommonUtils.errorDialog(data);
            } else {
                var promise = SurveyService.answer(data, function (resp) {
                    querySubject();
                });
                CommonUtils.loading(promise, '保存中...')
            }
        };


        // 查询试卷题目
        var querySubject = function () {
            var promise = SurveyService.nextsubject({id: surveyReportId}, function (data) {
                // 得到该试卷所有的题目
                subjects = data.data;
                if (!subjects) {
                    AlertFactory.info('回答完毕!');
                    // 查看总成绩
                    var p = SurveyService.score({id: surveyReportId}, function (o) {
                        $scope.currentScore = o.data.score || 0;
                        alert('回答完毕!总分:' + o.data.score || 0);
                    });
                    CommonUtils.loading(p);
                } else {
                    $scope.currentIndex = subjects.currentIndex || 1;
                    $scope.currentScore = subjects.currentScore || 0;
                }
                // 按照分页显示类型获取值
                $scope.subjects = [subjects];
            });
            CommonUtils.loading(promise, '加载题目...');
        };
        if (pageType == 'ANSWER') {
            querySubject();
        }


        // 获取考卷信息
        var querySurvey = function (id) {
            var promise = SurveyService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
            });
            CommonUtils.loading(promise);
        };
        // --------------------------------- HANDLE ---------------------

        querySurvey(surveyId);

    });

    // 当输入的文字边长时，自动增加文本框的长度
    var autoIncreaseWidth = function (Debounce) {
        return {
            restrict: 'A',
            link: function (scope, ele) {
                var span = $('<span>');
                span.hide();
                ele.parent().append(span);
                ele.bind('keydown', function () {
                    Debounce.delay(function () {
                        // 将文本框的值赋值给隐藏的span，用于获得真实的长度
                        span.html(ele.val());
                        // 获得文本框中文字的真实长度
                        var textRealWidth = span.width();
                        // 文本框的长度-文字的真实长度如果小于80，则从真实长度+80
                        if (ele.width() - textRealWidth < 80) {
                            ele.animate({
                                width: textRealWidth + 80
                            }, 50)
                        }
                    }, 100);
                });
            }
        }
    };

    app.directive('eccrmAw', ['Debounce', autoIncreaseWidth]);
})
(angular);
