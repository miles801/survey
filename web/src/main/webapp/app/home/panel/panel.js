(function (window, angular, $) {
    //获取模块
    var app = angular.module("eccrm.panel.base.list", [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.employee',
        'eccrm.base.user'
    ]);


    //
    app.controller('BaseCtrl', function ($http, User, EmployeeService, $scope, CommonUtils, AlertFactory) {
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

    });


})(window, angular, jQuery);
