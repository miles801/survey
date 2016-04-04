(function (window, angular, $) {
    var app = angular.module('spec.survey.import', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.knowledge.survey'
    ]);

    //
    app.controller('Ctrl', function ($scope, ModalFactory, CommonUtils, AlertFactory, SurveyService) {
        $scope.beans = {};

        $scope.importData = function () {
            var ids = $scope.fileUpload.getAttachment() || [];
            if (ids && ids.length < 1) {
                AlertFactory.error(null, '请上传数据文件!');
                return false;
            }
            var promise = SurveyService.importSubject({
                attachmentIds: ids.join(','),
                type: $scope.subjectType
            }, function () {
                AlertFactory.success('导入成功!');
                $scope.canImport = false;
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

    app.filter('subjectType', function () {
        return function (value) {
            if (!value) {
                return '';
            }
            if (value == '1') {
                return '单选题';
            } else if (value == '2') {
                return '多选题'
            } else if (value == '3') {
                return '判断题';
            } else if (value == '4') {
                return '填空题';
            }
            return '';
        }
    });
})(window, angular, jQuery);