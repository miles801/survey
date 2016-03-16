/**
 * Created by miles on 13-11-25.
 */
(function (angular) {
    var app = angular.module("eccrm.base.parameter.type", [
        'ngRoute',
        'eccrm.base.param',
        'eccrm.base.parameter.modal',
        'eccrm.angular',
        'eccrm.angularstrap'
    ]);
    var contextPath = $('#contextPath').val();
    app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/system', {
                templateUrl: contextPath + 'app/base/parameter/template/tab-parameterType-system.html',
                controller: 'SystemTypeCtrl'
            })
            .when('/business', {
                templateUrl: contextPath + 'app/base/parameter/template/tab-parameterType-business.html',
                controller: 'BusinessTypeCtrl'
            })
            .otherwise({redirectTo: '/system'});
    }]);

    app.controller('ParameterTypeCtrl', function ($scope) {
        $scope.active = 0;
        $scope.routeOptions = [
            {url: 'system', name: '基础参数类型', active: true},
            {url: 'business', name: '业务参数类型'}
        ];
    });
    var controller = function ($scope, AlertFactory, ModalFactory, ParameterType, ParameterTypeModal) {
        var ztreeObj;// ztree对象
        $scope.query = function () {
            if (!$scope.id) return;
            var result = ParameterType.children({id: $scope.id});
            AlertFactory.handle($scope, result, function (data) {
                $scope.parameters = data;
            });
        };
        var initParams = function (treeNode) {
            $scope.id = treeNode.id;
            $scope.currentNodeId = treeNode.id;
            $scope.currentNodeName = treeNode.name;
        };
        var setting = {
            view: {showIcon: false},
            callback: {
                onClick: function (event, treeId, treeNode) {
                    // 展开节点
                    if (treeNode.isParent === true && treeNode.open === false) {
                        ztreeObj.expandNode(treeNode, true, true, true);
                    }
                    // 防止重复点击
                    if (!treeNode.id || $scope.id == treeNode.id) return;

                    // 初始化参数
                    $scope.$apply(function () {
                        initParams(treeNode);
                    });
                    // 查询子节点
                    $scope.query();
                    event.preventDefault();
                }
            }
        };
        var initTree = function () {
            var result = ParameterType.tree();
            AlertFactory.handle($scope, result, function (data) {
                data = data.data || [];
                data.length > 0 ? initParams(data[0]) : null;
                $scope.query();//默认加载第一个菜单的数据到列表页面
                //初始化菜单
                ztreeObj = $.fn.zTree.init($("#treeDemo"), setting, [
                    {name: '参数类型', children: data || [], open: true}
                ]);
            });
        };

        $scope.add = function () {
            var options = {scope: $scope};
            if ($scope.id && $scope.currentNodeName) {
                options.parentId = $scope.currentNodeId;
                options.parentName = $scope.currentNodeName;
            }
            ParameterTypeModal.add(options, initTree);
        };
        $scope.view = function (id) {
            ParameterTypeModal.view({scope: $scope, id: id});
        };
        $scope.modify = function (id) {
            ParameterTypeModal.modify({scope: $scope, id: id}, initTree);
        };

        $scope.cancel = function () {
            ModalFactory.confirm({scope: $scope, content: '<b>注销</b>后该参数类型将不可用,请确认!'}, function () {
                var ids = [];
                angular.forEach($scope.items || [], function (o) {
                    ids.push(o.id);
                });
                ids.length > 0 ? ParameterType.deleteByIds({ids: ids}, initTree) : null;
                $scope.items = []; // 清空已选择选项，防止多次重复选择
            });
        };
        initTree();
    };
    app.controller('SystemTypeCtrl', ['$scope', 'AlertFactory', 'ModalFactory', 'SysParamType', 'SysParamTypeModal', 'CommonUtils', controller]);
    app.controller('BusinessTypeCtrl', ['$scope', 'AlertFactory', 'ModalFactory', 'BusinessParamType', 'BusinessParamTypeModal', 'CommonUtils', controller]);
})
(angular);
