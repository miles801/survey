package eccrm.base.auth.dao;

import eccrm.base.auth.bo.AccreditMenuBo;
import eccrm.base.auth.domain.AccreditMenu;
import eccrm.base.log.OperateType;
import eccrm.base.log.annotations.LogInfo;
import eccrm.base.menu.domain.Menu;

import java.util.List;

/**
 * @author Michael
 */
public interface AccreditMenuDao {

    @LogInfo(type = OperateType.ADD, describe = "菜单权限-新增")
    String save(AccreditMenu accreditMenu);

    List<AccreditMenu> query(AccreditMenuBo bo);

    long getTotal(AccreditMenuBo bo);

    /**
     * 删除指定部门/岗位下的所有菜单权限
     *
     * @param deptId 部门/岗位id
     */
    @LogInfo(type = OperateType.DELETE, describe = "菜单权限-删除岗位权限")
    void deleteByDeptId(String deptId);

    /**
     * 查询指定岗位所具有的权限菜单集合
     *
     * @param deptIds 岗位id数组
     * @return 菜单ID
     */
    List<String> queryMenuIds(String[] deptIds);

    List<Menu> queryMenus(String[] deptIds);

    /**
     * 查询指定员工所具有的所有权限菜单集合
     *
     * @param empId 员工ID
     */
    List<Menu> queryMenus(String empId);

    /**
     * 查询指定员工被授予的根菜单（子系统以及导航菜单）
     * 只查询有效且允许显示的已授权或不需要授权的菜单
     * 如果参数为空，则返回null
     *
     * @param userId 当前登录用户ID
     */
    List<Menu> queryAccreditMenuRoot(String userId);

    /**
     * 查询指定员工被授权的指定子系统下的所有导航菜单
     * 只查询有效且允许显示的已授权或不需要授权的菜单
     * 如果任意参数为空，则返回null
     *
     * @param userId       当前登录用户ID
     * @param systemMenuId 子系统ID
     */
    List<Menu> queryAccreditMenus(String userId, String systemMenuId);
}
