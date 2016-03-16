/**
 * Created by miles on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  usergroup.js
 */


(function (angular, window) {
    var app = angular.module('eccrm.base.user.modal', ['mgcrea.ngStrap', 'eccrm.base.user', 'eccrm.angularstrap']);
    app.factory('UserModal', function ($modal, User, AlertFactory,CommonUtils) {
            return {
                single: function (cfg, callback) {
                    var defaults = {
                        scope: null,//必选项
                        value: null,//可选项，用于回显
                        exclude: [],//可选项，要排除的数据，用于列出非自身的数据
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    };
                    cfg = angular.extend({}, defaults, cfg);

                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    callback = callback || cfg.callback;

                    var _t = this;
                    var modal = $modal({scope: scope, backdrop: 'static', template: CommonUtils.contextPathURL('/app/base/user/template/user-modal-single.tpl.html')});

                    User.query(function (data) {
                        var tmp = [];
                        if (angular.isArray(cfg.exclude) && cfg.exclude.length > 0) {
                            for (var i = 0; i < data.data.length; i++) {
                                (function (t) {
                                    var foo = data.data[t];
                                    if ($.inArray(foo.id, cfg.exclude) == -1) tmp.push(data.data[t]);
                                })(i);
                            }
                        } else {
                            tmp = data.data;
                        }
                        modal.$scope.users = tmp.data;
                    });
                    modal.$scope.selected = cfg.value;
                    modal.$scope.confirm = function () {
                        var foo = angular.extend({}, modal.$scope.selected);
                        if (callback && angular.isFunction(callback)) {
                            callback.call(_t, foo);
                        }
                        this.$hide();
                    }

                    //模态对话框显示后要执行的操作
                    modal.$promise.then(function () {
                        var foo = setInterval(function () {
                            if (modal.$scope.$isShown) {
                                if (cfg.afterShown && angular.isFunction(cfg.afterShown)) {
                                    cfg.afterShown.call(_t, arguments);
                                }
                                clearInterval(foo);
                            }
                        }, 50)
                    });
                }
            }
        }
    );
})(angular, window);