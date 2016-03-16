/**
 * 依赖于:
 *  menu.js
 *  ztree.js
 *  ztree-directive.js
 *
 * Created by miles on 2014/8/3.
 */
(function () {
    var app = angular.module('eccrm.base.menu.modal', [
        'eccrm.base.menu',
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.ztree.modal'
    ]);
    // 角色编辑模态对话框
    app.factory('MenuModal', function (MenuService, AlertFactory, CommonUtils, ZtreeModal) {
            return {
                // 打开菜单模态对话框，选择菜单
                // 参数1：{options}
                //      title:模态对话框的名称
                //      min：至少选择的个数
                //      max:最多可以选择的个数
                //      defaultChecked:函数，返回id数组，默认选中的节点（以id为标准）
                // 参数2：function(items)
                //      items:选择的元素,孩子节点将会以平级的方式返回
                dbTreePicker: function (options, callback) {
                    var defaults = {
                        title: '选择资源',
                        min: 1
                    };
                    options = angular.extend(defaults, options);
                    options.initLeft = function () {
                        return CommonUtils.promise(function (defer) {
                            var result = MenuService.queryValid();
                            AlertFactory.handle(null, result, function (data) {
                                defer.resolve(data.data || []);
                            });
                        });
                    };
                    options.callback = function (items) {
                        if (angular.isFunction(callback)) {
                            callback(items);
                        }
                    };
                    ZtreeModal.doubleTree(options, callback);
                }
            }
        }
    );
})();