package eccrm.base.user.service.impl;

import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.pager.PageVo;
import eccrm.base.common.enums.CommonStatus;
import eccrm.base.constant.ParameterConstant;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.user.bo.GroupBo;
import eccrm.base.user.dao.GroupDao;
import eccrm.base.user.domain.Group;
import eccrm.base.user.service.GroupService;
import eccrm.base.user.vo.GroupVo;
import eccrm.core.VoHelper;
import eccrm.utils.Argument;
import eccrm.utils.StringUtils;
import eccrm.utils.tree.PathTreeBuilder;
import eccrm.utils.tree.TreeFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-03
 */
@Service("groupService")
public class GroupServiceImpl implements GroupService {
    @Resource
    private GroupDao groupDao;
    private Logger logger = Logger.getLogger(GroupServiceImpl.class);

    @Override
    public String save(Group group) {
        if (StringUtils.isEmpty(group.getStatus())) {
            group.setStatus(CommonStatus.INACTIVE.getValue());
        }
        if (group.getParent() != null && StringUtils.isEmpty(group.getParent().getId())) {
            group.setParent(null);
        }
        String id = groupDao.save(group);
        PathTreeBuilder.buildAfterSave(groupDao, group);
        return id;
    }

    @Override
    public void update(Group group) {
        PathTreeBuilder.buildBeforeUpdate(groupDao, group);
        if (group.getParent() != null && StringUtils.isEmpty(group.getParent().getId())) {
            group.setParent(null);
        }
        groupDao.update(group);
    }

    @Override
    public PageVo query(GroupBo bo) {
        PageVo vo = new PageVo();
        Long total = groupDao.getTotal(bo);
        vo.setTotal(total);
        if (total == 0) return vo;
        List<Group> groups = groupDao.query(bo);
        vo.setData(VoHelper.wrapVos(groups, this));
        return vo;
    }

    @Override
    public GroupVo findById(String id) {
        return wrap(groupDao.findById(id));
    }


    @Override
    public List<GroupVo> validTree() {
        GroupBo bo = new GroupBo();
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        List<Group> groups = groupDao.query(bo);
        List<Group> tree = TreeFactory.buildTree(groups, Group.class);
        return VoHelper.wrapVos(tree, this);
    }

    @Override
    public void deleteByIds(String... ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            Group group = groupDao.findById(id);
            Argument.entityNotFound(group, id);
            String status = group.getStatus();
            if (CommonStatus.INACTIVE.getValue().equals(status)) {
                List<Group> children = groupDao.queryChildren(id);
                for (Group child : children) {
                    groupDao.delete(child);
                }
                groupDao.delete(group);
                logger.debug(SecurityContext.getUsername() + "删除组[" + group.getName() + "]以及其所有子组!");
            } else {
                group.setStatus(CommonStatus.CANCELED.getValue());
            }
        }
    }

    @Override
    public List<GroupVo> queryValid(GroupBo bo) {
        if (bo == null) {
            bo = new GroupBo();
        }
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        List<Group> groups = groupDao.query(bo);
        return VoHelper.wrapVos(groups, this);
    }

    @Override
    public boolean hasName(String groupId, String name) {
        return groupDao.hasName(groupId, name);
    }

    @Override
    public List<GroupVo> tree() {
        List<Group> groups = groupDao.query(null);
        List<Group> tree = TreeFactory.buildTree(groups, Group.class);
        return VoHelper.wrapVos(tree, this);
    }

    public GroupVo wrap(Group group) {
        if (group == null) return null;
        GroupVo vo = new GroupVo();
        BeanUtils.copyProperties(group, vo);
        Group parent = group.getParent();
        if (group.getParent() != null) {
            vo.setParentId(parent.getId());
            vo.setParentName(parent.getName());
        }
        List<Group> children = group.getChildren();
        if (children != null) {
            vo.setChildren(VoHelper.wrapVos(children, this));
        }
        ParameterContainer container = ParameterContainer.getInstance();
        vo.setStatusName(container.getSystemName(ParameterConstant.COMMON_STATE, vo.getStatus()));
        return vo;
    }
}
