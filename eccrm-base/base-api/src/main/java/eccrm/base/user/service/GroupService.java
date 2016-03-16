package eccrm.base.user.service;

import com.ycrl.core.pager.PageVo;
import eccrm.base.user.bo.GroupBo;
import eccrm.base.user.domain.Group;
import eccrm.base.user.vo.GroupVo;
import eccrm.core.VoWrapper;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-03
 */
public interface GroupService extends VoWrapper<Group, GroupVo> {

    String save(Group group);

    void update(Group group);

    PageVo query(GroupBo bo);

    GroupVo findById(String id);

    void deleteByIds(String... ids);

    /**
     * 查询所有状态为启用的组，并组装成树形
     */
    List<GroupVo> validTree();

    /**
     * 查询所有状态为启用的组，并可设置额外的查询条件
     * 用于选择有效的用户组
     *
     * @param bo 额外的查询条件
     */
    List<GroupVo> queryValid(GroupBo bo);

    /**
     * 检查指定组下指定名称的组是否存在
     *
     * @param groupId 组的id（可为空）
     * @param name    组的名称
     */
    boolean hasName(String groupId, String name);

    /**
     * 查询所有的数据，并组装成树
     * 用于管理员的维护界面
     *
     * @return 树形结构
     */
    List<GroupVo> tree();
}
