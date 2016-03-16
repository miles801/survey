(function (angular, window) {
    var app = angular.module('eccrm.base.position.modal', [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.directive.ztree',// tree
        'base.org',
        'eccrm.position.position'
    ]);
    app.factory('PositionModal', function ($modal, OrgTree, positionConstant, CommonUtils, PositionService, ClassifyService, CompileTree, AlertFactory, ModalFactory, $filter) {
        return {
            //分类==》新增
            addAllot: function (options, callback) {
                var defaults = {
                    scope: null,
                    pageType: 'add',
                    callback: null,//点击确定后要执行的函数
                    afterShown: null//模态对话框显示完成后要执行的函数
                };

                options = angular.extend({}, defaults, options);
                var scope = options.scope;
                if (!scope) throw '使用模态对话框时必须指定scope!';
                var modal = $modal({
                    backdrop: 'static',
                    scope: scope,
                    template: CommonUtils.contextPathURL('app/position/template/positionAllot_add.tpl.html')
                });
                var foo = {
                    status: '1'
                };
                var that = this;
                callback = callback || options.callback;
                var $scope = modal.$scope;

                $scope.close = function () {
                    $scope.$hide();
                };
                $scope.beans = {parent: null};
                $scope.ztreeOptions = {
                    data: CompileTree.init,
                    click: function (node) {
                        $scope.beans.parent = {id: node.id, name: node.name};
                    }
                };

                $scope.save = function (createNew) {
                    var promise = ClassifyService.save($scope.beans, function (data) {
                        if (data && data.success) {
                            if (createNew) {
                                AlertFactory.success(null, '保存成功!');
                                $scope.beans = angular.extend({}, foo);
                                return;
                            }
                            if (angular.isFunction(callback || options.callback)) {
                                callback();
                            }
                            $scope.$hide();
                        } else {
                            AlertFactory.error('保存失败!', -1);
                        }
                    });
                    CommonUtils.loading(promise);
                };

                $scope.update = function () {
                    var promise = ClassifyService.update($scope.beans, function (data) {
                        if (data && data.success) {
                            AlertFactory.success(null, '更新成功!');
                            if (angular.isFunction(callback || options.callback)) {
                                callback();
                            }
                            $scope.$hide();
                        } else {
                            AlertFactory.error(null, '更新失败!' + data.data);
                        }
                    });
                    CommonUtils.loading(promise);
                };

                var load = function (id) {
                    var promise = ClassifyService.get({id: id}, function (data) {
                        $scope.beans = data.data;
                    });
                    CommonUtils.loading(promise);
                };

                $scope.pageType = options.pageType || 'add';
                if ($scope.pageType == 'modify') {
                    if (!options.id) {
                        AlertFactory.error(null, '页面初始化错误，未获得ID!');
                        CommonUtils.delay($scope.close, 3000);
                        return;
                    }

                    load(options.id)
                }


            },
            //权限==》新增
            addRole: function (cfg, callback) {
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
                    template: CommonUtils.contextPathURL('app/position/template/positionRole_add.tpl.html')
                });
                var foo = {
                    status: '1'
                };
                var that = this;
                callback = callback || cfg.callback;
                var scope = modal.$scope;

                scope.close = function (createNew) {
                    scope.$hide();
                }

                scope.save = function (createNew) {
                    PositionService.save(scope.beans, function (data) {
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

                callback = callback || cfg.callback;

            },
            //知识库==》调查问卷==》新增
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
                    template: CommonUtils.contextPathURL('app/position/template/position_add.tpl.html')
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
                positionConstant.busiType(function (data) {
                    scope.busiTypes = data;
                });
                // 机构类型
                positionConstant.roleType(function (data) {
                    scope.roleTypes = data;
                });
                // 机构类型
                positionConstant.status(function (data) {
                    scope.statuss = data;
                });
                scope.beans = {};
                scope.ClassifyztreeOptions = {
                    multi: true,
                    data: CompileTree.init,
                    click: function (node) {
                        scope.beans.classify = {id: node.id, name: node.name};
                    }
                }
                scope.OrgztreeOptions = {
                    multi: true,
                    data: OrgTree.init,
                    click: function (node) {
                        scope.beans.organization = {id: node.id, name: node.name};
                    }
                }

                /*   var originalName;
                 scope.validateRuleName = {
                 validateType: 'multiName',
                 validateMsg: '岗位名称重复',
                 validateFn: function (value) {
                 if (!value || value === originalName) return true;
                 return CommonUtils.promise(function (defer) {
                 PositionService.exists({
                 name: encodeURI(encodeURI(scope.beans.name))
                 }
                 , function (data) {
                 defer.resolve(!data.data);
                 });
                 });
                 }
                 };*/
                scope.validateEmpCount = {
                    validateType: 'ContInt',
                    validateMsg: '不是正整数!',
                    validateFn: function (value) {
                        if (!value) return true;
                        var type = /^[0-9]*[1-9][0-9]*$/;
                        var re = new RegExp(type);
                        if (value.match(re) == null) {
                            return false;
                        }
                        return true;
                    }
                };
                scope.validateMinEmpCount = {
                    validateType: 'ContInt',
                    validateMsg: '不是正整数!或人员编制小于最小人员!',
                    validateFn: function (value, currentOptions) {
                        if (!value) return true;
                        var type = /^[0-9]*[1-9][0-9]*$/;
                        var re = new RegExp(type);
                        if (value.match(re) == null) {
                            return false;
                        }
                        if (value > scope.beans.empCount) {
                            return false;
                        }
                        return true;
                    }
                };
                if (type == 'add') {
                    scope.type = 'add';
                    var default_dataClass = {
                        isAlert: true,
                        roleType: "SYS_YYGW",
                        status: "ACTIVE"
                    }
                    scope.beans = angular.extend({}, default_dataClass);
                    scope.save = function (createNew) {
                        PositionService.exists({
                                name: encodeURI(encodeURI(scope.beans.name))
                            }
                            , function (data) {
                                if (!data.data) {
                                    PositionService.save(scope.beans, function (data) {
                                        if (data && data.success) {
                                            if (createNew) {
//                                        AlertFactory.success('保存成功!',-1);
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
                                } else {
                                    AlertFactory.error(scope, '岗位名称重复!');
                                }

                            });
                    }
                } else if (type == 'modify') {
                    scope.type = 'modify';
                    PositionService.get({id: scope.id}, function (data) {
                        scope.beans = data.data || [];
//                            originalName=scope.beans.name
                        scope.beans.classify = {id: scope.beans.classifyId, name: scope.beans.classifyName};
                        scope.beans.organization = {id: scope.beans.organizationId, name: scope.beans.organizationName};
                    });
                    scope.update = function () {
                        PositionService.update(scope.beans, function (data) {
                            if (data && data.success) {
//                                    AlertFactory.success('保存成功!',-1);
                                scope.$hide();
                            } else {
                                AlertFactory.error('保存失败!', -1);
                            }
                            angular.isFunction(callback) ? callback.call(scope) : null;
                        });
                    };
                    scope.type = 'modify';
                    ModalFactory.afterShown(modal, cfg.afterShown);
                } else if (type == 'detail') {
                    scope.type = 'detail';
                    PositionService.get({id: scope.id}, function (data) {
                        scope.beans = data.data || [];
//                            originalName=scope.beans.name
                        scope.beans.classify = {id: scope.beans.classifyId, name: scope.beans.classifyName};
                        scope.beans.organization = {id: scope.beans.organizationId, name: scope.beans.organizationName};
                    });
                    ModalFactory.afterShown(modal, cfg.afterShown);
                }
            }
        }
    });
})(angular, window);