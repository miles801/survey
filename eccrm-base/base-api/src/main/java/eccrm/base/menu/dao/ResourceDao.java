package eccrm.base.menu.dao;

import eccrm.base.log.OperateType;
import eccrm.base.log.annotations.LogInfo;
import eccrm.base.menu.bo.ResourceBo;
import eccrm.base.menu.domain.Resource;
import eccrm.utils.tree.PathTreeDao;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;
import java.util.Set;

/**
 * @author Michael
 */
public interface ResourceDao extends PathTreeDao<Resource, String> {
    List<Resource> query(ResourceBo bo);

    /**
     * 保存菜单，成功后返回主键
     *
     * @return id
     */
    @LogInfo(type = OperateType.ADD, describe = "资源-新增")
    String save(Resource resource);

    /**
     * 更新菜单
     */
    @LogInfo(type = OperateType.MODIFY, describe = "资源-修改")
    void update(Resource resource);


    @LogInfo(type = OperateType.DELETE, describe = "资源-删除")
    void delete(Resource resource);

    long getTotal(ResourceBo bo);

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
    List<Resource> queryOther(String id);

    /**
     * 查询所有有效的菜单（只包含子系统和功能操作）
     */
    List<Resource> queryValidResource();


    /**
     * <p>查询指定id列表的资源</p>
     *
     * @param ids 资源的id集合
     */
    List<Resource> queryByIds(Set<String> ids);

    /**
     * <p>查询所有有效，且需要授权的数据资源的编号的集合</p>
     *
     * @return 数据资源的编号的集合
     */
    List<String> queryAllLimitDataResource();

    /**
     * 查询有效的操作资源的id集合
     *
     * @return 离线查询对象
     */
    DetachedCriteria queryValidFuncResource();

    /**
     * 查询有效的数据资源的id集合
     *
     * @return 离线查询对象
     */
    DetachedCriteria queryValidDataResource();
}
