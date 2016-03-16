package eccrm.base.role.dao;

import eccrm.base.role.bo.RoleBo;
import eccrm.base.role.domain.Role;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-03-26
 */
public interface RoleDao {

    String save(Role role);

    void update(Role role);

    List<Role> query(RoleBo bo);

    Long getTotal(RoleBo bo);

    Role findById(String id);

    void delete(Role role);

    /**
     * 判断指定名称是否存在
     * 存在：true，不存在：false
     *
     * @param id   可选，如果不为空，表示查询跳过id为当前值的这条记录
     * @param name 名称
     */
    boolean hasName(String id, String name);

    /**
     * 判断指定编号是否存在
     * 存在：true，不存在：false
     *
     * @param id   可选，如果不为空，表示查询跳过id为当前值的这条记录
     * @param code 编号
     */
    boolean hasCode(String id, String code);

}
