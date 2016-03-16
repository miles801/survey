(function (window, angular, $) {
    //获取模块
    var app = angular.module("eccrm.panel.base.list", [
        'eccrm.angular',
        'eccrm.angularstrap',
        'eccrm.base.employee',
        'eccrm.base.user',
        'spec.youth'
    ]);


    //
    app.controller('BaseCtrl', function ($http, User, EmployeeService, $scope, CommonUtils, AlertFactory, YouthService) {
        $scope.beans = {};
        $scope.isShow = false;
        var id = $('#userId').val();
        // 加载员工信息
        var loadEmployee = function () {
            var promise = EmployeeService.get({id: id}, function (data) {
                data = data.data || {};
                $scope.beans = data;
                var imageId = data.picture;
                if (imageId) {
                    $('#imageId').html('<img src="' + CommonUtils.contextPathURL('/attachment/view?id=' + imageId) + '" width="150" height="180">');
                } else {
                    $('#imageId').html('<img src="' + CommonUtils.contextPathURL('/style/standard/images/default_tx.png') + '" width="150" height="180">');
                }

                if (!$scope.beans.orgId) {
                    AlertFactory.error('请先完善个人信息!(选择“所属县区”，更新后请重新登录!)')
                } else {
                    // 查询匹配状态
                    YouthService.pageQuery({ownerId: id, states: ['YELLOW', 'BLUE_WAIT', 'GRAY_WAIT']}, function (o) {
                        o = o.data || {total: 0, data: []};
                        if (o.total < 1) {
                            $scope.beans.matched = false;
                        } else {
                            $scope.beans.matched = o.data[0].id;
                            $scope.youth = o.data[0];
                            if ($scope.youth.picture) {
                                $('#image2').html('<img src="' + CommonUtils.contextPathURL('/attachment/view?id=' + $scope.youth.picture) + '" width="150" height="180">');
                            } else {
                                $('#image2').html('<img src="' + CommonUtils.contextPathURL('/style/standard/images/default_tx.png') + '" width="150" height="180">');
                            }
                        }
                    });
                }
            });
            CommonUtils.loading(promise);
        };

        // 拥有查看报表的权限
        $scope.hasReportPermission = false;
        $http.get(CommonUtils.contextPathURL('/auth/accreditFunc/hasPermission?code=YOUTH_REPORT'))
            .success(function (data) {
                if (data.data) {
                    // 获取报表数据
                    var promise = YouthService.analysis(function (data) {
                        var $report = $('#report');
                        data = data.data || [];
                        var total = ['', '银川市团委 ', 0, 0, 0, 0, 0, 0];
                        angular.forEach(data, function (o, index) {
                            total[2] += o[2];
                            total[3] += o[3];
                            total[4] += o[4];
                            total[5] += o[5];
                            total[6] += o[6];
                        });
                        data.unshift(total);
                        angular.forEach(data, function (o) {
                            var node = $('<div style="height: 350px;width: 400px;float: left;margin: 10px 20px;" ></div>');
                            $report.append(node);
                            var pie = echarts.init(node[0]);
                            var options = {
                                title: {
                                    text: o[1] + '帮扶统计',
                                    subtext: '总计-' + o[2] + '个',
                                    x: 'center'
                                },
                                tooltip: {
                                    trigger: 'item',
                                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                                },
                                legend: {
                                    orient: 'vertical',
                                    left: 'left',
                                    data: ['未配对', '帮扶中', '帮扶成功', '解除帮扶']
                                },
                                color: ['#c12e2a', '#fcf61b', '#428bca', '#a2aeae'],
                                series: [
                                    {
                                        name: '闲散青年人数(个)',
                                        type: 'pie',
                                        radius: '55%',
                                        data: (function () {
                                            var arr = [];
                                            arr.push({name: '未配对', value: o[3]});
                                            arr.push({name: '帮扶中', value: o[4]});
                                            arr.push({name: '帮扶成功', value: o[5]});
                                            arr.push({name: '解除帮扶', value: o[6]});
                                            return arr;
                                        })(),
                                        center: ['50%', '60%']
                                    }
                                ]
                            };
                            pie.setOption(options);
                        })
                    });
                    CommonUtils.loading(promise);
                }

            });

        loadEmployee();

    });


})(window, angular, jQuery);
