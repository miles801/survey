package eccrm.base.user.dao;

import eccrm.base.user.domain.UserGroup;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-03
 */
public interface UserGroupDao {

    String save(UserGroup userGroup);

    void deleteByGroupId(String groupId);

    void deleteByUserId(String userId);

    List<UserGroup> queryUser(String groupId);

    List<UserGroup> queryGroup(String userId);

    /**
     * 根据用户ID查询所有的用户组
     *
     * @param userId 用户id
     * @return 用户组id
     */
    List<String> queryGroupIds(String userId);
}
