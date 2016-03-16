/**
 * Created by chenl on 2014-10-14 15:56:01.
 */
(function (angular, $) {
    var app = angular.module("base.position.emp", [
        'ngResource',
        'eccrm.angular'
    ]);
    app.service('PositionEmpService', function ($resource, CommonUtils) {
        return $resource(CommonUtils.contextPathURL('/wanda/positionEmp/:method'), {}, {
            //保存
            save: {method: 'POST', params: {method: 'save'}, isArray: false},

            // 批量保存
            // 必须参数：
            //  orgId:组织机构id
            //  positionId：岗位id
            //  empIds：员工id，多个值使用逗号分隔
            batchSave: {method: 'POST', params: {method: 'batchSave'}, isArray: false},
            // 批量保存
            // 必须参数：
            //  orgId:组织机构id
            //  positionId：岗位id
            //  empIds：员工id，多个值使用逗号分隔
            batchSaveTwo: {method: 'POST', params: {method: 'batchSaveTwo'}, isArray: false},

            //保存
            saves: {method: 'POST', params: {method: 'saves'}, isArray: false},

            //更新
            update: {method: 'POST', params: {method: 'update'}, isArray: false},

            // 根据id查询机构员工视图信息
            get: {method: 'GET', params: {id: '@id'}, isArray: false},

            // 根据员工id查询对应的岗位和机构信息
            queryByEmpId: {method: 'GET', params: {method: 'queryByEmpId', empId: '@empId'}, isArray: false},

            queryByOrgId: {method: 'GET', params: {method: 'queryByOrgId', id: '@id'}, isArray: false},

            // 分页查询，返回{total:,data:[{},{}]}
            query: {method: 'POST', params: {method: 'query', limit: '@limit', start: '@start'}, isArray: false},

            // 分页查询员工信息
            // 必须参数：
            //  orgId：组织机构id
            //  positionId:岗位id
            // 返回：{success:true,data:{total:10,data:[]}}
            // 数据模型：Employee对象
            queryEmp: {
                method: 'GET',
                params: {
                    method: 'queryEmp',
                    orgId: '@orgId',
                    positionId: '@positionId',
                    limit: '@limit',
                    start: '@start'
                },
                isArray: false
            },

            // 不建议使用！！！！！
            queryAll: {method: 'POST', params: {method: 'queryAll', limit: '@limit', start: '@start'}, isArray: false},


            //根据id字符串（使用逗号分隔多个值），删除对应的机构员工视图，成功则返回{success:true}
            deleteByIds: {method: 'DELETE', params: {method: 'delete', ids: '@ids'}, isArray: false},

            // 删除关联关系
            // 必须指定哪个机构下的哪个岗位的哪些员工
            // 必须参数：
            // orgId：机构id
            // positionId：岗位id
            // empIds:员工id，多个值使用逗号分隔
            deleteRelations: {
                method: 'DELETE', params: {
                    method: 'delete',
                    orgId: '@orgId', positionId: '@positionId', empIds: '@empIds'
                }, isArray: false
            },

            deleteByRoleId: {method: 'POST', params: {method: 'deleteByRoleId'}, isArray: false}
        })
    });
})(angular, jQuery);
