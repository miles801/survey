/**
 * 试卷答题、预览页面
 * 通过pageType来判断页面的类型
 * Created by Michael on 2015/3/27.
 */
(function (angular) {
    var app = angular.module("eccrm.knowledge.survey.preview", [
        'ngAnimate',    // 动画
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.param',
        'eccrm.knowledge.survey'
    ]);
    app.controller('SurveyPreviewCtrl', function ($scope, CommonUtils, AlertFactory, SurveyService) {

        $scope.danx = [];
        $scope.duox = [];
        $scope.pd = [];
        $scope.tk = [];
        // 试卷ID
        var surveyId = $('#surveyId').val();
        if (!surveyId) {
            AlertFactory.error($scope, '没有获得试卷ID!', '页面初始化失败');
            return false;
        }
        // 业务类型
        var userName = $('#userName').val();
        var userType = $('#userType').val();
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

        // 多选题的值发生变化时
        $scope.changeValue = function () {
            var bean = $scope.subjects[$scope.index];
            var checked = [];
            angular.forEach(bean.items || [], function (o) {
                if (o.isSelected === true) {
                    checked.push(o.id);
                }
            });
            bean.answer = checked.join(',');
        };

        /**
         * 可以下一页的条件
         * 多页显示 && 不是最后一页
         */
        $scope.next = function () {
            $scope.index++;
        };

        /**
         * 可以上一页的条件
         * 多页显示 && 不是第一页
         */
        $scope.prev = function () {
            $scope.index--;
        };

        $scope.changeIndex = function (index) {
            $scope.index = index;
        };

        $scope.finish = false;        // 是否答题完成
        // 提交答案
        $scope.commitAnswer = function () {
            var beans = [];
            angular.forEach($scope.subjects, function (o) {
                beans.push({
                    subjectId: o.id,
                    surveyReportId: surveyReportId,
                    answer: o.answer
                });
            });
            var promise = SurveyService.answer(beans, function (data) {
                data = data.data || {};
                $scope.finish = true;
                $scope.index = 0;              // 打完过后切换到第一题
                AlertFactory.success('答题完成!总得分:' + data.score);
                $scope.score = data.score;
                angular.forEach($scope.subjects, function (subject) {
                    var errors = data.errors;
                    for (var i = 0; i < errors.length; i++) {
                        if (subject.id == errors[i].subjectId) {   // 错题
                            subject.error = true;
                            if (subject.subjectType == '2') {       // 多选题的话
                                var rightAnswer = errors[i].rightAnswer.split(',');
                                angular.forEach(subject.items || [], function (item) {
                                    if ($.inArray(item.id, rightAnswer) != -1) {
                                        item.isRight = true;
                                    }
                                })
                            } else {
                                subject.rightAnswer = errors[i].rightAnswer;
                            }
                            break;
                        }
                    }

                });
            });
            CommonUtils.loading(promise, '提交中...');
        };


        // 查询试卷题目
        $scope.nowIndex = 0;  // 当前已答题目的序号


        var fillNumber = function (arr) {
            for (var i = 0; i < arr.length; i++) {
                arr[i] = i + 1;
            }
        };
        // 获取考卷信息
        var querySurvey = function (id) {
            var promise = SurveyService.get({id: id}, function (data) {
                $scope.beans = data.data || {};
                if ($scope.beans.finish) {
                    AlertFactory.error('该试卷已经完成!');
                    return;
                }
                $scope.danxuan = new Array($scope.beans.xzCounts || 0);
                $scope.duoxuan = new Array($scope.beans.dxCounts || 0);
                $scope.pd = new Array($scope.beans.pdCounts || 0);
                $scope.tk = new Array($scope.beans.tkCounts || 0);
                fillNumber($scope.danxuan);
                fillNumber($scope.duoxuan);
                fillNumber($scope.pd);
                fillNumber($scope.tk);
            });
            CommonUtils.loading(promise);

            // 获取考卷信息
            SurveyService.score({id: surveyReportId}, function (data) {
                data = data.data || {};
                if (data.finish) {
                    $scope.finish = true;
                    $scope.index = -1;              // 将索引切换到不存在的值
                    AlertFactory.success('答题完成!总得分:' + data.score);
                    $scope.score = data.score;
                    angular.forEach($scope.subjects, function (subject) {
                        var errors = data.errors;
                        for (var i = 0; i < errors.length; i++) {
                            if (subject.id == errors[i].subjectId) {   // 错题
                                subject.error = true;
                                if (subject.subjectType == '2') {       // 多选题的话
                                    var rightAnswer = errors[i].rightAnswer.split(',');
                                    angular.forEach(subject.items || [], function (item) {
                                        if ($.inArray(item.id, rightAnswer) != -1) {
                                            item.isRight = true;
                                        }
                                    })
                                } else {
                                    subject.rightAnswer = errors[i].rightAnswer;
                                }
                                break;
                            }
                        }

                    });
                }

            });

            // 获取所有题目
            SurveyService.querySubjectWithItems({
                surveyReportId: surveyReportId
            }, function (data) {
                $scope.subjects = data.data || [];
                $scope.xzSubjects = []; // 单选
                $scope.dxSubjects = []; // 多选
                $scope.pdSubjects = []; // 判断
                $scope.tkSubjects = []; // 填空
                angular.forEach($scope.subjects || [], function (o, index) {
                    o.index = index;
                    if (o.subjectType == '1') {
                        $scope.xzSubjects.push(o);
                    } else if (o.subjectType == '2') {
                        $scope.dxSubjects.push(o);
                    } else if (o.subjectType == '3') {
                        $scope.pdSubjects.push(o);
                    } else if (o.subjectType == '4') {
                        $scope.tkSubjects.push(o);
                    } else {
                        AlertFactory.error('不支持的题型!');
                    }
                });
                $scope.index = 0;
            });
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
