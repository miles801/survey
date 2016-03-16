package eccrm.base.auth.dao;

import eccrm.base.auth.domain.AccreditFunc;
import eccrm.base.log.OperateType;
import eccrm.base.log.annotations.LogInfo;

import java.util.List;

/**
 * @author Michael
 */
public interface AccreditFuncDao {

    @LogInfo(type = OperateType.ADD, describe = "功能授权-新增")
    String save(AccreditFunc accreditFunc);


    @LogInfo(type = OperateType.DELETE, describe = "功能授权-删除岗位权限")
    void deleteByDeptId(String deptId);

    List<String> queryResourceCodeByDept(String[] deptIds);

    /**
     * 查询指定员工被授予的全部操作资源（状态为有效）
     *
     * @return 返回操作资源的编号
     */
    List<String> queryEmpResourceCode(String empId);
}
