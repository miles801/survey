package eccrm.base.menu.service;


import com.ycrl.core.pager.PageVo;
import eccrm.base.menu.bo.MenuBo;
import eccrm.base.menu.domain.Menu;
import eccrm.base.menu.vo.MenuVo;

import java.util.List;

/**
 * Created by miles on 13-11-20.
 * 菜单的业务逻辑服务
 */
public interface MenuService {
    public String save(Menu menu);

    /**
     * 根据ID查询出对应的菜单
     * 如果id为空，则抛出异常
     */
    public MenuVo findById(String menuId);


    /**
     * 更新实体类
     * 如果没有传递实体类的id，则会抛出异常
     */
    public void update(Menu menu);

    /**
     * 根据id删除对应的菜单
     * 如果状态为未启用，则删除，同时会删除所有子菜单
     *
     * @param ids 用逗号分隔的id字符串
     */
    public void delete(String... ids);


    /**
     * 查询指定id的所有子节点（包括隔代节点和自身）
     */
    public PageVo queryAllChildren(String id);

    /**
     * 查询指定id的直接孩子节点中，是否具有同名的菜单
     * true:重名  false:不重名
     *
     * @param id   上级菜单id（可为空）
     * @param name 菜单名称（不可为空）
     * @return true/false
     */
    public boolean hasName(String id, String name);

    /**
     * 分页查询：高级查询接口
     *
     * @param menuBo 高级查询条件
     * @return 分页对象
     */
    PageVo query(MenuBo menuBo);


    /**
     * 根据条件查询符合条件的菜单并组装成树（用于管理员维护页面的左侧树行展示）
     * bo为空则表示查询全部
     *
     * @return 树形结构
     */
    List<MenuVo> tree(MenuBo bo);

    /**
     * 查询指定节点（包含子节点）以外的所有非注销的菜单，组装成树
     *
     * @param id 可为空
     * @return 树形结构
     */
    List<MenuVo> queryOtherTree(String id);

    /**
     * 查询所有有效的菜单，并组装成树
     * <p> 该方法只返回id、name和parentId属性</p>
     *
     * @return 树形结构
     */
    List<MenuVo> queryValid();

    /**
     * 查询指定的编号是否存在
     *
     * @return 存在true 不存在false
     */
    boolean hasCode(String code);

    /**
     * 查询所有有效的菜单（不包含界面元素、业务对象）
     * <p> 该方法只返回id、name和parent属性</p>
     *
     * @return 树形集合
     */
    List<MenuVo> queryValidMenu();

    /**
     * 查询所有需要进行验证的资源（菜单）的id
     *
     * @return id集合
     */
    List<String> queryPermissionMenus();
}
