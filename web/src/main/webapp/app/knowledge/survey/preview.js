/**
 * 调查问卷答题、预览页面
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
    app.controller('SurveyPreviewCtrl', function ($scope, CommonUtils, AlertFactory, SurveyService, SurveyAnswerService) {
        // 调查问卷ID
        var surveyId = $('#surveyId').val();
        if (!surveyId) {
            AlertFactory.error($scope, '没有获得调查问卷ID!', '页面初始化失败');
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
        $scope.pageType = pageType;

        // 是否多页显示
        var multiPage = Boolean($('#multiPage').val());
        $scope.multiPage = multiPage;

        // 保存所有的题目
        var subjects = null;
        var hasLoad = CommonUtils.defer();
        // 初始导航按钮
        $scope.index = 0;
        $scope.total = 0;

        /**
         * 验证并获得表单中的值！
         * 如果验证通过，则返回表单的数据对象
         * 如果验证不通过，则返回错误信息
         */
        var validate = function () {
            var data = [];
            var i = 0, length = subjects.length || 0;
            for (i; i < length; i++) {
                var o = subjects[i];
                var isRequired = o.isRequired || false;
                var foo = {};
                if (o.subjectType == '2') { // 多选
                    var checked = [];
                    angular.forEach(o.items || [], function (foo) {
                        if (foo.isSelected == true) {
                            checked.push(foo.id);
                        }
                    });
                    if (isRequired && checked.length === 0) {
                        return "请填写必填项的值!";
                    }
                    foo.answer = checked.join(',');
                } else {
                    if (isRequired && (o.answer === "" || o.answer === undefined)) {
                        return "请填写必填项的值!";
                    }
                    foo.answer = o.answer;
                }
                // 组装数据
                foo.surveyId = surveyId;            // 问卷ID
                foo.surveyName = surveyName;        // 问卷名称
                foo.subjectId = o.id;               // 题目ID
                foo.subjectName = o.title;          // 题目标题
                foo.subjectType = o.subjectType;    // 题目类型
                foo.businessId = businessId;        // 业务ID
                foo.answerUserId = userId;          // 答题人ID
                foo.answerUserName = userName;      // 答题人名称
                foo.answerUserType = userType;      // 答题人类型
                foo.batchId = batchId;             // 批次ID
                data.push(foo);
            }
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
                var promise = SurveyAnswerService.batchSave(data, function (resp) {
                    if (resp && resp.success) {
                        AlertFactory.success(null, '回答完毕');
                        $scope.form.$setValidity('committed', false);
                    }
                });
                CommonUtils.loading(promise, '保存中...')
            }
        };

        // 回显已答题目
        var getAnswer = function () {
            // 问卷ID、答题人、业务ID
            var promise = SurveyAnswerService.queryResult({
                surveyId: surveyId,
                businessId: businessId,
                userId: userId
            }, function (data) {
                hasLoad.promise.then(function () {
                    data = data.data || [];
                    angular.forEach(subjects, function (o) {
                        for (var i = 0; i < data.length; i++) {
                            if (o.id === data[i].subjectId) {
                                var answer = data[i].answer;
                                if (o.subjectType == '2') {     // 多选
                                    angular.forEach(o.items, function (foo) {
                                        if (answer.indexOf(foo.id) > -1) {
                                            foo.isSelected = true;
                                        }
                                    });
                                } else if (o.subjectType == '3') {  // 判断
                                    o.answer = !!answer;
                                } else {
                                    o.answer = answer;
                                }


                                break;
                            }
                        }
                    });
                });
            });
            CommonUtils.loading(promise, 'Loading....');
        };


        // 查询问卷题目
        var querySubject = function () {
            var promise = SurveyService.querySubjectWithItems({surveyId: surveyId});
            CommonUtils.loading(promise, '加载题目...', function (data) {
                // 得到该调查问卷所有的题目
                subjects = data.data || [];
                hasLoad.resolve(true);
                var length = subjects.length;
                if (length == 0) {
                    CommonUtils.errorDialog('初始化失败!该问卷没有设置题目!');
                    $scope.form.$setValidity('init', false);
                    return false;
                }
                $scope.index = 0;
                $scope.total = length;
                // 按照分页显示类型获取值
                if (multiPage == true) {
                    $scope.beans = [subjects[0]];
                } else {
                    $scope.beans = subjects;
                }
            });
        };

        // --------------------------------- HANDLE ---------------------


        // 获取问卷的所有题目
        querySubject();

        // 如果答题完成，则浏览
        if (pageType == 'VIEW') {
            getAnswer();
        }


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
