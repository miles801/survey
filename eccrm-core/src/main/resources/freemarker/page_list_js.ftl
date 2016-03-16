/**
* Created by ${author!'CODE GENERATOR'} <#if current??>on ${current}</#if>.
*/
(function (window, angular, $) {
    var app = angular.module('${name}.${entity}.list', [
        'eccrm.angular',
        'eccrm.angularstrap',
        '${name}.${entity}'
    ]);
    app.controller('Ctrl', function ($scope, CommonUtils, ${className}Service) {
        $scope.condition = { };

        //查询数据
        $scope.query = function() {
            $scope.pager.query();
        };

    <#if listPage.allowPager==true>
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, {start: this.start, limit: this.limit}, $scope.condition);
                $scope.beans = [];
                return CommonUtils.promise(function(){
                    var promise = ${className}Service.pageQuery(param, function(data){
                        param = null;
                        $scope.beans = data.data || {total: 0};
                        defer.resolve($scope.beans);
                    });
                    CommonUtils.loading(promise, 'Loading...');
                });
            }
        }
    </#if>

        // 删除或批量删除
        $scope.remove = function (id) {
            ModalFactory.confirm({
                scope: $scope,
                content: '确定要执行【批量启动】操作吗?',
                callback: function () {
                    var ids = id || getCheckedIds();
                    var promise = KnowledgeService.batchActive({ids: ids}, $scope.query);
                    CommonUtils.loading((promise));
                }
            });
        };

        // 新增
        $scope.add = function () {
            CommonUtils.addTab({
                title: '新增',
                url: '/oa/knowledge/add',
                onUpdate: $scope.query
            });
        };

        // 更新
        $scope.modify = function (id) {
            CommonUtils.addTab({
                title: '更新',
                url: '/oa/knowledge/modify?id=' + id,
                onUpdate: $scope.query
            });
        };


    });
})(window, angular, jQuery);