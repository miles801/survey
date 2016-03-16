
// angular-ztree-directive.js
(function (angular, $) {
    var app = angular.module("eccrm.directive.ztree.test", [
        'eccrm.angular'
    ]);
    var defaults = {
        data: undefined,// （必须）树的数据：支持[]，函数，未来对象（必须返回[]）
        treeId: null,// 树的id
        multi: false,// （已废弃）是否多棵树：如果一个页面中会出现多棵树，则设置该属性为true，表示每次都加载
        zindex: 99,// 树的css层级：z-index的值
        speed: 500,// 展开或折叠的速度：单位为毫秒,
        click: angular.noop,// 点击事件
        disableClick: null,// 是否禁用某次点击操作，返回true表示禁用
        reload: false, // （已废弃）是否重新刷新树：当该值为true时，会重新刷新树
        async: angular.noop,// 异步函数,异步函数参数（选中的节点，数据返回后的回调函数）
        maxHeight: 300, // 树的最大高度：值为数字
        treeSetting: null,// 树的展示方式（一般使用默认值即可）
        position: 'fixed',// 定位方式，默认使用悬浮定位
        backgroundColor: '#F0F6E4'// 树的背景色
    };
    //单选树
    //<input ztree-single="ztreeOptions"/>
    app.directive('ztreeSingle', function (CommonUtils, $q) {
        // ztree 的默认初始化参数
        var treeCount = 0;
        return {
            scope: {
                options: '=ztreeSingle'
            },
            link: function (scope, element, attrs, ctrl) {

            }
        }
    });

    //多选树
    app.directive('ztreeMulti', function () {
        return {
            scope: {
                options: '=ztreeMulti'
            },
            link: function (scope, element, attrs, ctrl) {

            }
        }
    });
})
(angular, jQuery);

// 合并ztree的所有功能
(function () {
    angular.module('eccrm.angular.ztree.test', [
        'eccrm.directive.ztree.test'
    ]);
})();