(function (window, angular, $) {
    var app = angular.module('eccrm.base.user.edit', [
        'eccrm.base.user',
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.employee.modal'
    ]);
    app.controller('UserEditController', function ($scope, CommonUtils, User, AlertFactory, UserConstant, EmployeeModal, $filter) {
            // 定义变量
            $scope.user = {};
            var originalName;
            var defaults = {
                createdDatetime: new Date().getTime(),
                creatorName: CommonUtils.loginContext().username,
                startDate: $filter('eccrmDatetime')(new Date().getTime()),
                type: 'OFFICIAL',
                gender: 'BP_MAN',
                status: 'ACTIVE'
            };

            // 初始化参数
            UserConstant.type(function (userTypes) {
                $scope.types = userTypes;
            });

            UserConstant.status(function (userStatus) {
                $scope.status = userStatus;
            });

            UserConstant.genders(function (genders) {
                $scope.genders = genders;
            });


            // 设置员工信息
            var setEmpInfo = function (obj) {
                var user = $scope.user;
                user.deptId = obj.organizationId;// 机构
                user.deptName = obj.organizationName;// 机构名称
                user.employeeId = obj.id;
                user.employeeNo = obj.employeeCode;
                user.employeeName = obj.employeeName;
                user.mobilePhone = obj.mobile;
                user.officePhone = obj.extensionNumber;
            };
            // 选择员工
            $scope.pickEmp = function () {
                EmployeeModal.pickEmployee({}, setEmpInfo);
            };

            // 清空选择的员工信息
            $scope.clearEmp = function () {
                var user = $scope.user;
                user.employeeId = null;
                user.employeeNo = null;
                user.employeeName = null;
                user.mobilePhone = null;
                user.officePhone = null;
                user.organizationId = null;
                user.organizationName = null;
            };

            // 回到上一个页面
            $scope.back = CommonUtils.back;

            // 保存/更新前的合法性验证
            var verify = function (callback) {
                // 验证用户组必选
                // 验证临时用户时时间的必填
                // 验证用户名是否重复
                CommonUtils.chain([function () {
                    if (!$scope.user.type) {
                        this.reject('请选择用户类型!');
                    } else if ($scope.user.type === 'TEMP' && !($scope.user.startDate && $scope.user.endDate)) {
                        this.reject('请选择用户的有效期!');
                    } else {
                        this.resolve(true);
                    }
                }, function () {
                    var context = this;
                    if ($scope.user.username === originalName) {
                        context.resolve(true);
                    } else {
                        var result = User.hasName({name: CommonUtils.encode($scope.user.username)});
                        AlertFactory.handle($scope, result, function (data) {
                            if (data.data === true) {
                                context.reject('用户名重复!');
                            } else {
                                context.resolve(true);
                            }
                        });
                    }
                }], callback, function (reason) {
                    AlertFactory.error($scope, reason);
                });
            };

            // 将my97中选择的时间赋值到$scope.user对象中
            var wrapDateToUser = function () {
                $scope.user.endDate = $('#endDate').val();
                $scope.user.startDate = $('#startDate').val();
            };
            $scope.save = function (createNew) {
                verify(function () {
                    wrapDateToUser();
                    var result = User.save($scope.user);
                    AlertFactory.handle($scope, result, function (data) {
                        if (createNew) {
                            $scope.user = angular.extend({}, defaults);
                            $scope.groups = null;
                            return;
                        }
                        $scope.back();
                    });
                });
            };

            // 在更新页面需要加载用户所属组
            $scope.update = function () {
                verify(function () {
                    wrapDateToUser();
                    var promise = User.update($scope.user, function () {
                        CommonUtils.addTab('update');
                        AlertFactory.success(null, '更新成功!');
                        CommonUtils.delay($scope.back, 3000);
                    });
                    CommonUtils.loading(promise)
                });
            };

            // 根据id加载用户信息
            var load = function (id, callback) {
                var result = User.get({id: id});
                AlertFactory.handle($scope, result, function (data) {
                    data = data.data || {};
                    var user = $scope.user = data;
                    originalName = data.username;
                    if (angular.isFunction(callback)) {
                        callback.call($scope);
                    }
                    // 转换时间为my97能识别的格式
                    user.endDate = $filter('eccrmDatetime')(user.endDate);
                    user.startDate = $filter('eccrmDatetime')(user.startDate);
                })
            };


            //执行方法
            var pageType = $scope.pageType = $('#pageType').val();
            var id = $('#id').val();
            if (pageType == 'modify') {//更新页面
                $scope.canUpdate = true;
                load(id);
            } else if (pageType == 'detail') {//查看页面
                load(id, function () {
                    $('input,textarea,select').attr('disabled', 'disabled');
                })
            } else if (pageType == 'add') {
                $scope.user = angular.extend({}, defaults);
            }
        }
    )
})(window, angular, jQuery);