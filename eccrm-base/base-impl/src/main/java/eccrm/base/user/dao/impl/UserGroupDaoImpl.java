package eccrm.base.user.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import eccrm.base.user.dao.UserGroupDao;
import eccrm.base.user.domain.UserGroup;
import eccrm.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author miles
 * @datetime 2014-07-03
 */
@Repository("userGroupDao")
public class UserGroupDaoImpl extends HibernateDaoHelper implements UserGroupDao {

    @Override
    public String save(UserGroup userGroup) {
        return (String) getSession().save(userGroup);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserGroup> queryGroup(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("根据用户id查询用户组时,没有获得用户的ID!");
        }
        return createDefaultCriteria()
                .add(Restrictions.eq("user.id", userId))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserGroup> queryUser(String groupId) {
        if (StringUtils.isEmpty(groupId)) {
            throw new IllegalArgumentException("根据组ID查询用户时,没有获得组的ID!");
        }
        Criteria criteria = createDefaultCriteria();
        return criteria.add(Restrictions.eq("group.id", groupId))
                .list();
    }

    private Criteria createDefaultCriteria() {
        return createCriteria(UserGroup.class)
                .addOrder(Order.asc("sequenceNo"));
    }

    @Override
    public void deleteByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("根据用户ID删除用户组时,没有获得用户的ID!");
        }
        getSession().createQuery("delete from " + UserGroup.class.getName() + " ug where ug.user.id=?")
                .setParameter(0, userId)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> queryGroupIds(String userId) {
        return createCriteria(UserGroup.class)
                .setProjection(Projections.property("group.id"))
                .add(Restrictions.eq("user.id", userId))
                .list();
    }

    @Override
    public void deleteByGroupId(String groupId) {
        if (StringUtils.isEmpty(groupId)) {
            throw new IllegalArgumentException("根据用户ID删除用户组时,没有获得用户的ID!");
        }
        getSession().createQuery("delete from " + UserGroup.class.getName() + " u where u.group.id=?")
                .setParameter(0, groupId)
                .executeUpdate();
    }

}