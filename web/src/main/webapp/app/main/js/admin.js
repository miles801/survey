(function (angular, $, window) {
    var app = angular.module('eccrm.main', [
        'eccrm.base.menu',
        'eccrm.angular',
        'eccrm.directive.ztree'
    ]);
    app.controller('MainController', function ($scope, $http, MenuService, $timeout, CommonUtils) {

        $scope.menus = []; // 菜单

        $scope.subMenus = [];// 子菜单
        // 查询菜单
        // 查询权限菜单
        MenuService.queryValidMenu(function (data) {
            // 包含子节点的根元素
            data = data.data || [];
            $scope.menus = data;
            if (data[0]) {
                $scope.subMenus = data[0].children || [];
            }
            $timeout(function () {
                $('#main .leftbar .LB_container a:eq(0)', window.document.body).addClass('current');
            }, 50);
        });

        // 切换显示子菜单
        $scope.showChildren = function (menu) {
            $scope.subMenus = menu.children || [];
        };

        var logout = function () {
            window.location.href = CommonUtils.contextPathURL('/logout');
        };
        // 登出系统
        $scope.logout = function () {
        };
    });
    // 单击时切换状态，允许传递要被显示的（与使用当前指令）同辈元素的jquery 选择器
    app.directive('navClickSlide', function () {
        return {
            link: function (scope, element, attr) {

                element.bind('click', function () {
                    var selector = attr['navClickSlide'];
                    element.parent().siblings().find(selector + ':visible').slideUp();
                    element.nextAll(selector).slideUp();
                    var next = element.nextAll();
                    next.not(":visible") ? next.show() : null;
//                    next.not(":visible") ? next.css({opacity: 0}).show().animate({opacity: 1}, 400) : null;
                });
            }
        }
    });
})(angular, $, window);