
(function (angular, $) {
    var app = angular.module("wbs.org.business.code", [
        'ngResource',
        'eccrm.base.param'// 参数
    ]);
    app.service('OrgConstant', ['Parameter', function (Parameter) {
        return {
            // 知识状态
            orgType: function (callback) {
                Parameter.businessItems('BP_ORGAN', function (data) {
                    callback(data.data || []);
                });
            },
            // 知识类型
            bussitypeId: function (callback) {
                Parameter.businessItems('BP_YETAI', function (data) {
                    callback(data.data || []);
                });
            }
        }
    }]);


})(angular,jQuery);
