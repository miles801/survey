package eccrm.base.menu.dao;

import com.ycrl.utils.tree.PathTreeDao;
import eccrm.base.log.OperateType;
import eccrm.base.log.annotations.LogInfo;
import eccrm.base.menu.bo.MenuBo;
import eccrm.base.menu.domain.Menu;

import java.util.List;

/**
 * 菜单的数据库访问服务
 * 菜单等具有导航性实体对外提供的接口：
 * 有效：显示、不授权、需要授权且具有权限的、启用/正常状态的菜单
 * 查询根元素：仅查询有效的的菜单
 * 查询所有的非根元素：仅查询有效的菜单
 * 查询所有的元素（条件）：维护时用
 * 选择单个菜单：仅查询有效的菜单
 * 选择多个菜单：仅查询有效的菜单
 * Created by miles on 13-11-21.
 */
public interface MenuDao extends PathTreeDao<Menu, String> {

    List<Menu> query(MenuBo bo);

    /**
     * 保存菜单，成功后返回主键
     *
     * @return id
     */
    @LogInfo(type = OperateType.ADD, describe = "菜单-新增")
    String save(Menu menu);

    /**
     * 更新菜单
     */
    @LogInfo(type = OperateType.MODIFY, describe = "菜单-修改")
    void update(Menu menu);


    @LogInfo(type = OperateType.DELETE, describe = "菜单-删除")
    void delete(Menu menu);

    long getTotal(MenuBo bo);

    /**
     * 判断指定id下指定名称是否存在
     * 存在：true      不存在：false
     *
     * @param id   为空表示根
     * @param name 菜单名称
     */
    boolean hasName(String id, String name);

    /**
     * 判断编号是否存在
     * 存在：true 不存在：false
     */
    boolean hasCode(String code);


    /**
     * 查询除当前节点（包含子节点）以外的所有子节点
     *
     * @param id 可为空
     */
    List<Menu> queryOther(String id);

    /**
     * 查询所有有效的菜单（只包含子系统和功能操作）
     */
    List<Menu> queryValidMenu();

    /**
     * 批量更新当前节点及子节点的状态
     *
     * @param parentId 当前节点ID
     * @param status   制定的状态
     */
    @LogInfo(type = OperateType.MODIFY, describe = "菜单:批量更新状态")
    void batchUpdateChildrenStatus(String parentId, String status);

    /**
     * 删除当前节点及其所有子节点
     * 注意：慎用此方法！
     *
     * @param id 当前节点ID
     */
    @LogInfo(type = OperateType.DELETE, describe = "菜单:批量删除")
    void batchDelete(String id);
}
