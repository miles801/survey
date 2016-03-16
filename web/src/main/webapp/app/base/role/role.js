/**
 * Created by chenl on 13-12-5.
 */
(function (angular) {
    var app = angular.module("eccrm.base.role", [
        'ngResource',
        'eccrm.base.param'
    ]);
    app.service('RoleService', function ($resource) {
        var query = {method: 'query', start: '@start', limit: '@limit', orderBy: '@orderBy', reverse: '@reverse'};
        return $resource('base/role/:method', {}, {
            save: {method: 'POST', params: {method: 'save'}, isArray: false},
            update: {method: 'PUT', params: {method: 'update'}, isArray: false},

            // 根据ID删除对象
            // 必须参数：ids，多个id使用逗号进行分隔
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},//更新

            // 查询具体的角色信息
            // 必须参数：id
            // 返回数据：{success:true,data:{}}
            get: {method: 'GET', params: {method: 'get', id: '@id'}, isArray: false},

            // 查询所有有效的角色
            // 必须参数：
            // 可选参数：name,code
            // 返回数据：{success:true,data:[]}
            queryValid: {method: 'GET', params: {method: 'queryValid'}, isArray: false},

            // 高级查询对象，支持传递一个RoleBo对象进行模糊匹配查询
            // 必须参数：无
            // 可选参数：
            // 返回数据：{success:true,data:{ total:总数据,data:[] }}
            query: {method: 'POST', params: query, isArray: false}//查询出所有的菜单,允许使用json的方式传递参数
        })
    });

    // 组与角色的关系
    app.service('RoleGroupService', function ($resource) {
        return $resource('base/role/groups/:method', {}, {
            // 保存角色用用户组的关系
            // 数据{ groupId:'',roleIds:''}
            // 多个角色id使用逗号分隔
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 查询指定用户组下的所有角色
            // 必须参数：groupId
            // 返回数据:{success:true,data:[]}
            query: {method: 'GET', params: {method: 'query', groupId: '@groupId'}, isArray: false},

            // 删除指定id的关系
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false}
        });
    });


    // 用户与角色的关系
    app.service('RoleUserService', function ($resource) {
        return $resource('base/role/users/:method', {}, {
            save: {method: 'POST', params: {method: 'save'}, isArray: false},//保存

            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},//更新

            // 查询指定用户的所有角色
            // 必须参数：userId
            // 返回数据:{success:true,data:[]}
            query: {method: 'GET', params: {method: 'query', userId: '@userId'}, isArray: false}

        })
    });

    // 资源与角色的关系
    app.service('RoleResourceService', function ($resource) {
        return $resource('base/role/resources/:method', {}, {
            // 保存
            // 必须参数
            //      roleId：角色id
            //      resources:资源id数组
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 根据id删除关联关系
            // 必须参数：
            //      ids:使用逗号分隔的多个id组成的字符串
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},//更新

            // 查询指定用户的所有资源
            // 必须参数：
            //      userId：用户id
            // 返回数据:{success:true,data:[]}
            queryByUser: {method: 'GET', params: {method: 'query', userId: '@userId'}, isArray: false},

            // 查询指定角色下的所有资源
            // 必须参数：
            //      roleId：角色id
            // 返回数据:{success:true,data:[]}
            queryByRole: {method: 'GET', params: {method: 'query', userId: '@userId'}, isArray: false}

        })
    });


    app.service('RoleConstant', ['Parameter', function (Parameter) {
        return{
            status: function (callback) {
                Parameter.systemItems('SP_ROLE_STATE', function (data) {
                    callback(data.data || []);
                });
            }
        };
    }]);


})(angular);
