package eccrm.base.menu.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.menu.bo.ResourceBo;
import eccrm.base.menu.domain.Resource;
import eccrm.base.menu.vo.ResourceVo;

import java.util.List;

/**
 * @author Michael
 */
public interface ResourceService {
    public String save(Resource resource);

    /**
     * 根据ID查询出对应的菜单
     * 如果id为空，则抛出异常
     */
    public ResourceVo findById(String resourceId);


    /**
     * 更新实体类
     * 如果没有传递实体类的id，则会抛出异常
     */
    public void update(Resource resource);

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
     * @param resourceBo 高级查询条件
     * @return 分页对象
     */
    PageVo query(ResourceBo resourceBo);


    /**
     * 根据条件查询符合条件的菜单并组装成树（用于管理员维护页面的左侧树行展示）
     * bo为空则表示查询全部
     *
     * @return 树形结构
     */
    List<ResourceVo> tree(ResourceBo bo);

    /**
     * 查询指定节点（包含子节点）以外的所有启用的资源，组装成树
     *
     * @param id 可为空
     * @return 树形结构
     */
    List<ResourceVo> queryOtherTree(String id);

    /**
     * 查询所有有效的资源，并组装成树
     *
     * @return 树形结构
     */
    List<ResourceVo> queryValid();

    /**
     * 查询指定的编号是否存在
     *
     * @return 存在true 不存在false
     */
    boolean hasCode(String code);


    /**
     * 查询所有需要进行验证的资源（菜单）的id
     *
     * @return id集合
     */
    List<String> queryPermissionResources();

    /**
     * 查询所有有效的【菜单】资源（用于在授权时使用）
     *
     * @return 树形集合
     */
    List<ResourceVo> queryAllValidMenu();

    /**
     * 查询所有有效的【页面元素】资源（用于在授权时使用）
     * 返回的结果中只包含id、name、所属模块、编号
     */
    List<ResourceVo> queryAllValidElement();

    /**
     * 查询所有有效的【数据】资源（用于在授权时使用）
     * 返回的结果中只包含id、name、所属模块、编号
     */
    List<ResourceVo> queryAllValidData();

    /**
     * 查询所有有效且要求进行授权的数据资源的编号
     * @return 数据资源编号
     */
    List<String> queryAllLimitDataResourceCode();

}
