/**
 * Created by miles on 13-11-25.
 */
(function (angular, $) {
    var app = angular.module('eccrm.base.nav.home', [
        'eccrm.base.nav',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    app.controller('NavHomeCtrl', function ($scope, MenuNavService, MenuNavConstant, CommonUtils) {

        $scope.beans = CommonUtils.defer();
        //定义方法
        $scope.query = function () {
            var result = MenuNavService.queryValid();
            CommonUtils.loading(result, '正在加载...', function (data) {
                $scope.beans.resolve(data.data || []);
            });
        };

        // 执行
        $scope.query();
    });
    app.directive('eccrmBox', function (CommonUtils) {
        var index = 0;
        var height; // 每一个box的高度
        var createLi = function (child, index) {
            var li = $('<li></li>');
            var a = $('<a href="' + CommonUtils.contextPathURL(child.resourceUrl) + '" style="outline:none;">' + child.showName + '</a>');
            a.click(function (e) {
                e.preventDefault();
                var target = e.target;
                if (child.resourceUrl) {
                    var root = $(e.target).parents('.panel-tab:eq(0)');
                    var iframe = createIframe(root);
                    iframe.attr('src', target.href);
                }
            });
            li.append(a);
            li.click(function () {
                li.siblings().removeClass('active');
                li.addClass('active');
            });
            return li;
        };
        var createIframe = function (root) {
            if (root.find('.tab-content iframe').length == 0) {
                var iframeName = "tab_iframe" + (index++);
                var iframe = $('<iframe id="' + iframeName + '" frameborder="0" style="border: 1px solid #ddd;border-top:0;height: 100%;width: 100%;margin: 0;padding: 0;" ></iframe >');
                root.children('.tab-content').children().remove();
                root.children('.tab-content').append(iframe);
                // 删除原有的空白元素
                return iframe;
            } else {
                return root.children('.tab-content').children('iframe');
            }
        };
        /**
         * 根据数据创建HTML IFRAME
         * @param data
         * @returns {*|HTMLElement} iframe对象
         */
        var createBox = function (data) {
            if (!data) {
                return;
            }
            var box = $('<div style="float: left;margin:0;padding:3px;"></div>');
            box.hide();
            // 设置box的宽度和高度
            box.addClass('col-' + (4 * data.cols));
            box.height(height * data.rows);
            var panelTab = $('<div class="panel panel-tab" style="height: 100%;margin: 0;" ></div>');
            box.append(panelTab);
            var nav = $('<ul class="nav nav-tabs" ></ul>');
            panelTab.append(nav);
            var tabContent = $('<div class="tab-content" style="height: 100%;margin-top:-35px;padding-top: 29px;" >' +
                '<div style="height: 100%;border: 1px solid #ddd;border-top:0;"></div></div>');
            panelTab.append(tabContent);
            var children = data.children;
            if (children) {
                angular.forEach(children, function (child, index) {
                    var li = createLi(child, index);
                    nav.append(li);
                    if (index == 0) {
                        li.trigger('click');
                        li.children('a').trigger('click');
                    }
                });
            } else {
                var li = createLi(data, 0);
                nav.append(li);
                li.trigger('click');
                li.children('a').trigger('click');
            }
            if (data.showMore) {
                // 添加后缀isMore
                var url = data['resourceUrl'] || '';
                if (url.indexOf('?') > 0) {
                    url += '&';
                } else {
                    url += '?';
                }
                url += 'isMore=true';
                nav.append($('<a href="' + CommonUtils.contextPathURL(url) + '" style="margin:5px 10px 0 0;float:right;font-size:14px;font-weight:600;">更多>></a>'));
            }
            return box;
        };
        return {
            scope: {
                boxes: '=eccrmBox'
            },
            link: function (scope, element, attr, ctrl) {
                height = element.height() / 3;
                var promise = CommonUtils.parseToPromise(scope.boxes);
                promise.then(function (boxes) {
                    var root = [];      // 用于存放根页签
                    var map = {};       // 存放映射关系，key为id，值为对象
                    var children = [];  // 子页签

                    // 获得跟页签和子页签
                    angular.forEach(boxes || [], function (o) {
                        !o.parentId ? (function () {
                            root.push(o);
                            map[o.id] = o;
                        })() : children.push(o);
                    });

                    // 将子页签添加到根页签中
                    angular.forEach(children, function (o) {
                        var pid = o.parentId;
                        var parent = map[pid];
                        if (parent) {
                            parent.children = parent.children || [];
                            parent.children.push(o);
                        }
                    });

                    /**
                     * 清除iframe所占用的内存
                     */
                    var remove = function () {
                        var $iframe = $('iframe');
                        $iframe.attr('src', 'about:blank');
                        for (var i = 0; i < $iframe.length; i++) {
                            var foo = $iframe[i].contentWindow.document;
                            foo.write('');
                            foo.close();
                        }
                        $iframe.remove();
                    };

                    // 生产iframe
                    angular.forEach(root, function (o) {
                        var box = createBox(o);
                        box.show(500);
                        element.append(box);
                    });

                    // 清除数据
                    root = null;
                    children = null;
                    map = null;

                    window.onunload = function () {
                        remove();
                    };
                });
            }
        };
    });
})
(angular, jQuery);