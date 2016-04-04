(function (window, angular, $) {
    var app = angular.module('spec.member.import', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.employee'
    ]);

    //
    app.controller('Ctrl', function ($scope, ModalFactory, CommonUtils, AlertFactory, EmployeeService) {
        $scope.beans = {};

        $scope.importData = function () {
            var ids = $scope.fileUpload.getAttachment() || [];
            if (ids && ids.length < 1) {
                AlertFactory.error(null, '请上传数据文件!');
                return false;
            }
            var promise = EmployeeService.importData({attachmentIds: ids.join(',')}, function () {
                AlertFactory.success('导入成功!');
            });
            CommonUtils.loading(promise, '导入中,请稍后...');
        };

        // 关闭当前页签
        $scope.back = CommonUtils.back;


        // 附件
        $scope.fileUpload = {
            maxFile: 3,
            canDownload: true,
            onSuccess: function () {
                $scope.$apply(function () {
                    $scope.canImport = true;
                });
            },
            swfOption: {
                fileTypeExts: '*.xls;*.xlsx'
            }
        };

    });
})(window, angular, jQuery);