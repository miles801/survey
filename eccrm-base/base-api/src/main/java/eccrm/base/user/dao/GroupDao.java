package eccrm.base.user.dao;

import eccrm.base.user.bo.GroupBo;
import eccrm.base.user.domain.Group;
import eccrm.utils.tree.PathTreeDao;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-03
 */
public interface GroupDao extends PathTreeDao<Group, String> {

    String save(Group group);

    void update(Group group);

    List<Group> query(GroupBo bo);

    long getTotal(GroupBo bo);

    Group findById(String id);

    void delete(Group group);

    boolean hasName(String groupId, String name);
}
