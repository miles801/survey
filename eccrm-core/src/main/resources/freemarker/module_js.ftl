/**
 * ${cnName}
 * Created by ${author!'CODE GENERATOR'} <#if current??>on ${current}</#if>.
 */
(function (angular) {
    var app = angular.module('${name}.${entity}', [
        'ngResource',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);

    app.service('${className}Service', function (CommonUtils, $resource) {
        return $resource(CommonUtils.contextPathURL('/${entity}/:method'), {}, {
            // 保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 根据id查询信息
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 分页查询
            pageQuery: {method: 'POST', params: {method: 'pageQuery', limit: '@limit', start: '@start'}, isArray: false},

            // 根据id字符串（使用逗号分隔多个值）
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        })
    });

<#if param == true>
    app.service('${className}Param', function(ParameterLoader) {
        return {

        };
    });
</#if>

<#if modal == true>
    app.service('${className}Modal', function($modal, ModalFactory, AlertFactory, CommonUtils, ${className}Service) {
        var common = function (options, callback) {
            var defaults = {
                id: null,//id
                pageType: null,     // 必填项,页面类型add/modify/view
                callback: null,     // 点击确定后要执行的函数
                afterShown: null    // 模态对话框显示完成后要执行的函数
            };
            options = angular.extend({}, defaults, options);
            callback = callback || options.callback;
            var modal = $modal({template: CommonUtils.contextPathURL('app/base/parameter/template/param-type-modal.ftl.html'), backdrop: 'static'});
            var $scope = modal.$scope;
            var pageTypes = ['add', 'modify', 'view'];
            var pageType = $scope.pageType = options.pageType;
            if ($.inArray(options.pageType, pageTypes) == -1) {
                CommonUtils.errorDialog('不合法的页面类型!');
                throw '不合法的页面类型，仅支持[' + pageTypes.join(',') + ']类型!';
            }
            var id = options.id;
            $scope.save = function () {
                var promise = ${className}Service.save($scope.beans, function(data){
                    if (data && data.success){
                        if (callback && angular.isFunction(callback)) {
                            var o = angular.extend({}, $scope.beans);
                            callback(o);
                        }
                        $scope.$hide();
                    } else {
                        AlertFactory.saveError($scope, data);
                    }
                });
                CommonUtils.loading(promise, '保存中...');
            };

            $scope.update = function () {
                var promise = ${className}Service.update($scope.beans, function(data){
                    if (data && data.success) {
                        if (callback && angular.isFunction(callback)) {
                            var o = angular.extend({}, $scope.beans);
                            callback(o);
                        }
                        $scope.$hide();
                    }else{
                        AlertFactory.updateError($scope, data);
                    }
                });
                CommonUtils.loading(promise, '更新中...');
            };

            var load = function (id, callback){
                var promise = ${className}Service.get({id: id}, function(data) {
                    $scope.beans = data.data || {};
                    if (angular.isFunction(callback)) {
                        callback($scope.beans);
                    }
                });
                CommonUtils.loading(promise, 'Loading...');
            };

            if (pageType == 'add') {
                $scope.beans = {};
            } else if (pageType == 'modify') {
                load(id);
            } else {
                load(id, function () {
                    $('.modal-body').attr('disabled', 'disabled');
                });
            }
        }
        return {
            add: function (options, callback) {
                var o = angular.extend({}, options, {pageType: 'add'});
                common(o, callback);
            },
            modify: function (options, callback) {
                if (!options.id) {
                    CommonUtils.errorDialog('没有获得ID');
                    throw '没有获得ID!';
                }
                var o = angular.extend({}, options, {pageType: 'modify'});
                common(o, callback);
            },
            view: function (options, callback) {
                if (!options.id) {
                    CommonUtils.errorDialog('没有获得ID');
                    throw '没有获得ID!';
                }
                var o = angular.extend({}, options, {type: 'view'});
                common(o, callback);
            }
        }
    });
</#if>
})(angular);
