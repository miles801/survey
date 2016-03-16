(function (angular, $, window) {

    var app = angular.module('eccrm.main', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.user.password.modal',   // 修改密码
        'eccrm.im.news'        // 新闻
    ]);

    app.controller('MainController', function ($scope, $http, AsideFactory, PasswordModal, $timeout, CommonUtils, AlertFactory, AsideFactory) {
        $scope.menus = []; // 菜单

        $scope.subMenus = [];// 子菜单

        var $iframe = $('#iframe');
        var $tab = $('#tab');
        $scope.addTab = function (title, url, data) {
            if (!url) {
                return;
            }
            if (data && data.fullScreen == true) {
                hideColbar(function () {
                    $tab.hide();
                    $iframe.attr('src', CommonUtils.contextPathURL(url)).show();
                });
                return;
            }
            if ($iframe.is(":visible")) {
                $iframe.hide();
            }
            if ($tab.is(":hidden")) {
                $tab.show();
            }

            CommonUtils.addTab({
                title: title,
                url: url,
                isRoot: true,
                canClose: false,
                targetObj: window

            });
        };

        // 查询权限菜单
        // 首先查询根菜单
        // 当点击根菜单时，动态加载子菜单
        CommonUtils.promise(function (defer) {
            CommonUtils.loading(defer, '加载个人权限菜单...');
            $http.get(CommonUtils.contextPathURL('/auth/accreditMenu/queryAccreditMenuRoot'))
                .success(function (data) {
                    // 包含子节点的根元素
                    data = data.data || [];
                    $scope.menus = data;
                    $scope.showHome();  // 显示首页
                    defer.resolve(data);
                })
                .error(function (data) {
                    defer.reject(data);
                });
        });
        $scope.showHome = function () {
            hideColbar(function () {
                $tab.hide();
                $iframe.attr('src', CommonUtils.contextPathURL('/app/home/panel/panel.jsp')).show();
            });
        };

        // 切换显示子菜单
        $scope.showChildren = function (menu) {
            // 九宫格
            if (menu.type == 'SYS_DHBQ') {
                hideColbar(function () {
                    $tab.hide();
                    $iframe.attr('src', CommonUtils.contextPathURL('/app/home/panel/panel.jsp')).show();
                });
            } else {
                if (menu.hasQueryChild) {
                    $iframe.hide();
                    $scope.subMenus = menu.children || [];
                    if ($('#colbar:hidden').length > 0) {
                        $('#colbar').show().trigger('click');
                    }
                } else {
                    CommonUtils.promise(function (defer) {
                        CommonUtils.loading(defer, '加载个人权限菜单...');
                        $http.get(CommonUtils.contextPathURL('/auth/accreditMenu/queryAccreditMenus?parentId=' + menu.id))
                            .success(function (data) {
                                // 包含子节点的根元素
                                data = data.data || [];
                                menu.hasQueryChild = true;
                                menu.children = data;
                                $scope.showChildren(menu);
                                defer.resolve(data);
                            })
                            .error(function (data) {
                                defer.reject(data);
                            });
                    });
                }
            }
        };


        $scope.showMessages = function () {
            AsideFactory.info({title: '通知', content: '您有新的审批消息! 请及时进行处理!'});
        };

        $scope.messages = 0;
        var loadMessage = function () {
            $http.post(CommonUtils.contextPathURL('/spec/youth/pageQuery'), {states: ['BLUE_WAIT', 'GRAY_WAIT']})
                .success(function (data) {
                    data = data.data || {total: 0, data: []};
                    if (data.total > $scope.messages) {
                        $scope.showMessages();
                    }
                    $scope.messages = data.total;
                    CommonUtils.delay(loadMessage, 30000);
                });
        };


        $http.get(CommonUtils.contextPathURL('/auth/accreditFunc/hasPermission?code=YOUTH_APPROVE_MSG'))
            .success(function (data) {
                if (data.data) {
                    loadMessage();
                }
            });

        // 查询超时未处理的帮扶信息进行提醒
        $http.get(CommonUtils.contextPathURL('/auth/accreditFunc/hasPermission?code=OP_YOUTH_EXPIRED'))
            .success(function (data) {
                if (data.data) {
                    $http.post(CommonUtils.contextPathURL('/spec/youth/pageQuery?start=0&limit=1'), {expired: true})
                        .success(function (data) {
                            if (data.data.total > 0) {
                                AsideFactory.info({title: '警告', content: '发现有超时未处理的帮扶信息，请及时查看!'});
                            }
                        });
                }
            });

        // 隐藏收缩条
        var $colbar = $('#colbar');
        var hideColbar = function (callback) {
            if ($colbar.is(':visible')) {
                $colbar.trigger('click', ['true']);
                $colbar.hide(callback);
            } else {
                angular.isFunction(callback) && callback();
            }
        };


        // 便签
        $scope.showNote = function () {

            hideColbar();
            // 显示便签
            $('#iframe').show().attr('src', 'tools/note')
        };


        $scope.updatePwd = function () {
            PasswordModal.modifyPwd({
                scope: $scope, callback: function () {
                    AlertFactory.success(null, '密码修改成功!重新登录有生效!');
                }
            });
        };


        // 当菜单渲染完毕后要执行的操作
        $scope.$on('ngRepeatFinish', function () {

            // 初始化菜单翻动
            //leftbar禁止双击选中
            $(".leftbar").attr("onselectstart", "return false");

            //leftBar翻动
            var $leftBarItem = $(".leftbar .LB_container > a"),
                leftbarBtnT = $(".leftbar .btnT"),
                leftbarBtnB = $(".leftbar .btnB");
            leftbarBtnT.click(function () {
                if (parseInt($leftBarItem.css("top")) < 0) {
                    $leftBarItem.animate({"top": parseInt($leftBarItem.css("top")) + 51}, 200);
                }
            });
            leftbarBtnB.click(function () {
                var leftbarItemHeight = $leftBarItem.length * 51;
                var leftbarContainerHeight = $(".leftbar .LB_container").height();
                if (parseInt(leftbarItemHeight) + parseInt($leftBarItem.css("top")) - parseInt(leftbarContainerHeight) > 0) {
                    $leftBarItem.animate({"top": parseInt($leftBarItem.css("top")) - 51}, 200);
                }
            });

            //leftBar选中切换
            $leftBarItem.click(function () {
                var obj = $(this);
                obj.siblings("a").removeClass("current");
                obj.addClass("current")
            });

            // 渲染完毕后再触发一次窗口大小改变事件
            $(document).trigger('resize');
        });
    });


    // 单击时切换状态，允许传递要被显示的（与使用当前指令）同辈元素的jquery 选择器
    app.directive('navClickSlide', function () {
        var speed = 200;
        return {
            link: function (scope, element, attr) {

                element.bind('click', function (e) {

                    var selector = attr['navClickSlide'];// 实际存放导航菜单的

                    var next = element.nextAll(); //存放下级元素的根元素
                    var elms = element.parent().siblings().find(selector + ':visible');
                    var showNext = function () {
//                        next.not(":visible") ? next.show('fast') : null; // 点击当前元素不折叠
                        next.slideToggle(speed);// 点击当前元素折叠
                    };
                    // 当前节点的兄弟节点没有有下级
                    if (next.children().length === 0) {
                        // 收起当前节点所有同级节点下被显示的元素
                        elms.length > 0 && elms.slideUp(speed);
                    } else {
                        // 当前节点的兄弟节点有下级
                        // 收起已被展开的,并展开现在的
                        elms.length > 0 ? elms.slideUp(speed, showNext) : showNext();

                        // 禁用默认事件（防止处理时导致的停顿现象）
                        e.preventDefault();
                    }
                });
            }
        }
    });

    // 当ng-repeat渲染完成后要执行的函数
    app.directive('ngRepeatFinish', ['$timeout', function ($timeout) {
        var defaultName = "ngRepeatFinish";
        return {
            restrict: 'A',
            link: function ($scope, element, attr) {
                var eventName = attr['ngRepeatFinish'];
                eventName = eventName || defaultName;
                if ($scope.$last === true) {
                    $timeout(function () {
                        $scope.$emit(eventName);
                    });
                }
            }
        }
    }]);

})(angular, $, window);

// 页面关闭/刷新时进行提示
window.onbeforeunload = function () {
    if ($.browser.msie) {// ie关闭
        // 第一个条件：点击右上角的关闭按钮
        // 第二个条件：点击页签的关闭按钮
        // 第三个条件：按下了ALT键等导致页面跳转的都视为关闭
        if (event.screenY - 20 < 0 || event.screenX > 440 || event.altKey) {// alt按键
            event.returnValue = "确定退出系统?"
        }
    } else if ($.browser.chrome) {
        event.returnValue = "确定退出系统?"
    }
};