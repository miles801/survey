/**
 * Created by miles on 13-11-25.
 */
(function (angular) {
    var app = angular.module("eccrm.base.resource", [
        'ngResource',
        'eccrm.base.param',
        'eccrm.angular'
    ]);
    app.service('ResourceService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL("base/resource/:method"), {}, {
            save: {method: 'POST', params: {method: 'save'}, isArray: false},//保存

            update: {method: 'PUT', params: {method: 'update'}, isArray: false},//更新

            // 批量删除/注销
            //返回：{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 根据ID查询对象的信息
            // 返回{data:{}}
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},//根据id查询对象

            // 查询当前id下的所有子节点（包括自己）
            // 返回分页对象：{data:{total,data:[{}]}}
            allChildren: {
                method: 'GET',
                params: {method: 'children', id: '@id', start: '@start', limit: '@limit'},
                isArray: false
            },

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
            queryValidResource: {method: 'GET', params: {method: 'queryValidResource'}, isArray: false},

            // 查询所有有效的【页面元素】资源
            // 返回{data:[]}
            queryAllValidElement: {method: 'GET', params: {method: 'queryAllValidElement'}, isArray: false},

            // 查询所有有效的【数据】资源
            // 返回{data:[]}
            queryAllValidData: {method: 'GET', params: {method: 'queryAllValidData'}, isArray: false},

            // 查询所有有效的功能菜单（包括界面元素等），并组装成树
            // 授权用
            // 返回：{data:[]}
            queryValid: {method: 'GET', params: {method: 'queryValid'}, isArray: false}
        })
    });
    app.service('ResourceParam', function (CommonUtils, ParameterLoader) {
        return {
            modules: function (callback) {
                ParameterLoader.loadSysParam('SP_MODULE', callback, '加载数据字典：模块...');
            },
            types: function (callback) {
                ParameterLoader.loadSysParam('SP_RESOURCE_TYPE', callback, '加载数据字典：资源类型...');
            },
            status: function (callback) {
                ParameterLoader.loadSysParam('SP_COMMON_STATE', callback, '加载数据字典：资源状态...');
            },
            defaults: {
                show: true,
                authorization: true,
                sequenceNo: 1,
                type: "3",//功能操作
                status: "ACTIVE",//正常状态
                creatorId: CommonUtils.loginContext().id,
                creatorName: CommonUtils.loginContext().username,
                createdDatetime: new Date().getTime()
            }
        }
    });

})(angular);
