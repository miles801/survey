(function (window, angular, $) {
    var app = angular.module('eccrm.knowledge.survey.edit', [
        'eccrm.knowledge.survey',
        'knowledge.survey.category',
        'eccrm.angular',
        'eccrm.angular.ztree',
        'eccrm.angularstrap'
    ]);

    // 入口
    app.controller("SurveyEditCtrl", function ($scope, Survey, CommonUtils, SurveyService, AlertFactory, SubjectCategoryTree) {
        var pageType = $("#pageType").val();    // 页面类型
        var id = $("#id").val();                // ID
        var beans = $scope.beans = {};          // 数据区
        var editor;

        // 页签
        /**
         *
         * @param id 试卷ID
         * @param pageType 页面类型，值为新增、更新、查看
         */
        var initTab = function (id, pageType) {
            if (beans.isRandomSubject == true) {
                return;
            }
            var tabs = [
                {
                    title: '试卷题目',
                    isRoot: true,
                    url: 'app/knowledge/survey/subject/list/survey_subject.jsp?surveyId=' + id + '&pageType=' + pageType,
                    targetObj: window.self
                }
            ];
            angular.forEach(tabs, function (tab) {
                CommonUtils.addTab(tab);
            });
        };

        $scope.categoryTree = SubjectCategoryTree.pick(function (o) {
            $scope.beans.categoryId = o.id;
            $scope.beans.categoryName = o.name;
        });

        $scope.clearCategory = function () {
            $scope.beans.categoryId = null;
            $scope.beans.categoryName = null;
        };

        var check = function () {
            var navContent = editor.html();
            if ($scope.beans.showNavPage == true && !navContent) {
                CommonUtils.errorDialog('请填写”前导页内容“!');
                return false;
            }
            $scope.beans.navContent = navContent;
            return true;
        };

        // 更新
        $scope.update = function () {
            if (!check()) {
                return;
            }
            var promise = SurveyService.update($scope.beans, function (data) {
                if (data && data.success) {
                    AlertFactory.success($scope, '更新成功!');
                } else {
                    AlertFactory.error($scope, '[' + (data && data.error || data.fail || '') + ']', '更新失败!');
                }
            });
            CommonUtils.loading(promise, '更新中...');
        };

        // 保存
        $scope.save = function () {
            if (!check()) {
                return;
            }
            var promise = SurveyService.save($scope.beans);
            CommonUtils.loading(promise, '保存中...', function (data) {
                if (data && data['success']) {
                    AlertFactory.success($scope, null, '保存成功!');
                    $scope.pageType = 'modify'; // 更改页面类型
                    $scope.beans.id = data.data;
                    initTab(data.data, 'add');
                } else {
                    AlertFactory.saveError($scope, data);
                }
            });
        };

        // 返回
        $scope.back = CommonUtils.back;

        var load = function () {
            var promise = SurveyService.get({id: id});
            CommonUtils.loading(promise, '加载中...', function (data) {
                $scope.beans = data.data;
                editor.html($scope.beans.navContent);
            });
        };
// ----------------------------------------------------------------------------------------
// |                         定义区结束                                                     |
// ----------------------------------------------------------------------------------------

        // 初始化文本编辑器
        editor = KindEditor.create('#navPageContent');
        $scope.pageType = pageType;

        // 状态
        Survey.status(function (data) {
            data = data || [];
            $scope.status = data;
        });

        // 重置分数、题目总和
        var resetScore = function () {
            var beans = $scope.beans;
            beans.xzTotalScore = (beans.xzCounts || 0) * (beans.xzScore || 0);
            beans.dxTotalScore = (beans.dxCounts || 0) * ( beans.dxScore || 0);
            beans.pdTotalScore = (beans.pdCounts || 0) * ( beans.pdScore || 0);
            beans.tkTotalScore = (beans.tkCounts || 0) * ( beans.tkScore || 0);
            beans.jdTotalScore = (beans.jdCounts || 0) * ( beans.jdScore || 0);
            beans.totalSubjects = parseInt(beans.xzCounts || 0) + parseInt(beans.dxCounts || 0) + parseInt(beans.pdCounts || 0) + parseInt(beans.tkCounts || 0) + parseInt(beans.jdCounts || 0);
            beans.totalScore = beans.xzTotalScore + beans.dxTotalScore + beans.pdTotalScore + beans.tkTotalScore + beans.jdTotalScore;
        };
        if (pageType == 'view' || pageType == 'detail') {
            editor.readonly(true);
            initTab(id, 'view');
            load();
            $('input,select,textarea').attr('disabled', 'disabled');
        } else if (pageType == 'add') {
            beans.creatorName = CommonUtils.loginContext().employeeName;
            beans.createdDatetime = new Date().getTime();
            beans.status = 'ACTIVE';
            beans.xzCounts = 10;
            beans.xzScore = 2;
            beans.dxCounts = 5;
            beans.dxScore = 5;
            beans.pdCounts = 0;
            beans.pdScore = 0;
            beans.tkCounts = 0;
            beans.tkScore = 0;
            beans.jdCounts = 0;
            beans.jdScore = 0;
            beans.startTime = new Date().getTime();
            beans.isRandomSubject = true;     // 采用随机选题
        } else if (pageType == 'modify') {
            initTab(id, 'modify');
            load();
        }


        // 当值改变时，重置模型
        $scope.$watch(function () {
            var b = $scope.beans;
            return b.xzCounts + ',' + b.xzScore + ',' + b.dxCounts + ',' + b.dxScore + ',' + b.pdCounts + ',' + b.pdScore + ',' + b.tkCounts + ',' + b.tkScore + ',' + b.jdCounts + ',' + b.jdScore;
        }, resetScore);


    });
})(window, angular, jQuery);
