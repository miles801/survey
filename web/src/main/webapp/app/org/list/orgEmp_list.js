/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (window, angular, $) {
    var app = angular.module('eccrm.organi.organiEmp.list', [ 'eccrm.angular',
        'base.org','eccrm.organi.Emp',
        'eccrm.angularstrap']);
    app.controller('OrganiEmpListController', function ($scope, $window,AlertFactory,ModalFactory,CommonUtils, OrgService,EmpService) {
        //初始化参数
        var defaults = {};
        var orgid='0';
        $scope.beans = angular.extend({}, defaults);
        //查询数据
        $scope.pager = {
            fetch: function () {
                var param = angular.extend({},{id:orgid},{start: $scope.pager.start || 0, limit: $scope.pager.limit || 10});
                return CommonUtils.promise(function (defer) {
                    EmpService.queryByOrgId(param, function (data) {
                        $scope.beans = data;
                        defer.resolve(data);
                    });
                });
            }
        };

        $scope.query = function (id) {
            orgid=id;
            $scope.pager.query();
        }
        //回到上一个页面
        $scope.back = function () {
            $window.history.back();
        }

        $scope.del = function (id) {
            ModalFactory.remove($scope, function () {
                if (!id) {
                    var items = [];
                    angular.forEach($scope.items, function (v) {
                        items.push(v.id);
                    });
                    if (!items || items.length < 1) {
                        AlertFactory.error($scope, '请选择至少一条数据后再进行删除!', '错误操作!');
                        return;
                    }
                    id = items.join(',');
                }
                var result = EmpService.deleteByIds({ids: id});
                AlertFactory.handle($scope, result, initTree);
            });
        };
        //初始化ztree树（必须在文档加载后执行）
        var setting = {
            view: {showIcon: false},
            data: {
                simpleData: {enable: true}
            },
            callback: {
                onClick: function (event, treeId, treeNode) {
                    $scope.query(treeNode.id);
                }
            }
        };
        var initTree = function () {
            var param = angular.extend({},{start: $scope.pager.start || 0, limit: $scope.pager.limit || 10});
            var result = OrgService.query(param);
            AlertFactory.handle($scope, result, function (data) {
                data = data.data || [];
                //初始化菜单
                $.fn.zTree.init($("#treeDemo"), setting, [
                    {id: 0, name: '机构组织', children: data || [], open: true}
                ]);
            });
        }

        //执行方法
        initTree();


        //初始化菜单
//        var data = [
//            {"id": 1, "name": "商业规划院", "open": true,
//                "children": [
//                    {"id": 2, "name": "总经办"},
//                    {"id": 3, "name": "综合管理中心"},
//                    {"id": 4, "name": "北区运营中心"}
//                ]
//            },
//            {"id": 01, "name": "万达学院", "open": true,
//                "children": [
//                    {"id": 02, "name": "商管总部",
//                        "children": [
//                            {"id": 021, "name": "总经办"},
//                            {"id": 022, "name": "综合管理中心"},
//                            {"id": 023, "name": "北区运营中心"},
//                            {"id": 024, "name": "中区运营中心"},
//                            {"id": 025, "name": "南区运营中心"},
//                            {"id": 026, "name": "工程物业中心"},
//                            {"id": 027, "name": "安全监督部"},
//                            {"id": 028, "name": "人力资源行政部"},
//                            {"id": 029, "name": "信息管理部"},
//                            {"id": 0210, "name": "财务部"},
//                            {"id": 0211, "name": "成本部"}
//                        ]},
//                    {"id": 03, "name": "保险推广专员"},
//                    {"id": 06, "name": "商管总部隐患督办专员"}
//                ]
//            },
//            {"id": 01, "name": "酒店建设公司", "open": true,
//                "children": [
//                    {"id": 02, "name": "商管总部",
//                        "children": [
//                            {"id": 021, "name": "总经办"},
//                            {"id": 022, "name": "综合管理中心"},
//                            {"id": 023, "name": "北区运营中心"},
//                            {"id": 027, "name": "安全监督部"},
//                            {"id": 028, "name": "人力资源行政部"},
//                            {"id": 0210, "name": "财务部"}
//                        ]}
//                ]
//            },
//            {"id": 01, "name": "酒店管理公司", "open": true,
//                "children": [
//                    {"id": 02, "name": "商管总部",
//                        "children": [
//                            {"id": 021, "name": "总经办"},
//                            {"id": 022, "name": "综合管理中心"},
//                            {"id": 023, "name": "北区运营中心"},
//                            {"id": 024, "name": "中区运营中心"},
//                            {"id": 025, "name": "南区运营中心"},
//                            {"id": 027, "name": "安全监督部"},
//                            {"id": 028, "name": "人力资源行政部"},
//                            {"id": 029, "name": "信息管理部"},
//                            {"id": 0210, "name": "财务部"},
//                            {"id": 0211, "name": "成本部"}
//                        ]}
//                ]
//            },
//            {"id": 01, "name": "商管公司", "open": true,
//                "children": [
//                    {"id": 02, "name": "商管总部",
//                        "children": [
//                            {"id": 021, "name": "总经办"},
//                            {"id": 022, "name": "综合管理中心"},
//                            {"id": 023, "name": "北区运营中心"},
//                            {"id": 024, "name": "中区运营中心"},
//                            {"id": 025, "name": "南区运营中心"},
//                            {"id": 026, "name": "工程物业中心"},
//                            {"id": 027, "name": "安全监督部"},
//                            {"id": 028, "name": "人力资源行政部"},
//                            {"id": 029, "name": "信息管理部"},
//                            {"id": 0210, "name": "财务部"},
//                            {"id": 0211, "name": "成本部"}
//                        ]},
//                    {"id": 23, "name": "保险推广专员"},
//                    {"id": 24, "name": "总部资产变动提醒接收人"},
//                    {"id": 25, "name": "系统管理员"},
//                    {"id": 26, "name": "商管总部隐患督办专员"}
//                ]
//            }
//        ];
    });
})(window, angular, jQuery);