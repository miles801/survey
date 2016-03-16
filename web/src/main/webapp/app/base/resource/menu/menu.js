/**
 * Created by miles on 13-11-25.
 */
(function (angular) {
    var app = angular.module("eccrm.base.menu", [
        'ngResource',
        'eccrm.base.param'// 参数
    ]);
    var baseUrl = "base/menu";
    app.service('MenuService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL(baseUrl) + '/:method', {}, {
            save: {method: 'POST', params: {method: 'save', attachmentIds: '@attachmentIds'}, isArray: false},//保存

            update: {method: 'PUT', params: {method: 'update', attachmentIds: '@attachmentIds'}, isArray: false},//更新

            // 批量删除/注销
            //返回：{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 根据ID查询对象的信息
            // 返回{data:{}}
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},//根据id查询对象

            // 查询当前id下的所有子节点（包括自己）
            // 返回分页对象：{data:{total,data:[{}]}}
            allChildren: {method: 'GET', params: {method: 'children', id: '@id', start: '@start', limit: '@limit'}, isArray: false},

            // 维护时用：查询所有的菜单，并组装成树
            // 返回：{data : [{id,name,children:[]}]}
            tree: {method: 'POST', params: {method: 'tree'}, isArray: false},

            // 判断指定名称在指定的节点下是否重复,name需要使用encodeURI()进行两次编码
            hasName: {method: 'GET', params: {method: 'hasName', id: '@id', name: '@name'}, isArray: false},

            // 判断指定编号是否重复,,code需要使用encodeURI()进行两次编码
            hasCode: {method: 'GET', params: {method: 'hasCode', code: '@code'}, isArray: false},

            // 选择上级菜单时用：查询所有的菜单，并组装成树
            // 可选参数 id
            // 返回：{data:[]}
            queryOther: {method: 'GET', params: {method: 'queryOther', id: '@id'}, isArray: false},


            // 查询所有有效的菜单，并组装成树
            // 导航菜单用
            // 返回：{data:[]}
            // 数据模型{id,name,parentId}
            queryValidMenu: {method: 'GET', params: {method: 'queryValidMenu'}, isArray: false},

            // 查询所有有效的功能菜单（包括界面元素等），并组装成树
            // 授权用
            // 返回：{data:[]}
            // 数据模型{id,name,parentId}
            queryValid: {method: 'GET', params: {method: 'queryValid'}, isArray: false}
        })
    });
    app.service('MenuUrl', function ($window, CommonUtils) {
        var to = function (url) {
            $window.location.href = CommonUtils.contextPathURL(url);
        };
        return {
            toList: function () {
                to(baseUrl);
            },
            toAdd: function () {
                to(baseUrl + '/add');
            },
            toModify: function (id) {
                to(baseUrl + '/modify?id=' + id);
            },
            toView: function (id) {
                to(baseUrl + '/detail?id=' + id);
            }
        };
    });
    app.service('MenuConstant', function (CommonUtils, Parameter) {
        var status;
        var types;
        return {
            types: function (callback) {
                if (types === undefined) {
                    var result = Parameter.systemItems('SP_MENU_TYPE', function (data) {
                        types = data.data || [];
                        callback(types);
                    });
                    CommonUtils.loading(result, '加载数据字典:菜单类型');
                } else {
                    callback(types);
                }
            },
            status: function (callback) {
                if (status === undefined) {
                    var result = Parameter.systemItems('SP_COMMON_STATE', function (data) {
                        status = data.data || [];
                        callback(status);
                    });
                    CommonUtils.loading(result, '加载数据字典:菜单状态');
                } else {
                    callback(status);
                }
            },
            defaults: {
                show: true,
                type: "BP_CDGN",//功能操作
                order: 0,
                status: "ACTIVE",//正常状态
                authorization: true,
                creatorId: CommonUtils.loginContext().id,
                creatorName: CommonUtils.loginContext().username,
                createdDatetime: new Date().getTime()
            }
        }
    });

    //格式化菜单类型（使用文字代替具体的数字）
    app.service("MenuTree", function (MenuService, $q) {
        return {
            init: function () {
                var defer = $q.defer();
                MenuService.tree(function (data) {
                    defer.resolve(data);
                });
                return defer.promise;
            }
        };
    });
})(angular);
