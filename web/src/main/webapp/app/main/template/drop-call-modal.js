(function (angular, window) {
    var app = angular.module('eccrm.mail.modal', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.param',
        'eccrm.customer.contactRecords'
    ]);
    app.factory('MailModal', function ($modal, CommonUtils, ContactRecordsService, AlertFactory, Parameter, BusinessParamItem) {
        var common = function (options, callback) {
            var defaults = {
                pageType: 'add',//页面类型，支持'add','view'
                scope: null,    //必选项
                callback: null, //点击确定后要执行的函数
                afterShown: null//模态对话框显示完成后要执行的函数
            };
            options = angular.extend({}, defaults, options);

            var scope = options.scope;
            if (!scope) throw '使用模态对话框时必须指定scope!';
            callback = callback || options.callback;

            var modal = $modal({
                scope: scope,
                backdrop: 'static',
                template: CommonUtils.contextPathURL('/app/main/template/drop_call.tpl.html')
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
            dropCall: function (options, callback) {
                options = options || {};
                options.pageType = 'add';
                var $scope = common(options, callback);
                var contRecordId = $scope.contRecordId;

                //联络结果
                Parameter.businessItems("BP_CONTACT_RESULT", function (data) {
                    $scope.resultType = data.data;
                });

                //联络事项
                Parameter.businessItems("BP_CONTACT_ITEM", function (data) {
                    $scope.contactItems = data.data || {};
                    $scope.contactItems.unshift({name: '请选择...'});
                });

                //联络事项--子类型
                $scope.contactSubType2 = function (value1) {
                    // 清除缓存数据
                    $scope.contactItems2 = null;
                    $scope.contactItems3 = null;
                    if (!value1) {
                        return;
                    }
                    // 获得级联编号和值
                    var type1;
                    var contactItems = $scope.contactItems;
                    for (var i = 0; i < contactItems.length; i++) {
                        if (contactItems[i].value == value1) {
                            type1 = contactItems[i].type;
                            break;
                        }
                    }
                    // 查询级联数据
                    Parameter.fetchBusinessCascade(type1, value1, function (data) {
                        if (data.data && data.data.length > 0) {
                            $scope.contactItems2 = data.data;
                            $scope.contactItems2.unshift({name: '请选择...'});
                        }
                    });
                };
                //联络事项--子子类型
                $scope.contactSubType3 = function (value2) {
                    // 清除之前的数据
                    $scope.contactItems3 = null;
                    if (!value2) {
                        return;
                    }
                    // 获得级联编号和值
                    var type2;
                    var contactItems2 = $scope.contactItems2;
                    for (var i = 0; i < contactItems2.length; i++) {
                        if (contactItems2[i].value == value2) {
                            type2 = contactItems2[i].type;
                            break;
                        }
                    }
                    // 查询级联数据
                    Parameter.fetchBusinessCascade(type2, value2, function (data) {
                        if (data.data && data.data.length > 0) {
                            $scope.contactItems3 = data.data;
                            $scope.contactItems3.unshift({name: '请选择...'});
                        }
                    });
                };

                var beans = $scope.beans = {};
                $scope.contResult = {};
                $scope.update = function () {
                    if (contRecordId != null && contRecordId != '' && contRecordId != 'undefined') {
                        var result = ContactRecordsService.get({id: contRecordId}, function (data) {
                            $scope.contResult = angular.extend(beans, $scope.contResult);
                            beans = result.data;
                            beans.code = window.parent.sheetNo;
                            beans.contactItem = $scope.contResult.contactItem;
                            beans.contactItem2 = $scope.contResult.contactItem2;
                            beans.contactItem3 = $scope.contResult.contactItem3;
                            beans.content = $scope.contResult.content;
                            beans.resultType = $scope.contResult.resultType;
                            beans.remark = $scope.contResult.remark;
                            ContactRecordsService.update(beans);
                            window.parent.sheetNo = null;
                            $scope.$hide();

                        });
                    }
                };
                $scope.close = function () {
                    $scope.$hide();
                };
            }
        }
    });
})(angular, window);