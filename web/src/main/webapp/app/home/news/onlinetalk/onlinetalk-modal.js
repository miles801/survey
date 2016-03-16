/**
 * Created by liuxq on 14-3-14.
 * dependencies:
 *  angularstrap-v2.0
 *  usergroup.js
 */


(function (window, angular, $) {
    //, 'eccrm.base.user','eccrm.base.usergroup'
    var app = angular.module('eccrm.base.onlinetalk.modal', ['eccrm.angular', 'eccrm.angularstrap',
        'eccrm.home.onlinetalk', 'eccrm.base.user', 'eccrm.base.usergroup', 'eccrm.ztree.modal']);
    //UserService,UserGroupService,
    app.factory('OnlineTalkModal', function ($modal, Debounce, $http, ZtreeModal, CommonUtils, User, Group, UserGroup, UserConstant, OnlinetalkService, OnlineLogService, AlertFactory, ModalFactory, $filter) {
            var modal_config1;
            var modal_config2;
            var modal_config3;
            var modal_config4;
            return {
                //阅读员工列表
                userList: function (cfg, callback) {
                    var defaults = {
                        scope: null,//必选项
                        value: null,//可选项，用于回显
                        exclude: [],//可选项，要排除的数据，用于列出非自身的数据
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    }
                    cfg = angular.extend({}, defaults, cfg);

                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    var id = cfg.id;
                    callback = callback || cfg.callback;
                    var _t = this;
                    var modal = $modal({
                        scope: cfg.scope,
                        template: CommonUtils.contextPathURL('/app/home/news/onlinetalk/readStatus-confirm.tpl.html')
                    });

                    OnlineLogService.getLog({id: id}, function (data) {

                        modal.$scope.userReceiveLists = data;
                    });
                },
                //发送
                sendModal: function (cfg, callback) {
                    var defaults = {
                        titleText: null,
                        scope: null,
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    }
                    cfg = angular.extend({}, defaults, cfg);
                    var scope = cfg.scope;
                    var _t = this;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    var modal = $modal({
                        backdrop: 'static',
                        template: CommonUtils.contextPathURL('/app/home/news/onlinetalk/send.tpl.html')
                    });
                    var context = CommonUtils.loginContext() || {};
                    var ueditor_config = {
                        initialFrameHeight: 150,
                        autoHeightEnabled: false,
                        toolbars: [
                            ["bold", "italic", "underline", "fontsize", "|", "insertorderedlist", "insertunorderedlist", "|", 'removeformat', 'forecolor', 'backcolor']
                        ],
                        //关闭字数统计
                        wordCount: false,
                        //关闭elementPath
                        elementPathEnabled: false
                    };


                    ModalFactory.afterShown(modal, function () {
                        if (modal_config1 == undefined && modal_config1 == null) {
                            var a = UE.getEditor('container1', ueditor_config);
                            modal_config1 = modal;
                        } else {
                            var a = UE.getEditor('container1', ueditor_config);
                            a.render('container1');
                        }
                        $('#container .edui-editor.edui-default').css('width', '100%');
                    });
                    var $scope = modal.$scope;
                    //onlineTalk默认值设置
                    var foo = {
                        type: 1,
                        needReply: false,
                        flag: cfg.scope.flag,
                        user: cfg.user,
                        username: cfg.scope.username
                    };
                    $scope.onlineTalk = angular.extend({}, foo);
                    $scope.destroy = modal.destroy;
//                    $scope.uploadSendOptions = {
//                        enableUrl: false,
//                        maxFiles: 1
//                    };

                    var that = this;
                    modal.$scope.onlineTalk.operatingOnlineContactReceivers = [];
                    callback = callback || cfg.callback;
                    $scope.save = function (createNew) {
                        if (UE.getEditor('container1').getContent() != null && UE.getEditor('container1').getContent().trim() != "") {
                            $scope.onlineTalk.content = UE.getEditor('container1').getContent();
                        } else {
                            return AlertFactory.success(modal.$scope, " ", '发送的内容不能为空！');
                        }
//                        var attachmentIds = $scope.uploadSendOptions.getIds();
//                        if (attachmentIds.length > 0) {
//                            $scope.onlineTalk.attachmentIds = attachmentIds.join(",");
//                        }
                        if (modal.$scope.onlineTalk.operatingOnlineContactReceivers == null || modal.$scope.onlineTalk.operatingOnlineContactReceivers.length < 1) {
                            return AlertFactory.success(modal.$scope, " ", '发送的人和组都为空,无法发送，至少一个不为空！');
                        }
                        OnlinetalkService.save(modal.$scope.onlineTalk, function (data) {
                            if (data && data.success) {
                                if (createNew) {
                                    AlertFactory.success(modal.$scope, null, '保存成功!');
                                    modal.$scope.onlineTalk = angular.extend({}, foo);
                                    return;
                                }
                                if (angular.isFunction(callback || cfg.callback)) {
                                    callback.call(that, arguments);
                                }
                                modal.$scope.$hide();
                            } else {
                                AlertFactory.error(modal.$scope, '[' + (data && data.error || data.fail || '') + ']', '保存失败!');
                            }
                        });
                        //返回上一页刷新
                        window.location.reload();
                    }
                    //岗位
                    modal.$scope.pos = function () {
                        that.pos({scope: modal.$scope}, function (data) {
                        });
                    }
                    //机构岗位
                    modal.$scope.orgPos = function () {
                        that.orgPos({scope: modal.$scope}, function (data) {
                        });
                    }
                    //系统类型
                    modal.$scope.busi = function () {
                        that.busi({scope: modal.$scope}, function (data) {
                        });
                    }
                    modal.$scope.emplo = function () {
                        that.users({scope: modal.$scope}, function (data) {
                            if (!data || !data.length) return;
                            if (modal.$scope.onlineTalk.operatingOnlineContactReceivers != null &&
                                modal.$scope.onlineTalk.operatingOnlineContactReceivers != undefined) {
                                modal.$scope.onlineTalk.operatingOnlineContactReceivers.user = null;
                            } else {
                                modal.$scope.onlineTalk.operatingOnlineContactReceivers = [];
                            }
                            for (var i = 0; i < data.length; i++) {
                                modal.$scope.onlineTalk.operatingOnlineContactReceivers.push({
                                    user: {
                                        id: data[i].id
                                    }
                                });
                            }
                            var names = [];
                            angular.forEach(data, function (v) {
                                names.push(v.username);
                            });
                            modal.$scope.onlineTalk.userName = names.join(' , ');
                        });
                    }
                    //选择接收组
                    modal.$scope.Group = function () {
                        /* that.dbTreePicker({scope: modal.$scope}, function (data) {
                         if (!data && !data.length) return;
                         var ids = [];
                         var addChild = function (obj) {
                         obj.id && ids.push(obj.id);
                         if (obj.children && obj.children.length > 0) {
                         (function (bar) {
                         var i = 0;
                         for (i; i < bar.length; i++) {
                         addChild(bar[i]);
                         }
                         })(obj.children);
                         }
                         }
                         for (var i = 0; i < data.length; i++) {
                         addChild(data[i]);
                         }
                         modal.$scope.onlineTalk.operatingOnlineContactReceivers = [];
                         if (ids != null && ids.length > 0) {
                         for (var j = 0; j < ids.length; j++) {

                         modal.$scope.onlineTalk.operatingOnlineContactReceivers.push({

                         userGroup: {
                         id: ids[j]
                         }
                         });
                         }
                         }
                         var name = data.map(function (v) {
                         return v.name
                         })
                         modal.$scope.onlineTalk.userGroupName = name.join(',');
                         });*/
                        that.dbTreePicker({
                            title: '接收组'
                        }, function (items) {
                            var ids = [];
                            angular.forEach(items, function (v) {
                                ids.push(v.id);
                            });
                            var addChild = function (obj) {
                                obj.id && ids.push(obj.id);
                                if (obj.children && obj.children.length > 0) {
                                    (function (bar) {
                                        var i = 0;
                                        for (i; i < bar.length; i++) {
                                            addChild(bar[i]);
                                        }
                                    })(obj.children);
                                }
                            }
                            for (var i = 0; i < ids.length; i++) {
                                addChild(ids[i]);
                            }
                            if (modal.$scope.onlineTalk.operatingOnlineContactReceivers != null &&
                                modal.$scope.onlineTalk.operatingOnlineContactReceivers != undefined) {
                                modal.$scope.onlineTalk.operatingOnlineContactReceivers.group = null;
                            } else {
                                modal.$scope.onlineTalk.operatingOnlineContactReceivers = [];
                            }
                            if (ids != null && ids.length > 0) {
                                for (var j = 0; j < ids.length; j++) {

                                    modal.$scope.onlineTalk.operatingOnlineContactReceivers.push({

                                        group: {
                                            id: ids[j]
                                        }
                                    });
                                }
                            }
                            var items = [];
                            angular.forEach(ids, function (v) {
                                items.push(v.name);
                            });
                            modal.$scope.onlineTalk.userGroupName = items.join(',');
                        });
                    };
                    $scope.title = cfg.title || '发送信息';
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
                },
                //回复
                replyModal: function (cfg, callback) {
                    var context = CommonUtils.loginContext() || {};
                    var defaults = {
                        createdDatetime: new Date().getTime(),
                        creatorName: context.username

                    }
                    cfg = angular.extend({}, defaults, cfg);
                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    var id = cfg.id;
                    var modal = $modal({
                        backdrop: 'static',
                        scope: scope,
                        template: CommonUtils.contextPathURL('/app/home/news/onlinetalk/reply.tpl.html')
                    });
                    var ueditor_config = {
                        initialFrameHeight: 150,
                        autoHeightEnabled: false,
                        toolbars: [
                            ["bold", "italic", "underline", "fontsize", "|", "insertorderedlist", "insertunorderedlist", "|", 'removeformat', 'forecolor', 'backcolor']
                        ],
                        //关闭字数统计
                        wordCount: false,
                        //关闭elementPath
                        elementPathEnabled: false
                    };
                    var defer = CommonUtils.defer();
                    scope.fileUploadOptions = defer;
                    ModalFactory.afterShown(modal, function () {
                        scope.fileUploadOptions.resolve({
                            id: 'fileupload',
                            formData: [
                                {name: 'businessType', value: 'step3'}
                            ],
                            success: function (data) {
                                scope.attachments = scope.attachments || [];
                                scope.attachments.push(data[0]);
                            }
                        });
                        if (modal_config4 == undefined) {
                            var a = UE.getEditor('container4', ueditor_config);
                            modal_config4 = modal;
                        } else {
                            var a = UE.getEditor('container4', ueditor_config);
                            a.render('container4');
                        }
                        $('#container .edui-editor.edui-default').css('width', '100%');
                    });
                    function loadAttachment() {
                        var url = CommonUtils.contextPathURL('/attachment/query?btype=step3&bid=' + modal.$scope.beans.id + "&_tmp=" + new Date().getTime);
                        $http.get(url)
                            .success(function (data) {
                                scope.attachments = data.data || [];
                            });
                    }

                    OnlinetalkService.get({id: id}, function (data) {
                        modal.$scope.beans = data;
                        loadAttachment()
                        modal.$scope.onlineTalk = {
                            user: {
                                id: data.creatorId,
                                username: data.creatorName
                            },
                            operatingOnlineContact: modal.$scope.beans
                        }

                        modal.$scope.onlineTalk = angular.extend({}, defaults, modal.$scope.onlineTalk);
                    });
                    var that = this;
                    callback = callback || cfg.callback;
                    modal.$scope.saveReply = function (createNew) {
                        if (UE.getEditor('container4').getContent() != null && UE.getEditor('container4').getContent().trim() != "") {
                            modal.$scope.onlineTalk.replyContent = UE.getEditor('container4').getContent();
                        } else {
                            return AlertFactory.success(modal.$scope, " ", '发送的内容不能为空！');
                        }
//                        var attachmentIds = modal.$scope.uploadReplyOptions.getIds();
//                        if (attachmentIds.length > 0) {
//                            modal.$scope.onlineTalk.attachmentIds = attachmentIds.join(",");
//                        }
                        OnlineLogService.saveReply(modal.$scope.onlineTalk, function (data) {
                            if (data && data.success) {
                                if (createNew) {
                                    AlertFactory.success(modal.$scope, null, '保存成功!');
                                    modal.$scope.onlineTalk = angular.extend({}, foo);
                                    return;
                                }
                                if (angular.isFunction(callback || cfg.callback)) {
                                    callback.call(that, arguments);
                                }
                                modal.$scope.$hide();
                            } else {
                                AlertFactory.error(modal.$scope, '[' + (data && data.error || data.fail || '') + ']', '保存失败!');
                            }
                        });
                        window.location.reload();
                    }
                    modal.$scope.type = 'add';

                },
                //转发
                forwarding: function (cfg, callback) {
                    var defaults = {
                        senderDatetime: new Date().getTime(),
                        status: 1,
                        sequenceNo: 0

                    }
                    cfg = angular.extend({}, defaults, cfg);
                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    var id = cfg.id;
                    var datas;
                    var modal = $modal({
                        backdrop: 'static',
                        scope: scope,
                        template: CommonUtils.contextPathURL('/app/home/news/onlinetalk/forwarding.tpl.html')
                    });
                    var ueditor_config = {
                        initialFrameHeight: 150,
                        autoHeightEnabled: false,
                        toolbars: [
                            ["bold", "italic", "underline", "fontsize", "|", "insertorderedlist", "insertunorderedlist", "|", 'removeformat', 'forecolor', 'backcolor']
                        ],
                        //关闭字数统计
                        wordCount: false,
                        //关闭elementPath
                        elementPathEnabled: false
                    };
                    ModalFactory.afterShown(modal, function () {
                        if (modal_config2 == undefined) {
                            var a = UE.getEditor('container2', ueditor_config);
                            modal_config2 = modal;
                        } else {
                            var a = UE.getEditor('container2', ueditor_config);
                            a.render('container2');
                        }
                        $('#container .edui-editor.edui-default').css('width', '100%');
                    });
//                    modal.$scope.uploadForwardingOptions = {
//                        enableUrl: false,
//                        maxFiles: 1
//                    }
                    OnlinetalkService.replyForwarding({id: id}, function (data) {
                        var tmp = [];
                        if (angular.isArray(cfg.exclude) && cfg.exclude.length > 0) {
                            for (var i = 0; i < data.data.length; i++) {
                                (function (t) {
                                    var foo = data.data[t];
                                    if ($.inArray(foo.id, cfg.exclude) == -1) tmp.push(data.data[t]);
                                })(i);
                            }
                        } else {
                            tmp = data;
                        }
                        modal.$scope.onlineTalk = tmp;
                        modal.$scope.onlineTalk.type = 1;

                    });
                    var context = CommonUtils.loginContext() || {};
                    var foo = {
                        senderDatetime: new Date().getTime(),
                        status: 1,
                        sequenceNo: 0,
                        acceptedUser: {
                            username: context.username,
                            id: context.id
                        },
                        type: true
                    };
                    var that = this;
                    callback = callback || cfg.callback;
                    modal.$scope.onlineTalk = angular.extend({}, foo, datas);
                    modal.$scope.save = function (createNew) {
                        OnlinetalkService.save(modal.$scope.onlineTalk, function (data) {
//                            return false ;
                            if (data && data.success) {
                                if (createNew) {
                                    AlertFactory.success(modal.$scope, null, '保存成功!');
                                    modal.$scope.onlineTalk = angular.extend({}, foo);
                                    return;
                                }
                                if (angular.isFunction(callback || cfg.callback)) {
                                    callback.call(that, arguments);
                                }
                                modal.$scope.$hide();
                            } else {
                                AlertFactory.error(modal.$scope, '[' + (data && data.error || data.fail || '') + ']', '保存失败!');
                            }
                        });
                    }
                    //选择用户
                    modal.$scope.chooseParent = function () {
                        modal.$scope.type = 2;
                    }
                    //选择接收人
                    modal.$scope.emplo = function () {
                        that.emplo({scope: modal.$scope}, function (data) {
                        });
                    }
                    //选择接收组
                    modal.$scope.Group = function () {
                        that.Group({scope: modal.$scope}, function (data) {
                        });
                    }
                    //选择用户操作
                    modal.$scope.confirm = function (id, userName) {
                        if (!modal.$scope.items) modal.$scope.items = [];
//                        //console.info(modal.$scope.items.length);
                        if (modal.$scope.items.length > 0) {
                            //遍历选中的记录，获取其中的id放入items中
                            var items = [];
                            angular.forEach(modal.$scope.items, function (v) {
                                items.push(v.id);
                            });
                            if (items.length < 1) return;
                            //将各项的id用,分割  存放到id对象中
                            id = items.join(',');
                            modal.$scope.onlineTalk.useridStr = id;
                            var items = [];
                            angular.forEach(modal.$scope.items, function (v) {
                                items.push(v.username);
                            });
                            modal.$scope.onlineTalk.userName = items.join(' , ');
                        }

                        modal.$scope.type = 1;
//                        this.$hide();
                    };
                    //查询用户
                    OnlinetalkService.queryAllUsers(function (data) {
//                      UserService.queryAll(function(data){
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
                        modal.$scope.users = tmp;
                    })
                },
                //明细中的转发
                detailReplyForwarding: function (cfg, callback) {
                    var defaults = {
                        senderDatetime: new Date().getTime(),
                        status: 1,
                        sequenceNo: 0

                    }
                    cfg = angular.extend({}, defaults, cfg);
                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    var id = cfg.id;

                    var modal = $modal({
                        backdrop: 'static',
                        scope: scope,
                        template: CommonUtils.contextPathURL('/app/home/news/onlinetalk/detailForwarding.tpl.html')
                    });
                    var datas;
                    var ueditor_config = {
                        initialFrameHeight: 150,
                        autoHeightEnabled: false,
                        toolbars: [
                            ["bold", "italic", "underline", "fontsize", "|", "insertorderedlist", "insertunorderedlist", "|", 'removeformat', 'forecolor', 'backcolor']
                        ],
                        //关闭字数统计
                        wordCount: false,
                        //关闭elementPath
                        elementPathEnabled: false
                    };
                    ModalFactory.afterShown(modal, function () {
                        if (modal_config3 == undefined) {
                            var a = UE.getEditor('container3', ueditor_config);
                            modal_config3 = modal;
                        } else {
                            var a = UE.getEditor('container3', ueditor_config);
                            a.render('container3');
                        }
                        $('#container .edui-editor.edui-default').css('width', '100%');
                    });
//                    modal.$scope.uploadDetailReplyOptions = {
//                        enableUrl: false,
//                        maxFiles: 1
//                    }
                    OnlinetalkService.reply({id: id}, function (data) {
                        if (angular.isArray(cfg.exclude) && cfg.exclude.length > 0) {
                            for (var i = 0; i < data.data.length; i++) {
                                (function (t) {
                                    var foo = data.data[t];
                                    if ($.inArray(foo.id, cfg.exclude) == -1) tmp.push(data.data[t]);
                                })(i);
                            }
                        } else {
                            modal.$scope.onlineTalk = data;
                            modal.$scope.onlineTalk.type = 1;
                        }
                    });
                    //选择用户
                    OnlinetalkService.queryAllUsers(function (data) {
//                      UserService.queryAll(function(data){
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
                        modal.$scope.users = tmp;
                    })
                    //选择用户
                    modal.$scope.chooseParent = function () {
                        modal.$scope.type = 2;
                    }
                    //选择用户操作
                    modal.$scope.confirm = function (id, userName) {
                        if (!modal.$scope.items) modal.$scope.items = [];
//                        //console.info(modal.$scope.items.length);
                        if (modal.$scope.items.length > 0) {
                            //遍历选中的记录，获取其中的id放入items中
                            var items = [];
                            angular.forEach(modal.$scope.items, function (v) {
                                items.push(v.id);
                            });
                            if (items.length < 1) return;
                            //将各项的id用,分割  存放到id对象中
                            id = items.join(',');
                            modal.$scope.onlineTalk.useridStr = id;
                            var items = [];
                            angular.forEach(modal.$scope.items, function (v) {
                                items.push(v.username);
                            });
                            modal.$scope.onlineTalk.userName = items.join(' , ');
                        }

                        modal.$scope.type = 1;
//                        this.$hide();
                    };
                    var context = CommonUtils.loginContext() || {};
                    var foo = {
                        senderDatetime: new Date().getTime(),
                        status: 1,
                        sequenceNo: 0,
                        acceptedUser: {
                            username: context.username,
                            id: context.id
                        },
                        type: 1

                    };
                    var that = this;
                    callback = callback || cfg.callback;
                    modal.$scope.onlineTalk = angular.extend({}, foo, datas);
                    modal.$scope.save = function (createNew) {
                        OnlinetalkService.save(modal.$scope.onlineTalk, function (data) {
//                            return false ;
                            if (data && data.success) {
                                if (createNew) {
                                    AlertFactory.success(modal.$scope, null, '保存成功!');
                                    modal.$scope.onlineTalk = angular.extend({}, foo);
                                    return;
                                }
                                if (angular.isFunction(callback || cfg.callback)) {
                                    callback.call(that, arguments);
                                }
                                modal.$scope.$hide();
                            } else {
                                AlertFactory.error(modal.$scope, '[' + (data && data.error || data.fail || '') + ']', '保存失败!');
                            }
                        });
                    }

                },
                //发件人用户
                //弹出div选择负责人
                sendUser: function (cfg, callback) {
                    var defaults = {
                        scope: null,//必选项
                        value: null,//可选项，用于回显
                        exclude: [],//可选项，要排除的数据，用于列出非自身的数据
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    }
                    cfg = angular.extend({}, defaults, cfg);

                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';

                    callback = callback || cfg.callback;

                    var _t = this;
                    var modal = $modal({
                        scope: scope,
                        template: CommonUtils.contextPathURL('/app/home/news/onlinetalk/singleUser.tpl.html')
                    });

                    //从员工表中获得所有的员工
                    OnlinetalkService.queryAllUsers(function (data) {
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
                        modal.$scope.principals = tmp;
//                        //console.dir(tmp);
                    });

                    //modal.$scope.principals = [{id:1,username:'admin'},{id:2,username:'刘德华'}];

                    modal.$scope.selected = null;
                    modal.$scope.confirm = function () {
                        //被选中的用户
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
                },
                //      title:模态对话框的名称
                //      min：至少选择的个数
                //      max:最多可以选择的格式
                //      defaultChecked:默认选中的节点（以id为标准）
                // 参数2：function(items)
                //      items:选择的元素
                dbTreePicker: function (options, callback) {
                    var defaults = {
                        title: '选择组',
                        min: 1
                    };
                    options = angular.extend(defaults, options);
                    options.initLeft = function () {
                        return CommonUtils.promise(function (defer) {
                            var result = Group.validTree();
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
                },
                users: function (cfg, callback) {
                    var defaults = {
                        scope: null,//必选项
                        value: null,//可选项，用于回显
                        exclude: [],//可选项，要排除的数据，用于列出非自身的数据
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    }
                    cfg = angular.extend({}, defaults, cfg);

                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    callback = callback || cfg.callback;

                    var _t = this;
                    var modal = $modal({
                        backdrop: 'static',
                        scope: cfg.scope,
                        template: CommonUtils.contextPathURL('/app/notice/notice/template/common-modalEmployee-confirm.tpl.html')
                    });
                    var defaults_data = {
                        orderBy: 'createdDatetime',
                        reverse: 'true',
                        groupName: null
                    };
                    modal.$scope.reset = function () {
                        modal.$scope.conditionModal = angular.extend({}, defaults_data);
                    };

                    modal.$scope.reset();
                    modal.$scope.pager = {
                        fetch: function () {
                            var param = angular.extend({}, modal.$scope.conditionModal, {
                                start: this.start,
                                limit: this.limit
                            });
                            return CommonUtils.promise(function (defer) {
                                var result = User.query(param);
                                AlertFactory.handle(modal.$scope, result, function (data) {
                                    tmp = data.data || {total: 0};
                                    modal.$scope.beaned = tmp.data;
                                    defer.resolve(data);
                                });
                            });
                        }
                    };

                    //查询数据
                    modal.$scope.query = function () {
                        (modal.$scope.pager.query || modal.$scope.pager.fetch)();
                    };

                    var destroy = modal.$scope.$watch('conditionModal', function (value, oldValue) {
                        Debounce.delay(modal.$scope.query, 400);
                    }, true);
                    modal.$scope.$on('$destroy', destroy)
                    modal.$scope.addGroup = function (id) {
                        _t.dbTreePicker({
                            title: '接收组'
                        }, function (data) {
                            var ids = '';
                            var names = '';
                            angular.forEach(data, function (o) {
                                ids += o.id + ",";
                            });
                            angular.forEach(data, function (o) {
                                names += o.name + ",";
                            });
                            modal.$scope.conditionModal.groupIds = ids;
                            modal.$scope.conditionModal.groupName = names.substring(0, names.length - 1);
                        });
                    };
                    modal.$scope.clearGroup = function () {
                        modal.$scope.conditionModal.groupIds = null;
                        modal.$scope.conditionModal.groupName = null;
                    };

                    modal.$scope.beans = [];

                    modal.$scope.selectOne = function () {
                        if (!modal.$scope.itemd || !modal.$scope.itemd.length)return;
                        for (var j = 0; j < modal.$scope.itemd.length; j++) {
                            var itemdId = modal.$scope.itemd[j].id
                            for (var i = 0; i < modal.$scope.beaned.length; i++) {
                                var ids = modal.$scope.beaned[i].id;
                                if (ids == itemdId) {
                                    modal.$scope.beans.push(modal.$scope.beaned[i])
                                    modal.$scope.beaned.splice(i, 1);
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
                            for (var i = 0; i < modal.$scope.beans.length; i++) {
                                var ids = modal.$scope.beans[i].id;
                                if (ids == itemsId) {
                                    modal.$scope.beaned.push(modal.$scope.beans[i])
                                    modal.$scope.beans.splice(i, 1);
                                }
                            }
                        }
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.selectAll = function () {
                        if (modal.$scope.beans != null) {
                            for (var i = 0; i < modal.$scope.beaned.length; i++) {
                                modal.$scope.beans.push(modal.$scope.beaned[i])
                            }
                        } else {
                            modal.$scope.beans = tmp.data;
                        }
                        modal.$scope.beaned = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.deleteAll = function () {
                        if (modal.$scope.beaned != null) {
                            for (var i = 0; i < modal.$scope.beans.length; i++) {
                                modal.$scope.beaned.push(modal.$scope.beans[i])
                            }
                        } else {
                            modal.$scope.beaned = tmp.data;
                        }
                        modal.$scope.beans = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.selected = cfg.value;
                    modal.$scope.confirm = function () {
                        callback = callback || cfg.callback;
                        if (callback && angular.isFunction(callback)) {
                            callback.call(_t, modal.$scope.beans);
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
                },
                busi: function (cfg, callback) {
                    var defaults = {
                        scope: null,//必选项
                        value: null,//可选项，用于回显
                        exclude: [],//可选项，要排除的数据，用于列出非自身的数据
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    }
                    cfg = angular.extend({}, defaults, cfg);

                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    callback = callback || cfg.callback;

                    var _t = this;

                    var modal = $modal({
                        backdrop: 'static',
                        scope: cfg.scope,
                        template: CommonUtils.contextPathURL('/app/notice/notice/template/common-busi.tpl.html')
                    });
                    // 系统
                    NoticeConstant.busiType(function (data) {
                        scope.beaned = data;
                    });
                    scope.beans = [];
                    modal.$scope.selectOne = function () {
                        if (!modal.$scope.itemd || !modal.$scope.itemd.length)return;
                        for (var j = 0; j < modal.$scope.itemd.length; j++) {
                            var itemdId = modal.$scope.itemd[j].id
                            for (var i = 0; i < scope.beaned.length; i++) {
                                var ids = scope.beaned[i].id;
                                if (ids == itemdId) {
                                    scope.beans.push(scope.beaned[i])
                                    scope.beaned.splice(i, 1);
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
                            for (var i = 0; i < scope.beans.length; i++) {
                                var ids = scope.beans[i].id;
                                if (ids == itemsId) {
                                    scope.beaned.push(scope.beans[i])
                                    scope.beans.splice(i, 1);
                                }
                            }
                        }
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.selectAll = function () {
                        if (scope.beans != null) {
                            for (var i = 0; i < scope.beaned.length; i++) {
                                scope.beans.push(scope.beaned[i])
                            }
                        } else {
                            scope.beans = tmp.data;
                        }
                        scope.beaned = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.deleteAll = function () {
                        if (scope.beaned != null) {
                            for (var i = 0; i < scope.beans.length; i++) {
                                scope.beaned.push(scope.beans[i])
                            }
                        } else {
                            scope.beaned = tmp.data;
                        }
                        scope.beans = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.confirm = function () {
                        var userGroup = null;
                        callback = callback || cfg.callback;
                        if (callback && angular.isFunction(callback)) {
                            callback.call(_t, userGroup);
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
                },
                orgPos: function (cfg, callback) {
                    var defaults = {
                        scope: null,//必选项
                        value: null,//可选项，用于回显
                        exclude: [],//可选项，要排除的数据，用于列出非自身的数据
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    }
                    cfg = angular.extend({}, defaults, cfg);

                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    callback = callback || cfg.callback;

                    var _t = this;

                    var modal = $modal({
                        backdrop: 'static',
                        scope: cfg.scope,
                        template: CommonUtils.contextPathURL('/app/notice/notice/template/common-orgPos.tpl.html')
                    });
                    var type = scope.type;
                    scope.beans = [];
                    modal.$scope.selectOne = function () {
                        if (!modal.$scope.itemd || !modal.$scope.itemd.length)return;
                        for (var j = 0; j < modal.$scope.itemd.length; j++) {
                            var itemdId = modal.$scope.itemd[j].id
                            for (var i = 0; i < scope.beaned.length; i++) {
                                var ids = scope.beaned[i].id;
                                if (ids == itemdId) {
                                    scope.beans.push(scope.beaned[i])
                                    scope.beaned.splice(i, 1);
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
                            for (var i = 0; i < scope.beans.length; i++) {
                                var ids = scope.beans[i].id;
                                if (ids == itemsId) {
                                    scope.beaned.push(scope.beans[i])
                                    scope.beans.splice(i, 1);
                                }
                            }
                        }
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.selectAll = function () {
                        if (scope.beans != null) {
                            for (var i = 0; i < scope.beaned.length; i++) {
                                scope.beans.push(scope.beaned[i])
                            }
                        } else {
                            scope.beans = tmp.data;
                        }
                        scope.beaned = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.deleteAll = function () {
                        if (scope.beaned != null) {
                            for (var i = 0; i < scope.beans.length; i++) {
                                scope.beaned.push(scope.beans[i])
                            }
                        } else {
                            scope.beaned = tmp.data;
                        }
                        scope.beans = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.confirm = function () {
                        var userGroup = null;
                        callback = callback || cfg.callback;
                        if (callback && angular.isFunction(callback)) {
                            callback.call(_t, userGroup);
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
                },
                pos: function (cfg, callback) {
                    var defaults = {
                        scope: null,//必选项
                        value: null,//可选项，用于回显
                        exclude: [],//可选项，要排除的数据，用于列出非自身的数据
                        callback: null,//点击确定后要执行的函数
                        afterShown: null//模态对话框显示完成后要执行的函数
                    }
                    cfg = angular.extend({}, defaults, cfg);

                    var scope = cfg.scope;
                    if (!scope) throw '使用模态对话框时必须指定scope!';
                    callback = callback || cfg.callback;

                    var _t = this;

                    var modal = $modal({
                        backdrop: 'static',
                        scope: cfg.scope,
                        template: CommonUtils.contextPathURL('/app/notice/notice/template/common-pos.tpl.html')
                    });
                    scope.beans = [];
                    modal.$scope.selectOne = function () {
                        if (!modal.$scope.itemd || !modal.$scope.itemd.length)return;
                        for (var j = 0; j < modal.$scope.itemd.length; j++) {
                            var itemdId = modal.$scope.itemd[j].id
                            for (var i = 0; i < scope.beaned.length; i++) {
                                var ids = scope.beaned[i].id;
                                if (ids == itemdId) {
                                    scope.beans.push(scope.beaned[i])
                                    scope.beaned.splice(i, 1);
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
                            for (var i = 0; i < scope.beans.length; i++) {
                                var ids = scope.beans[i].id;
                                if (ids == itemsId) {
                                    scope.beaned.push(scope.beans[i])
                                    scope.beans.splice(i, 1);
                                }
                            }
                        }
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.selectAll = function () {
                        if (scope.beans != null) {
                            for (var i = 0; i < scope.beaned.length; i++) {
                                scope.beans.push(scope.beaned[i])
                            }
                        } else {
                            scope.beans = tmp.data;
                        }
                        scope.beaned = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.deleteAll = function () {
                        if (scope.beaned != null) {
                            for (var i = 0; i < scope.beans.length; i++) {
                                scope.beaned.push(scope.beans[i])
                            }
                        } else {
                            scope.beaned = tmp.data;
                        }
                        scope.beans = [];
                        modal.$scope.itemd = [];
                        modal.$scope.items = [];
                    }
                    modal.$scope.confirm = function () {
                        var userGroup = null;
                        callback = callback || cfg.callback;
                        if (callback && angular.isFunction(callback)) {
                            callback.call(_t, userGroup);
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
    )
    ;
})(window, angular, jQuery);