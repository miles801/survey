package eccrm.base.auth.service;

import eccrm.base.menu.vo.MenuVo;

import java.util.List;

/**
 * 授权资源接口
 *
 * @author Michael
 */
public interface AccreditMenuService {

    /**
     * 给部门（岗位）授权菜单
     *
     * @param deptId  部门ID
     * @param menuIds 菜单ID数组
     */
    void saveByDept(String deptId, String[] menuIds);


    /**
     * 根据部门/岗位查询对应的授权菜单的集合
     * 仅仅查询状态为启用且允许显示或者不需要授权的菜单
     *
     * @param deptIds 岗位ID列表
     */
    List<String> queryAccreditMenuIds(String[] deptIds);

    /**
     * 根据部门/岗位列表查询对应的授权菜单集合
     * 仅仅查询状态为启用且允许显示或者不需要授权的菜单
     *
     * @param deptIds 岗位ID列表
     * @return 授权菜单
     */
    List<MenuVo> queryAccreditMenus(String[] deptIds);

    /**
     * 查询员工所具有的所有权限菜单
     * 仅仅查询状态为启用且允许显示或者不需要授权的菜单
     *
     * @param empId 员工ID
     */
    List<MenuVo> queryPersonalAccreditMenus(String empId);

    /**
     * 查询被授权给个人的子系统以及九宫格
     * 仅仅查询状态为启用且允许显示或者不需要授权的菜单
     *
     * @param userId 当前登录用户ID
     */
    List<MenuVo> queryPersonalAccreditMenusRoot(String userId);

    /**
     * 查询被授权给个人的指定子系统下的所有有效导航功能
     * 仅仅查询状态为启用且允许显示或者不需要授权的菜单
     *
     * @param userId   当前登录用户ID
     * @param parentId 子系统ID
     */
    List<MenuVo> queryPersonalAccreditMenus(String userId, String parentId);
}
