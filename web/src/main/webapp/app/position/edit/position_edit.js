/**
 * Created by chenl on 2014-10-14 16:01:46.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm..position.edit', ['eccrm.position.position', 'eccrm.angular', 'eccrm.angularstrap']);
    app.controller('PositionEditController', function ($scope, $window, PositionService, AlertFactory) {

        //回到上一个页面
        $scope.back = function () {
            $window.history.back();
        }
        var type = $('#pageType').val();
        var id = $('#id').val();
        //回到上一个页面
        $scope.add = function () {
            alert('2222');
            PositionModal.add({scope: $scope}, function (data) {
            });
        }
        //保存
        $scope.save = function (createNew) {
            PositionService.save($scope.position, function (data) {
                if (data && data['success'] == true) { //保存成功
                    $scope.back();
                } else {
                    AlertFactory.saveError($scope, data);
                }
            });
        }
        //更新
        $scope.update = function () {
            PositionService.update($scope.position, function (data) {
                if (data && data['success'] == true) { //更新成功
                    $scope.back();
                    return;
                }
                AlertFactory.updateError($scope, data);
            });
        }


        if (type == 'add') {
            $scope.position = {};
        } else if (type == 'modify' || type == 'update') {
            $scope.position = PositionService.get({id: id});
        } else if (type == 'detail') {
            $scope.position = PositionService.get({id: id});
            $('input,textarea,select').attr('disabled', 'disabled');
        }
    });
})(window, angular, jQuery);