/**
 * Created by liuxq on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  usergroup.js
 */
(function (angular, window) {
    var app = angular.module('eccrm.soft.phone.modal', [
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.factory('SoftPhoneModal', function ($modal, $q, CommonUtils) {
        var common = function (options, callback) {
            var defaults = {
                pageType: 'add',//页面类型，支持'add','view'
                scope: null,//必选项
                stationId: '',// 必须（分机号）
                callback: null,//点击确定后要执行的函数
                afterShown: null//模态对话框显示完成后要执行的函数
            };
            options = angular.extend({}, defaults, options);

            var scope = options.scope;
            callback = callback || options.callback;

            var modal = $modal({
                scope: scope,
                backdrop: 'static',
                template: CommonUtils.contextPathURL('app/main/template/soft_phone_login.html')
            });
            var $scope = modal.$scope;
            $scope.pageType = options.pageType;
            if ("add|modify|view".indexOf($scope.pageType) == -1) {
                CommonUtils.errorDialog('不合法的了页面类型!');
                throw '不合法的页面类型!';
            }
            return $scope;
        };
        return {
            softPhone: function (options, callback) {
                var defaults = {
                    scope: null//必选项
                };
                options = options || {};
                options.pageType = 'add';
                var $scope = common(options, callback);
                $scope.close = function () {
                    $scope.$hide();
                };
                $scope.beans = {};
                $scope.readFile = function () {
                    var fileContext = window.parent.readConfigFile();
                    var vduStr = eval("(" + fileContext + ")");
                    $scope.beans = {
                        LoginID: vduStr.LoginID,        //工号
                        Password: vduStr.Password,      //密码
                        StationID: vduStr.StationID,    //分机
                        SDKServerURL: vduStr.SDKServerURL //服务地址
                    }
                };
                $scope.stationIds = options.stationId.split(',');
                $scope.readFile();
                if ($scope.beans && !$scope.beans.StationID) {
                    $scope.beans.StationID = $scope.stationIds[0];
                }
                $scope.save = function () {
                    window.parent.writeConfigFile($scope.beans.LoginID, $scope.beans.Password, $scope.beans.StationID, $scope.beans.SDKServerURL);
                    $scope.$hide();
                }
            }
        }
    });
})(angular, window);