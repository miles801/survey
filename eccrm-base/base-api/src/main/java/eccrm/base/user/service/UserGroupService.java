package eccrm.base.user.service;

import eccrm.base.user.vo.UserGroupVo;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-03
 */
public interface UserGroupService {

    /**
     * 根据用户id、组id数组保存关联关系
     * 默认按照组id的先后顺序排序
     *
     * @param userId   用户id
     * @param groupIds 组id数组
     */
    void saveByUser(String userId, String[] groupIds);

    /**
     * 根据组id、用户id数组保存关联关系
     * 默认按照用户id的先后顺序排序
     *
     * @param groupId 组id
     * @param userIds 用户id数组
     */
    void saveByGroup(String groupId, String[] userIds);

    /**
     * 删除指定组与所有用户的关系
     *
     * @param groupId 组id
     */
    void deleteByGroupId(String groupId);

    /**
     * 删除指定用户的所有组的关系
     *
     * @param userId 用户id
     */
    void deleteByUserId(String userId);

    /**
     * 查询用户组下的所有用户集合
     *
     * @param groupId 用户组id
     * @return 用户组集合
     */
    List<UserGroupVo> queryUser(String groupId);

    /**
     * 查询指定用户下的所有组的集合
     *
     * @param userId 用户id
     * @return 用户组的集合
     */
    List<UserGroupVo> queryGroup(String userId);

}
