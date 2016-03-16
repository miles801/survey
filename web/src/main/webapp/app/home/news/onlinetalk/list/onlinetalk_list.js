/**
 * Created by liuxq
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.home.onlinetalk.edit', [
        'ngRoute',
        'eccrm.home.onlinetalk',
        'eccrm.base.onlinetalk.modal', 'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.user.modal']);
    var contextPath = $('#contextPath').val();
    app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
//            .when('/onlineTalk', {templateUrl: 'app/home/news/onlinetalk/edit/onlinetab.html', controller: 'OnlineTalkController'})
            .when('/shou', {templateUrl: contextPath + 'app/home/news/onlinetalk/edit/shou.html', controller: 'ShouController'})
            .when('/fa', {templateUrl: contextPath + 'app/home/news/onlinetalk/edit/fatab.html', controller: 'FaController'})
            .otherwise({redirectTo: '/onlineTalk'});
    }]);
    app.controller('OnlineTalkEditController', function ($scope, $location, OnlinetalkService, OnlineTalkModal, Debounce) {
        var active = $('#active').val();
        $scope.active = 0;
        $scope.routeOptions = [
//            { url: 'onlineTalk', name: '在线人员', active: true },
            { url: 'shou', name: '收到的消息' },
            { url: 'fa', name: '发送的消息' }
        ];
        $scope.tab = 'onlineTalk';
        $scope.sale = {
            period: 2,
            target: 1,
            other: 1,
            type: 1
        };
        $scope.changeTab = function (path) {
            $scope.tab = path;
            $location.path(path);
        }

        /*$scope.condition = {
         status: 0
         };
         $scope.conditionSend = {
         status: 0
         };
         var pageType = $('#pageType').val();
         var id = $('#id').val();
         var defaults;
         $scope.back = function () {
         $window.history.back();
         }
         if (pageType == 'detail') {//查看页面
         OnlinetalkService.get({id: id}, function (data) {
         $scope.onlinTalkReadLog = data;
         ////console.dir(data);
         $('input,textarea,select').attr('disabled', 'disabled');
         });
         }
         //查询数据
         $scope.query = function () {
         //do your logic code here
         }
         //回到上一个页面
         $scope.back = function () {
         window.history.back();
         }
         //监听页面的类型，用于初始化页面
         var cancelEditType = $scope.$watch('page', function (value) {
         if (!value || !value.type) return;
         var id = $('#id').val();
         if (value.type == 'detail') {//查看页面
         OnlinetalkService.get({id: id}, function (data) {
         $scope.usergroup = data;
         $('input,textarea,select').attr('disabled', 'disabled');
         });
         }
         cancelEditType();//注销监听事件
         }, true)
         $scope.pager = {
         queryFunction: load //这里是分页所需要的方法
         }
         $scope.pagerLetter = {
         queryFunction: loadLetter //这里是分页所需要的方法
         }
         function load(callback) {
         if (callback) {
         $scope.queryCallback = callback;
         } else {
         callback = $scope.queryCallback;
         }
         var param = angular.extend({}, {start: $scope.pager.start || 0, limit: $scope.pager.limit || 10}, $scope.condition);
         OnlinetalkService.queryInbox(param, callback);
         }

         function loadLetter(callback) {
         if (callback) {
         $scope.queryCallback = callback;
         } else {
         callback = $scope.queryCallback;
         }
         var param = angular.extend({}, {start: $scope.pager.start || 0, limit: $scope.pager.limit || 10}, $scope.conditionSend);
         OnlinetalkService.queryLetter(param, callback);
         }

         //查询数据
         $scope.query = function (id) {
         $scope.pager.init && $scope.pager.init();
         load(function (data) {
         $scope.pager.total = data['total'];
         $scope.pager.ready = true;
         $scope.onlineTalks = data;

         });
         }
         //查询数据
         $scope.queryLetter = function (id) {
         $scope.pagerLetter.init && $scope.pagerLetter.init();
         loadLetter(function (data) {
         $scope.pager.total = data['total'];
         $scope.pager.ready = true;
         $scope.onlineTalkLetter = data;
         });
         }
         //        $scope.query();
         //        $scope.queryLetter();

         $scope.view = function (id) {
         OnlinetalkService.get({id: id}, function (data) {
         $scope.usergroup = data;
         $('input,textarea,select').attr('disabled', 'disabled');
         });
         }

         $scope.edit = function (id) {
         window.location.href = 'onlinetalk/' + id + '/modify';
         }

         $scope.detail = function (id) {
         window.location.href = 'onlinetalk/' + id + '/update';
         }
         $scope.pagerUser = {
         queryFunction: loadUser //这里是分页所需要的方法
         }
         //用户列表
         function loadUser(callback) {
         if (callback) {
         $scope.queryCallback = callback;
         } else {
         callback = $scope.queryCallback;
         }
         var param = angular.extend({}, {start: $scope.pager.start || 0, limit: $scope.pager.limit || 10}, $scope.condition);
         OnlinetalkService.queryAllUsers(param, callback);
         }

         $scope.queryUser = function (id) {
         $scope.pager.init && $scope.pager.init();
         loadUser(function (data) {
         $scope.pager.total = data['total'];
         $scope.pager.ready = true;
         $scope.users = data;
         //console.dir(data);
         });
         }

         $scope.pagerUsergroup = {
         queryFunction: loadUsergroup //这里是分页所需要的方法
         }
         //用户列表
         function loadUsergroup(callback) {
         if (callback) {
         $scope.queryCallback = callback;
         } else {
         callback = $scope.queryCallback;
         }
         var param = angular.extend({}, {start: $scope.pagerUsergroup.start || 0, limit: $scope.pagerUsergroup.limit || 10}, $scope.condition);
         OnlinetalkService.queryAllUsergroup(param, callback);
         }

         $scope.queryUsergroup = function (id) {
         alert('usergroup');
         $scope.pagerUsergroup.init && $scope.pagerUsergroup.init();
         loadUser(function (data) {
         $scope.pagerUsergroup.total = data['total'];
         $scope.pagerUsergroup.ready = true;
         $scope.usersgroup = data;
         //console.dir(data);
         });
         }

         $scope.$watch('condition', function (value, oldvalue) {
         if (value === oldvalue) return;
         Debounce.delay($scope.query, 400);
         }, true);

         $scope.$watch('conditionSend', function (value, oldvalue) {
         if (value === oldvalue) return;
         Debounce.delay($scope.query, 400);
         }, true);

         //发送alert
         $scope.send = function () {
         OnlineTalkModal.sendModal({scope: $scope}, function (data) {
         })
         };


         //回复alert
         $scope.reply = function (id) {
         OnlineTalkModal.replyModal({scope: $scope, id: id}, function (data) {
         })
         };
         //转发alert
         $scope.forwarding = function (id) {
         OnlineTalkModal.forwarding({scope: $scope, id: id}, function (data) {

         })
         };
         //阅读alert
         $scope.addRead = function (id) {
         OnlineTalkModal.userList({scope: $scope, id: id});
         };
         //收信箱负责人alert
         $scope.users = function () {
         OnlineTalkModal.sendUser({scope: $scope}, function (data) {
         //将选中的负责人显示到负责人的文本框中
         $scope.condition.userid = data.id;
         $scope.condition.acceptedUser = {
         name: data.username
         }

         });
         }
         //发信箱负责人alert
         $scope.sendUsers = function () {
         OnlineTalkModal.sendUser({scope: $scope}, function (data) {
         //将选中的负责人显示到负责人的文本框中
         //console.dir($scope);
         $scope.conditionSend.userid = data.id;
         //console.dir($scope.conditionSend.userid);
         $scope.conditionSend.senderUser = {
         name: data.username
         }

         });
         }
         $scope.detailReplyForwarding = function (id) {
         OnlineTalkModal.forwarding({scope: $scope, id: id}, function (data) {
         })
         };


         $scope.onlinetalks = {total: 0, data: []};*/
    });
    app.controller('OnlineTalkController', function ($scope, LoginLogService, AlertFactory, OnlineTalkModal, Debounce, $window, $http) {
        $scope.type = 1;
        $scope.condition = {};
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                $scope.logs = LoginLogService.onlineUsers(param);
                return $scope.logs;
            }
        };
        var context = Comm.getCrmContext() || {};
        $scope.userId = context.getUserId()
        $scope.query = function () {
            $scope.pager.query();
        };
        var destroy = $scope.$watch('condition', function (value, oldValue) {
            Debounce.delay($scope.query, 400);
        }, true);
        $scope.$on('$destroy', destroy);
        $scope.logout = function (userIds) {
            $http.get('logout/kick?userIds=' + userIds)
                .success(function () {
                    AlertFactory.success($scope, " ", '踢出成功!');
                    $scope.query();
                }).error(function (result) {
                    AlertFactory.handle($scope, result);
                });
        };
        //发送alert
        $scope.send = function (id, name) {
            var user = []
            //判断id是通过点击没条记录后的删除图标（会传递id）还是点击的上面删除按钮
            if (id == undefined && name == undefined) {
                //遍历选中的记录，获取其中的id放入items中
                var items = [];
                angular.forEach($scope.items, function (v) {
                    items.push(v.username);
                });
                if (items.length < 1) return;
                //将各项的id用,分割  存放到id对象中
                for (var i = 0; i < $scope.items.length; i++) {
                    user.push({
                        id: $scope.items[i].userId,
                        username: $scope.items[i].username
                    })
                }
                $scope.username = items.join(',');
            } else {
                user = {
                    id: id,
                    username: name
                }
                $scope.username = name;
            }
            $scope.flag = true;
            OnlineTalkModal.sendModal({scope: $scope, user: user}, function (data) {
            })
        };

    });
    app.controller('ShouController', function ($scope, UserModal, $window, ModalFactory, Debounce, CommonUtils, AlertFactory, OnlinetalkService, CommunicateConstant, OnlineTalkModal) {
        $scope.type = 1;
        var defaults = {
            orderBy: 'createdDatetime',
            reverse: 'true',
            createdUserName: null
        };
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
        }
        // 状态
        CommunicateConstant.status(function (data) {
            $scope.statuss = data;
        });
        // 类型
        CommunicateConstant.type(function (data) {
            $scope.types = data;
        });
        $scope.reset();
        //初始化分页信息
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                return CommonUtils.promise(function (defer) {
                    var result = OnlinetalkService.queryInbox(param);
                    AlertFactory.handle($scope, result, function (data) {
                        data = data || {total: 0};
                        $scope.onlineTalks = data;
                        defer.resolve(data);
                    });
                });
            },
            finishInit: function () {
                this.query();
            }
        }
        $scope.cleanInvalidStartDate = function () {
            $('#_invalidStartDate').val(null)
        }
        $scope.cleanInvalidEndDate = function () {
            $('#_invalidEndDate').val(null)
        }
        $scope.query = function () {
            var _invalidStartDate = $('#_invalidStartDate').val();
            if (_invalidStartDate) {
                $scope.condition.invalidStartDate = _invalidStartDate;
            }
            var _invalidEndDate = $('#_invalidEndDate').val();
            if (_invalidEndDate) {
                $scope.condition.invalidEndDate = _invalidEndDate;
            }
            ($scope.pager.query || $scope.pager.fetch)();
        }
        var destroy = $scope.$watch('condition', function (value, oldvalue) {
            Debounce.delay($scope.query, 400);
        }, true);
        $scope.$on('$destroy', destroy);
        //回复alert
        $scope.reply = function (id) {
            OnlineTalkModal.replyModal({scope: $scope, id: id}, function (data) {
            })
        };
        $scope.single = function (id) {
            UserModal.single({scope: $scope}, function (data) {
                $scope.condition.creatorName = data.username;
            });
        };
    });
    app.controller('FaController', function ($scope, UserModal, $window, ModalFactory, Debounce, CommonUtils, AlertFactory, OnlinetalkService, CommunicateConstant, OnlineTalkModal) {
        $scope.type = 1;
        var id = $('#id').val();
        var type = $('#pageType').val();

        var defaults = {
            orderBy: 'createdDatetime',
            reverse: 'true',
            createdUserName: null
        };
        $scope.reset = function () {
            $scope.condition = angular.extend({}, defaults);
        }
        // 状态
        CommunicateConstant.status(function (data) {
            $scope.statuss = data;
        });
        // 类型
        CommunicateConstant.type(function (data) {
            $scope.types = data;
        });
        $scope.reset();
        //初始化分页信息
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({}, $scope.condition, {start: this.start, limit: this.limit});
                return CommonUtils.promise(function (defer) {
                    var result = OnlinetalkService.queryLetter(param);
                    AlertFactory.handle($scope, result, function (data) {
                        data = data || {total: 0};
                        $scope.onlineTalks = data;
                        defer.resolve(data);
                    });
                });
            },
            finishInit: function () {
                this.query();
            }
        }
        $scope.cleanInvalidStartDate = function () {
            $('#_invalidStartDate').val(null)
        }
        $scope.cleanInvalidEndDate = function () {
            $('#_invalidEndDate').val(null)
        }
        $scope.query = function () {
            var _invalidStartDate = $('#_invalidStartDate').val();
            if (_invalidStartDate) {
                $scope.condition.invalidStartDate = _invalidStartDate;
            }
            var _invalidEndDate = $('#_invalidEndDate').val();
            if (_invalidEndDate) {
                $scope.condition.invalidEndDate = _invalidEndDate;
            }
            ($scope.pager.query || $scope.pager.fetch)();
        }
        var destroy = $scope.$watch('condition', function (value, oldvalue) {
            Debounce.delay($scope.query, 400);
        }, true);
        $scope.$on('$destroy', destroy);

        //修改装态
        $scope.updateStatus = function (id) {
            //判断id是通过点击没条记录后的删除图标（会传递id）还是点击的上面删除按钮
            if (id == undefined) {
                //遍历选中的记录，获取其中的id放入items中
                var items = [];
                angular.forEach($scope.items, function (v) {
                    items.push(v.id);
                });
                if (items.length < 1) return;
                //将各项的id用,分割  存放到id对象中
                id = items.join(',');
            }
            ModalFactory.cancel($scope, function (data) {
                var result = OnlinetalkService.updateStatus({ids: id});
                AlertFactory.handle($scope, result, $scope.query());
            });
        }
        //阅读alert
        $scope.addRead = function (id) {
            OnlineTalkModal.userList({scope: $scope, id: id});
        };
        $scope.single = function (id) {
            UserModal.single({scope: $scope}, function (data) {
                $scope.condition.creatorName = data.username;
            });
        };
        //发送alert
        $scope.send = function () {
            OnlineTalkModal.sendModal({scope: $scope}, function (data) {
            })
        };

    });


})(window, angular, jQuery);
