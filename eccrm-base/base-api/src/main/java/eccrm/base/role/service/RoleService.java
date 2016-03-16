package eccrm.base.role.service;

import eccrm.base.role.bo.RoleBo;
import eccrm.base.role.domain.Role;
import eccrm.base.role.vo.RoleVo;
import eccrm.core.VoWrapper;
import com.ycrl.core.pager.PageVo;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-03-26
 */
public interface RoleService extends VoWrapper<Role, RoleVo> {

    String ROLE_STATE = "SP_ROLE_STATE";
    String save(Role role);

    void update(Role role);

    PageVo query(RoleBo bo);

    RoleVo findById(String id);

    void deleteByIds(String... ids);

    /**
     * 查询所有有效的角色（可以直接额外的查询条件）
     * 可选参数：
     * groupId:如果指定了该值，则不会查询该组下的角色
     * userId：如果指定了该值，则不会查询该用户下的角色
     *
     * @param bo 高级查询对象
     * @return 集合
     */
    List<RoleVo> queryValid(RoleBo bo);
}
