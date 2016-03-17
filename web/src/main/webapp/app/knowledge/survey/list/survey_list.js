(function (window, angular, $) {
    var app = angular.module('eccrm.knowledge.survey.list', [
        'eccrm.knowledge.survey',
        'eccrm.angular',
        'eccrm.angular.ztree',
        'eccrm.angularstrap'
    ]);
    app.controller('SurveyListCtrl', function ($scope, CommonUtils, SurveyService, Survey, AlertFactory, ModalFactory) {

        // 初始化分页信息
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
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

        $scope.query = function () {
            $scope.pager.query();
        };


        // 新增
        $scope.add = function () {
            CommonUtils.addTab({
                title: '新增试卷',
                url: "/survey/add",
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id, status) {
            if (status !== 'INACTIVE' && status !== 'ACTIVE') {
                CommonUtils.errorDialog('只有"启用/未启用"的试卷才可以更新!');
                return false;
            }
            CommonUtils.addTab({
                title: '更新试卷',
                url: "/survey/modify?id=" + id,
                onUpdate: $scope.query
            });
        };

        // 查看
        $scope.view = function (id) {
            CommonUtils.addTab({
                title: '查看试卷',
                url: "/survey/detail?id=" + id
            });
        };

        // 发布
        $scope.publish = function (id, status) {
            if (status !== 'ACTIVE') {
                CommonUtils.errorDialog('只有"启用"的试卷才可以发布!');
                return false;
            }
            ModalFactory.confirm({
                scope: $scope,
                content: '确认要发布该试卷?'
            }, function () {
                SurveyService.publish({id: id}, function (data) {
                    if (data && data.success) {
                        $scope.query();
                        AlertFactory.success($scope, null, '成功发布!');
                    } else {
                        AlertFactory.error($scope, '[' + (data['fail'] || data['error'] || '') + ']', '发布失败!');
                    }
                });
            });
        };


        // 参与调查
        $scope.preview = function (id, status, startDate, endDate) {
            if (status === 'INACTIVE') {
                CommonUtils.errorDialog('"未启用"的试卷不可以预览!');
                return false;
            }
            CommonUtils.addTab({
                title: '预览试卷',
                url: "/survey/preview?id=" + id
            });
        };

        // 删除
        $scope.remove = function (id) {
            ModalFactory.remove($scope, function () {
                if (!id) {
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.id);
                    });
                    id = items.join(',');
                }
                var result = SurveyService.deleteByIds({ids: id});
                CommonUtils.loading(result, '删除中...', function () {
                    AlertFactory.success($scope, null, '删除成功!');
                    $scope.query();
                });
            });
        };

// ----------------------------------------------------------------------------------------
// |                         定义区结束                                                     |
// ----------------------------------------------------------------------------------------
        $scope.beans = {};
        Survey.status(function (data) {
            data = data || [];
            data.unshift({name: '全部'});
            $scope.status = data;
        });


    });
})(window, angular, jQuery);