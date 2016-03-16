/**
 * Created by liuxq on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  usergroup.js
 */


(function (angular, window) {
    var app = angular.module('com.wanda.positionAllot.modal', ['eccrm.angular', 'eccrm.angularstrap',
        'eccrm.position.positionAllot', 'eccrm.position.position',
        'eccrm.ztree.modal', 'eccrm.directive.ztree']);
    app.factory('PositionAllotModal', function ($modal, CompileTree, PositionService, positionAllotConstant, PositionAllotService, CommonUtils, ZtreeModal, AlertFactory, ModalFactory, $filter) {
            return {
                // 弹出层多选
                // 参数options(必须）：配置项
                // 参数callback（必须）：选择后的回调（接收一个被选择的组数组）
                multi: function (options, callback) {
                    var defaults = {
                        title: '分配岗位',
                        min: 1
                    };
                    options = angular.extend(defaults, options);
                    options.initLeft = function () {
                        return CommonUtils.promise(function (defer) {
//                            var result = PositionAllotService.query();
//                            AlertFactory.handle(null, result, function (data) {
//                                defer.resolve(data.data || []);
//                            });
                        });
                    };
                    options.callback = function (items) {
                        if (angular.isFunction(callback)) {
                            callback(items);
                        }
                    };
                    ZtreeModal.doubleTree(options, callback);
                },
                add: function (cfg, callback) {
                    var defaults = {
                        scope: null,
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    }

                    cfg = angular.extend({}, defaults, cfg);
                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    var modal = $modal({
                        backdrop: 'static',
                        scope: scope,
                        template: CommonUtils.contextPathURL('app/position/template/positionAllot_Allot.tpl.html')
                    });
                    var foo = {
                        status: '1'
                    };
                    var that = this;
                    callback = callback || cfg.callback;
                    var scope = modal.$scope;
                    var type = scope.type;
                    scope.close = function (createNew) {
                        scope.$hide();
                    }
                    // 系统
                    positionAllotConstant.busiType(function (data) {
                        scope.busiTypes = data;
                    });
                    // 机构类型
                    positionAllotConstant.orgType(function (data) {
                        scope.orgTypes = data;
                    });
                    scope.beans = {};
                    scope.ClassifyztreeOptions = {
                        data: CompileTree.init,
                        click: function (node) {
                            scope.beans.classify = {id: node.id, name: node.name};
                            PositionService.queryAll({id: node.id}, function (dataParam) {
                                dataParam = dataParam.data || {total: 0};
                                scope.positionAllotd = dataParam.data
                            });
                        }
                    };

                    modal.$scope.positionAllots = [];
                    modal.$scope.selectOne = function () {
                        if (!modal.$scope.itemd || !modal.$scope.itemd.length)return;
                        for (var j = 0; j < modal.$scope.itemd.length; j++) {
                            var itemdId = modal.$scope.itemd[j].id
                            for (var i = 0; i < modal.$scope.positionAllotd.length; i++) {
                                var ids = modal.$scope.positionAllotd[i].id;
                                if (ids == itemdId) {
                                    modal.$scope.positionAllots.push(modal.$scope.positionAllotd[i])
                                    modal.$scope.positionAllotd.splice(i, 1);
                                }
                            }
                        }
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.deleteOne = function () {
                        if (!modal.$scope.items || !modal.$scope.items.length)return;
                        for (var j = 0; j < modal.$scope.items.length; j++) {
                            var itemsId = modal.$scope.items[j].id
                            for (var i = 0; i < modal.$scope.positionAllots.length; i++) {
                                var ids = modal.$scope.positionAllots[i].id;
                                if (ids == itemsId) {
                                    modal.$scope.positionAllotd.push(modal.$scope.positionAllots[i])
                                    modal.$scope.positionAllots.splice(i, 1);
                                }
                            }
                        }
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.selectAll = function () {
                        if (modal.$scope.positionAllots != null) {
                            for (var i = 0; i < modal.$scope.positionAllotd.length; i++) {
                                modal.$scope.positionAllots.push(modal.$scope.positionAllotd[i])
                            }
                        } else {
                            modal.$scope.positionAllots = tmp.data;
                        }
                        modal.$scope.positionAllotd = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.deleteAll = function () {
                        if (modal.$scope.positionAllotd != null) {
                            for (var i = 0; i < modal.$scope.positionAllots.length; i++) {
                                modal.$scope.positionAllotd.push(modal.$scope.positionAllots[i])
                            }
                        } else {
                            modal.$scope.positionAllotd = tmp.data;
                        }
                        modal.$scope.positionAllots = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    scope.save = function (createNew) {
                        scope.beans.positions = modal.$scope.positionAllots;
                        if (modal.$scope.positionAllots == null) {
                            return art.dialog({
                                content: '没有选择岗位！',
                                cancelVal: '关闭',
                                cancel: true
                            });

                        }
                        if (scope.beans.busiType == null) {
                            return art.dialog({
                                content: '没有系统类型！',
                                cancelVal: '关闭',
                                cancel: true
                            });
                        }
                        if (scope.beans.orgType == null) {
                            return art.dialog({
                                content: '没有选择机构类型！',
                                cancelVal: '关闭',
                                cancel: true
                            });
                        }
                        PositionAllotService.save(scope.beans, function (data) {
                            if (data && data.success) {
                                if (createNew) {
                                    AlertFactory.success('保存成功!', -1);
                                    scope.beans = angular.extend({}, foo);
                                    return;
                                }
                                if (angular.isFunction(callback || cfg.callback)) {
                                    callback.call(that, arguments);
                                }
                                scope.$hide();
                                window.location.reload();
                            } else {
                                AlertFactory.error('保存失败!', -1);
                            }
                        });

                    }

                }

            }
        }
    )
    ;
})(angular, window);