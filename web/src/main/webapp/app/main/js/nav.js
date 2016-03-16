/**
 * Created by Michael on 2014/10/23.
 */
(function () {
    // 设置最左侧导航菜单的大小
    function setLayOutSize() {
        var $leftBar = $(".leftbar");
        var leftBarWidth = $leftBar.width(),
            leftBarHeight = $leftBar.height();
        var leftBarItemLength = $leftBar.find('.LB_container > a').length;
        var $leftBarContainer = $leftBar.find('.LB_container');
        if (leftBarItemLength * 51 < leftBarHeight) {
            $leftBar.find('.btnT,.btnB').hide();
            $(".leftbar .btnB").hide();

            $leftBarContainer.height(leftBarHeight - 2);
            $leftBarContainer.css("padding-top", 0);
        } else {
            $(".leftbar .btnT").show();
            $(".leftbar .btnB").show();
            $leftBarContainer.height(leftBarHeight - 28);
            $leftBarContainer.css("padding-top", 14);
        }
        $leftBarContainer.width(leftBarWidth - 2);
        $(".leftbar .LB_container > a").css("top", 0);
    }

    // 窗口大小发生变化时，动态改变#main的高度
    function resize() {
        // 如果是jquery1.8,这里的值应该为139
        // 如果是jquery1.7,这里的值应该为0
        var height = 88;    // 如果使用万达的SSO，这里的值应该是118
        $('#main').height($(window).outerHeight() - height);
        $(window).on('resize', function () {
            $('#main').height($(window).outerHeight() - height);
        });
    }

    // 展开/收缩左侧菜单
    function clickToggleMenu() {
        var $expand = $('#expand');
        var $fold = $('#fold');
        var $iframe = $('iframe');
        var speed = 'normal';
        var menuWidth = 150;
        $('#colbar').click(function (e, isIframe) {
            e.preventDefault();
            var colbarWidth = 10;
            if (isIframe) {
                colbarWidth = 0;
            }
            if ($fold.is(':hidden')) {//点击展开
                $('.content-iframe,.content-iframe').animate({
                    'marginLeft': -menuWidth - colbarWidth,
                    'paddingLeft': menuWidth + colbarWidth
                }, speed, function () {
                    $('#accordian').animate({width: menuWidth}, speed);
                });
                $fold.show();
                $expand.hide();
            } else {
                $('#accordian').animate({width: 0}, speed, function () {
                    $('.content-iframe,.content-iframe').animate({
                        'marginLeft': -colbarWidth,
                        'paddingLeft': colbarWidth
                    }, speed);
                });
                $fold.hide();
                $expand.show();
            }

        });
    }

    // 页面加载完毕后执行
    function afterLoad() {
        // 初始化左侧菜单
        setLayOutSize();

        $(window).resize(function () {
            resize();
            setLayOutSize();
        });

        clickToggleMenu();

    }

    // 真正的页面加载完毕后执行的函数
    $(afterLoad);
})();