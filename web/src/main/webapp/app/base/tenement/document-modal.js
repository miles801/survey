/**
 * Created by miles on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 */


(function (angular, window) {
    var app = angular.module('eccrm.base.document.modal', [
        'eccrm.attachment',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.service('Document', function ($resource) {
        return $resource('base/tenement/docs/:method', {}, {
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@attachmentIds'}},
            //根据id列表删除文档
            deleteByIds: {method: 'GET', params: {method: 'delete', ids: '@ids'}},

            get: {method: 'GET', params: {method: 'get', id: '@id'}},
            //查询指定租户下的所有文档
            queryByTenement: {method: 'GET', params: {method: 'list', tenementId: '@tenementId'}}
        });
    });
    app.service('DocumentConstant', function () {
        return {
            docTypes: [
                {code: 'contract_protocol', name: '合同协议'},
                {code: 'business_license', name: '营业执照'},
                {code: 'certificate', name: '法人证件'}
            ]
        };
    });

    app.filter('docType', function (DocumentConstant) {
        var docTypes = DocumentConstant.docTypes;
        return function (value) {
            if (!value) return '';
            var i = 0;
            for (; i < docTypes.length; i++) {
                if (value === docTypes[i].code) {
                    return docTypes[i].name;
                }
            }
            return '未知的类型!';
        };
    });
    app.factory('DocumentModal', function ($modal, Document, AlertFactory, DocumentConstant, AttachmentService, CommonUtils) {
            var common = function (options, callback) {
                var defaults = {
                    pageType: 'add',//页面类型，支持'add','view'
                    scope: null,//必选项
                    callback: null,//点击确定后要执行的函数
                    afterShown: null//模态对话框显示完成后要执行的函数
                };
                options = angular.extend({}, defaults, options);

                var scope = options.scope;
                if (!scope) throw '使用模态对话框时必须指定scope!';
                callback = callback || options.callback;

                var modal = $modal({scope: scope, backdrop: 'static', template: 'app/base/tenement/template/document-edit-modal.html'});
                var $scope = modal.$scope;
                $scope.pageType = options.pageType;
                if ("add|modify|view".indexOf($scope.pageType) == -1) {
                    CommonUtils.errorDialog('不合法的了页面类型!');
                    throw '不合法的页面类型!';
                }
                $scope.docTypes = DocumentConstant.docTypes;
                return $scope;
            };
            return {
                add: function (options, callback) {
                    options = options || {};
                    options.pageType = 'add';
                    var $scope = common(options, callback);
                    $scope.uploadOptions = {
                        minFiles: 1
                    };

                    $scope.beans = {
                        docType: 'contract_protocol',
                        createdDatetime: new Date().getTime(),
                        creatorName: window.top.getCrmContext().getUsername()
                    };
                    $scope.ok = function () {
                        var attachmentIds = $scope.uploadOptions.getIds();
                        if (attachmentIds.length > 0) {
                            $scope.beans.attachmentIds = attachmentIds.join(',');
                        }
                        var result = Document.save($scope.beans);
                        AlertFactory.handle($scope, result, function (data) {
                            if (angular.isFunction(callback)) {
                                callback.call($scope, data);
                            }
                            $scope.$hide();
                        });
                    }
                },
                view: function (options) {
                    options = options || {};
                    var id = options.id;
                    if (!id) {
                        throw '没有获得id!';
                    }
                    options.pageType = 'view';
                    var $scope = common(options);
                    //加载数据
                    (function () {
                        var result = Document.get({id: id});
                        AlertFactory.handle($scope, result, function (data) {
                            $scope.beans = data.data;
                            $('select,input,textarea', '.modal').attr('disabled', 'disabled');
                        })
                    })();
                    //加载附件
                    $scope.uploadOptions = {
                        disabled: true,
                        businessId: id
                    };
                }
            }
        }
    )
    ;
})(angular, window);