package eccrm.base.auth.dao;

import eccrm.base.auth.domain.AccreditData;
import eccrm.base.log.OperateType;
import eccrm.base.log.annotations.LogInfo;

import java.util.List;

/**
 * @author Michael
 */
public interface AccreditDataDao {

    @LogInfo(type = OperateType.ADD, describe = "数据授权-新增")
    String save(AccreditData accreditData);

    /**
     * 删除指定岗位被授权的所有数据资源
     *
     * @param deptId         岗位id
     * @param dataResourceId 资源数据id
     */
    @LogInfo(type = OperateType.DELETE, describe = "数据授权-删除岗位权限")
    void deleteAccreditByDept(String deptId, String dataResourceId);

    /**
     * 查询指定岗位被授权的所有数据资源的编号集合
     *
     * @param deptIds 岗位id列表
     * @return 数据资源编号集合
     */
    List<String> queryResourceCodeByDept(String[] deptIds);

    /**
     * 查询指定的人被授权的所有数据权限的编号
     *
     * @param userId 员工id
     * @return 数据权限资源的编号集合
     */
    List<String> queryResourceCodeByPerson(String userId);

    /**
     * <p>查询某个岗位授予给某个数据资源的明细</p>
     * <p>任意参数为空时，都将返回空</p>
     * <p>如果对于指定的岗位没有授予这个资源的权限，则也将返回null</p>
     *
     * @param deptId       岗位id
     * @param resourceCode 资源编号
     */
    AccreditData queryAccreditResource(String deptId, String resourceCode);

    /**
     * <p>查询指定员工所具有的所有的数据权限集合（状态为有效）</p>
     *
     * @param userId 员工id
     */
    List<AccreditData> queryPersonalAllDataResource(String userId);
}
