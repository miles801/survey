package eccrm.base.user.service.impl;

import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.user.dao.UserGroupDao;
import eccrm.base.user.domain.Group;
import eccrm.base.user.domain.User;
import eccrm.base.user.domain.UserGroup;
import eccrm.base.user.service.UserGroupService;
import eccrm.base.user.service.UserService;
import eccrm.base.user.vo.UserGroupVo;
import eccrm.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-03
 */
@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {
    @Resource
    private UserGroupDao dao;

    private String save(String userId, String groupId, Integer sequenceNo) {
        User user = new User();
        user.setId(userId);
        Group group = new Group();
        group.setId(groupId);
        UserGroup ug = new UserGroup();
        ug.setUser(user);
        ug.setGroup(group);
        ug.setSequenceNo(sequenceNo);
        return dao.save(ug);
    }

    @Override
    public void saveByUser(String userId, String... groupIds) {
        if (StringUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("根据用户ID保存与组的关系时,用户id不能为空!");
        }
        if (groupIds == null || groupIds.length < 1) {
            throw new IllegalArgumentException("根据用户ID保存与组的关系时,组ID列表不能为空!");
        }
        for (int i = 0; i < groupIds.length; i++) {
            save(userId, groupIds[i], i);
        }
    }

    @Override
    public void saveByGroup(String groupId, String... userIds) {
        if (StringUtils.isEmpty(groupId)) {
            throw new IllegalArgumentException("根据组ID保存与用户的关系时,组id不能为空!");
        }
        if (userIds == null || userIds.length < 1) {
            throw new IllegalArgumentException("根据组ID保存与用户的关系时,用户ID列表不能为空!");
        }
        for (int i = 0; i < userIds.length; i++) {
            save(userIds[i], groupId, i);
        }
    }

    @Override
    public void deleteByGroupId(String groupId) {
        dao.deleteByGroupId(groupId);
    }

    @Override
    public void deleteByUserId(String userId) {
        dao.deleteByUserId(userId);
    }

    @Override
    public List<UserGroupVo> queryUser(String groupId) {
        List<UserGroup> userGroups = dao.queryUser(groupId);
        return wrapGroupVos(userGroups);
    }

    @Override
    public List<UserGroupVo> queryGroup(String userId) {
        List<UserGroup> userGroups = dao.queryGroup(userId);
        return wrapGroupVos(userGroups);
    }

    private List<UserGroupVo> wrapGroupVos(List<UserGroup> userGroups) {
        if (userGroups == null) return null;
        List<UserGroupVo> vos = new ArrayList<UserGroupVo>();
        for (UserGroup userGroup : userGroups) {
            vos.add(wrapGroupVo(userGroup));
        }
        return vos;
    }

    private UserGroupVo wrapGroupVo(UserGroup userGroup) {
        if (userGroup == null) {
            return null;
        }
        UserGroupVo vo = new UserGroupVo();
        vo.setId(userGroup.getId());
        // 设置用户组信息
        Group group = userGroup.getGroup();
        vo.setGroupId(group.getId());
        vo.setGroupName(group.getName());

        // 设置用户信息
        User user = userGroup.getUser();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setSequenceNo(userGroup.getSequenceNo());
        vo.setUserCode(user.getCode());
        Date startDate = user.getStartDate();
        Date endDate = user.getEndDate();
        Date now = new Date();
        vo.setUserStartDate(startDate);
        vo.setUserEndDate(endDate);
        vo.setUserState("正常");
        if (startDate != null && startDate.after(now)) {
            vo.setUserState("未启用");
        }
        if (endDate != null && endDate.before(now)) {
            vo.setUserState("已过期");
        }
        vo.setUserStatusName(ParameterContainer.getInstance().getSystemName(UserService.SP_USER_STATE, user.getStatus()));
        return vo;
    }
}
