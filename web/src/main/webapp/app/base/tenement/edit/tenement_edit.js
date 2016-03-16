/**
 * Created by miles on 13-11-25.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.base.tenement.edit', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.tenement',
        'eccrm.base.document.modal',
        'eccrm.base.region'
    ]);
    app.service('PageVariable', function () {
        return {
            id: null,
            canEdit: false
        };
    });
    app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/document', {templateUrl: 'app/base/tenement/template/document-list-tab.html', controller: 'TDocCtrl'})
            .when('/contact', {templateUrl: 'app/base/tenement/contact_list.html', controller: 'TContactCtrl'})
            .when('/grant', {templateUrl: 'app/base/tenement/tenement_grant.html', controller: 'TGrantCtrl'})
            .when('/setting', {templateUrl: 'app/base/tenement/settings.html', controller: 'TSettingCtrl'})
            .otherwise({redirectTo: '/document'});
    }]);


    app.controller('TenementEditController', function ($scope, $window, TenementService, AlertFactory, RegionPicker, TenementConstant, PageVariable) {
        var pageType = $('#pageType').val();
        var id = $('#id').val();
        var defaults;
        var provinces = RegionPicker.province();
        $scope.scales = [
            {code: 1, name: '1-99'},
            {code: 2, name: '100-499'},
            {code: 3, name: '大于500'}
        ];

        $scope.industries = [
            {code: 1, name: '制造业'},
            {code: 2, name: '纺织业'},
            {code: 3, name: '媒体'},
            {code: 4, name: '科技'}
        ];
        $scope.rentTypes = [
            {code: 1, name: '固定用户'},
            {code: 2, name: '动态用户'}
        ];
        $scope.tenement = {};
        $scope.active = 0;
        $scope.routeOptions = [
            { url: 'document', name: '文档资料', active: true },
            { url: 'contact', name: '联系人' },
            { url: 'grant', name: '授权设置'},
            { url: 'settings', name: '功能设置' },
            { url: 'logs', name: '操作日志' }
        ];
        provinces.then(function (data) {
            $scope.provinces = data || [];
        });
        $scope.status = TenementConstant.status;
        $scope.changeProvince = function (provinceId) {
            $scope.cities = [];
            $scope.disticts = [];
            RegionPicker.city(provinceId).then(function (data) {
                $scope.cities = data || [];
            });
        };
        $scope.changeCity = function (cityId) {
            $scope.disticts = [];
            RegionPicker.district(cityId).then(function (data) {
                $scope.disticts = data || [];
            });
        };
        $scope.back = function () {
            $window.history.back();
        };
        PageVariable.id = id;
        if (pageType == 'modify') {//更新页面
            PageVariable.canEdit = true;
            TenementService.get({id: id}, function (data) {
                $scope.tenement = data;
            });
            //更新
            $scope.update = function () {
                TenementService.update($scope.tenement, function (data) {
                    if (data && data['success']) {
                        AlertFactory.success($scope, null, '更新成功!');
                    } else {
                        AlertFactory.updateError($scope, data);
                    }
                });
            }
        } else if (pageType == 'detail') {//查看页面
            TenementService.get({id: id}, function (data) {
                $scope.tenement = data;
                $('input,textarea,select').attr('disabled', 'disabled');
            });
        } else if (pageType == 'add') {
            var context = window.top.getCrmContext();
            var now = new Date().getTime();
            defaults = {
                companyScale: 1,
                userScale: 1,
                startDate: now,
                endDate: now + 30 * 24 * 60 * 60 * 1000,
                industry: 1,
                rentType: 1,
                createdUser: {
                    id: context.getUserId(),
                    name: context.getUsername()
                },
                status: 1,//采集中
                createdDatetime: now
            };
            $scope.tenement = angular.extend({}, defaults);
            //保存
            $scope.save = function () {
                TenementService.save($scope.tenement, function (data) {
                    if (data && data['success'] || data.id) {
                        AlertFactory.success($scope, null, '保存成功!');
                        $scope.tenement.id = data['id'];
                        PageVariable.id = data['id'];
                        PageVariable.canEdit = true;
                    } else {
                        AlertFactory.saveError($scope, data);
                    }
                });
            }
        } else {
            AlertFactory.error($scope, '错误的页面类型[' + pageType + ']');
        }
    });

    //文档资料
    app.controller('TDocCtrl', function ($scope, PageVariable, DocumentModal, AlertFactory, Document, ModalFactory) {
        var reload = function () {
            var result = Document.queryByTenement({tenementId: $scope.tid});
            AlertFactory.handle($scope, result, function (data) {
                data = data.data;
                $scope.documents = data;
            })
        };
        $scope.add = function () {
            DocumentModal.add({scope: $scope}, function () {
                reload();
            });
        };
        $scope.view = function (id) {
            DocumentModal.view({id: id, scope: $scope});
        };

        $scope.remove = function () {
            var items = [];
            angular.forEach($scope.items, function (v) {
                items.push(v.id);
            });
            if (items.length > 0) {
                ModalFactory.remove($scope, function () {
                    var result = Document.deleteByIds({ids: items.join(',')});
                    AlertFactory.handle($scope, result, reload);
                });
            }
        };
        $scope.canEidt = PageVariable.canEdit;
        $scope.tid = PageVariable.id;
        if ($scope.tid) {
            reload();
        }
    });


    //联系人
    app.controller('TContactCtrl', function ($scope, PageVariable) {
        $scope.canEidt = PageVariable.canEdit;
        $scope.tid = PageVariable.id;
    });


    //授权设置
    app.controller('TGrantCtrl', function ($scope, PageVariable) {
        $scope.canEidt = PageVariable.canEdit;
        $scope.tid = PageVariable.id;
    });


    //功能设置
    app.controller('TSettingCtrl', function ($scope, PageVariable) {
        $scope.canEidt = PageVariable.canEdit;
        $scope.tid = PageVariable.id;
    });


    //操作日志
    app.controller('TOperateCtrl', function ($scope, PageVariable) {
        $scope.canEidt = PageVariable.canEdit;
        $scope.tid = PageVariable.id;
    });
})(window, angular, jQuery);
