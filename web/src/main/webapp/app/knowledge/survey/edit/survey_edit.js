(function (window, angular, $) {
    var app = angular.module('eccrm.knowledge.survey.edit', [
        'eccrm.knowledge.survey',
        'eccrm.angular',
        'eccrm.angular.ztree',
        'eccrm.angularstrap'
    ]);

    // 入口
    app.controller("SurveyEditCtrl", function ($scope, Survey, CommonUtils, SurveyService, AlertFactory) {
        var pageType = $("#pageType").val();    // 页面类型
        var id = $("#id").val();                // ID
        var beans = $scope.beans = {};          // 数据区
        var editor;

        // 页签
        /**
         *
         * @param id 调查问卷ID
         * @param pageType 页面类型，值为新增、更新、查看
         */
        var initTab = function (id, pageType) {
            var tabs = [
                {
                    title: '问卷题目',
                    isRoot: true,
                    url: 'app/knowledge/survey/subject/list/survey_subject.jsp?surveyId=' + id + '&pageType=' + pageType,
                    targetObj: window.self
                }
            ];
            angular.forEach(tabs, function (tab) {
                CommonUtils.addTab(tab);
            });
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

        if (pageType == 'view' || pageType == 'detail') {
            editor.readonly(true);
            initTab(id, 'view');
            load();
            $('input,select,textarea').attr('disabled', 'disabled');
        } else if (pageType == 'add') {
            $scope.beans.creatorName = CommonUtils.loginContext().employeeName;
            $scope.beans.createdDatetime = new Date().getTime();
            $scope.beans.status = 'ACTIVE';
        } else if (pageType == 'modify') {
            initTab(id, 'modify');
            load();
        }

    });
})(window, angular, jQuery);
