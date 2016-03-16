package eccrm.base.auth.service;

import eccrm.base.auth.domain.AccreditData;
import eccrm.base.auth.vo.AccreditDataVo;

import java.util.List;
import java.util.Set;

/**
 * @author Michael
 */
public interface AccreditDataService {

    /**
     * 给指定岗位授权新的数据资源
     * 首先删除原有被授权的资源，然后重新保存新的资源集合
     *
     * @param accreditData 数据资源对象
     */
    void accreditToPosition(AccreditData accreditData);

    /**
     * 查询指定岗位被授权的所有数据资源的id集合
     *
     * @param positionIds
     * @return 数据资源的id集合
     */
    List<String> queryResourceCodeByPosition(String[] positionIds);

    /**
     * 查询授权给指定用户的所有数据权限的编号
     * 如果员工ID为空，则默认使用当前登录用户的id
     *
     * @return 数据权限的编号
     */
    Set<String> queryPersonalAllDataResourceCode(String userId);

    /**
     * 查询授权给指定用户的所有数据权限
     *
     * @param userId 用户id
     * @return 数据权限集合
     */
    List<AccreditData> queryPersonalAllDataResource(String userId);

    /**
     * <p>查询某个岗位授予给某个数据资源的明细</p>
     * <p>任意参数为空时，都将返回空</p>
     * <p>如果对于指定的岗位没有授予这个资源的权限，则也将返回null</p>
     *
     * @param deptId       岗位id
     * @param resourceCode 资源编号
     */
    AccreditDataVo queryAccreditResource(String deptId, String resourceCode);

}
