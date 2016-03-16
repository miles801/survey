package eccrm.base.role.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.SystemContainer;
import com.ycrl.core.pager.Pager;
import eccrm.base.role.bo.RoleBo;
import eccrm.base.role.dao.RoleDao;
import eccrm.base.role.dao.RoleGroupDao;
import eccrm.base.role.dao.RoleUserDao;
import eccrm.base.role.domain.Role;
import eccrm.utils.Argument;
import eccrm.utils.StringUtils;
import eccrm.utils.date.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author miles
 * @datetime 2014-03-26
 */
@Repository("roleDao")
public class RoleDaoImpl extends HibernateDaoHelper implements RoleDao {
    private static final String DEFAULT_ORDER = "pinyin";

    @Override
    public String save(Role role) {
        return (String) getSession().save(role);
    }

    @Override
    public void update(Role role) {
        getSession().update(role);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> query(RoleBo bo) {
        Criteria criteria = createCriteria(Role.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(RoleBo bo) {
        Criteria criteria = createRowCountsCriteria(Role.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void delete(Role role) {
        Argument.isNull(role, "要删除的角色不能为空!");
        Argument.isEmpty(role.getId(), "错误的角色对象,未获得ID!");
        getSession().delete(role);
    }


    @Override
    public boolean hasName(String id, String name) {
        Argument.isEmpty(name, "判断指定名称的角色是否存在时,角色名称不能为空!");
        Criteria criteria = createRowCountsCriteria(Role.class);
        if (StringUtils.isNotEmpty(id)) {
            criteria.add(Restrictions.ne("id", id));
        }
        criteria.add(Restrictions.eq("name", name));
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    public boolean hasCode(String id, String code) {
        Argument.isEmpty(code, "判断指定编号的角色是否存在时,编号名称不能为空!");
        Criteria criteria = createRowCountsCriteria(Role.class);
        if (StringUtils.isNotEmpty(id)) {
            criteria.add(Restrictions.ne("id", id));
        }
        criteria.add(Restrictions.eq("code", code));
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    public Role findById(String id) {
        return (Role) getSession().get(Role.class, id);
    }

    private void initCriteria(Criteria criteria, RoleBo bo) {
        if (bo == null) bo = new RoleBo();
        criteria.add(
                Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase()
                        .excludeProperty("status")
        );
        if (StringUtils.isNotEmpty(bo.getStatus())) {
            criteria.add(Restrictions.eq("status", bo.getStatus()));
        }
        if (bo.getValid() != null && bo.getValid()) {
            Date now = new Date();
            criteria.add(Restrictions.le("startDate", DateUtils.getDayBegin(now)))
                    .add(Restrictions.gt("endDate", new Date(DateUtils.getNextDayBegin(now).getTime() - 1)));
        }
        if (StringUtils.isNotEmpty(bo.getGroupId())) {
            RoleGroupDao roleGroupDao = SystemContainer.getInstance().getBean(RoleGroupDao.class);
            List<String> roleIds = roleGroupDao.queryIdsByGroupId(bo.getGroupId());
            if (!roleIds.isEmpty()) {
                criteria.add(Restrictions.not(Restrictions.in("id", roleIds)));
            }
        }
        if (StringUtils.isNotEmpty(bo.getUserId())) {
            RoleUserDao roleUserDao = SystemContainer.getInstance().getBean(RoleUserDao.class);
            List<String> userIds = roleUserDao.queryRoleIds(bo.getUserId());
            if (!userIds.isEmpty()) {
                criteria.add(Restrictions.not(Restrictions.in("id", userIds)));
            }
        }

        if (Pager.getOrder() == null || !Pager.getOrder().hasNext()) {
            criteria.addOrder(Order.asc(DEFAULT_ORDER));
        }
    }
}