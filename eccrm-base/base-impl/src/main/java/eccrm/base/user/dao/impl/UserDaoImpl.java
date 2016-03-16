package eccrm.base.user.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.context.SecurityContext;
import eccrm.base.user.bo.UserBo;
import eccrm.base.user.dao.UserDao;
import eccrm.base.user.domain.User;
import eccrm.utils.Argument;
import eccrm.utils.StringUtils;
import eccrm.utils.date.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author miles
 * @datetime 2014-03-14
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateDaoHelper implements UserDao {
    public static final String FILTER_NAME = "QUERY_USER";

    @Override
    public String save(User user) {
        return (String) getSession().save(user);
    }

    @Override
    public void update(User user) {
        getSession().update(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> query(UserBo bo) {
//        Criteria criteria = createCriteria(User.class, FILTER_NAME, "employeeId", FilterFieldType.EMPLOYEE);
        Criteria criteria = createCriteria(User.class);
        initCriteria(criteria, bo);
        criteria.addOrder(Order.asc("username"));
        return criteria.list();
    }

    @Override
    public Long getTotal(UserBo bo) {
//        Criteria criteria = createRowCountsCriteria(User.class, FILTER_NAME, "employeeId", FilterFieldType.EMPLOYEE);
        Criteria criteria = createRowCountsCriteria(User.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }

    @Override
    public String getName(String id) {
        Argument.isEmpty(id, "根据用户ID查询用户名时,用户ID不能为空!");
        return (String) createCriteria(User.class)
                .setProjection(Projections.property("username"))
                .add(Restrictions.idEq(id))
                .uniqueResult();
    }

    @Override
    public boolean updatePassword(String id, String password, Date deadline) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("更新用户密码时,参数不匹配!");
        }
        if (deadline != null) {
            getSession().createQuery("update " + User.class.getName() + " u set u.password=? , u.passwordDeadline=? where u.id=?")
                    .setParameter(0, password)
                    .setParameter(1, deadline)
                    .setParameter(2, id)
                    .executeUpdate();
        } else {
            getSession().createQuery("update " + User.class.getName() + " u set u.password=? where u.id=?")
                    .setParameter(0, password)
                    .setParameter(1, id)
                    .executeUpdate();
        }
        return true;
    }

    @Override
    public void delete(User user) {
        if (user == null || StringUtils.isEmpty(user.getId())) {
            throw new IllegalArgumentException("删除用户时,用户不能为空!");
        }
        getSession().delete(user);
    }

    @Override
    public User findByUsername(String username) {
        Argument.isEmpty(username, "根据用户名查询用户时,用户名不能为空!");
        Criteria criteria = createCriteria(User.class)
                .add(Restrictions.eq("username", username));
        if (StringUtils.isNotEmpty(SecurityContext.getTenementId())) {
            criteria.add(Restrictions.eq("tenementId", SecurityContext.getTenementId()));
        }
        List<User> users = criteria.setMaxResults(2).list();
        if (users == null || users.isEmpty()) {
            return null;
        } else if (users.size() > 1) {
            throw new RuntimeException("用户名" + username + "重复!");
        }
        return users.get(0);
    }

    @Override
    public User findByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException("根据邮箱查询用户时,邮箱不能为空!");
        }
        return (User) createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public User findById(String id) {
        return (User) getSession().get(User.class, id);
    }

    @Override
    public boolean hasName(String username) {
        Argument.isEmpty(username, "查询用户名是否重复时,用户名不能为空!");
        Criteria criteria = createRowCountsCriteria(User.class)
                .add(Restrictions.eq("username", username));
        return (Long) criteria.uniqueResult() > 0;
    }

    @Override
    public boolean hasCode(String code) {
        Argument.isEmpty(code, "查询用户编号是否重复时,编号不能为空!");
        Criteria criteria = createRowCountsCriteria(User.class)
                .add(Restrictions.eq("code", code));
        return (Long) criteria.uniqueResult() > 0;
    }

    private void initCriteria(Criteria criteria, UserBo bo) {
        if (bo == null) bo = new UserBo();
        criteria.add(Example.create(bo)
                .excludeProperty("status")
                .excludeProperty("type")
                .enableLike(MatchMode.ANYWHERE).ignoreCase());
        Boolean valid = bo.getValid();
        if (valid != null && valid) {
            Date now = new Date();
            criteria.add(Restrictions.le("startDate", DateUtils.getDayBegin(now)))
                    .add(Restrictions.gt("endDate", new Date(DateUtils.getNextDayBegin(now).getTime() - 1)));
        }
        //状态和类型进行精确匹配
        if (StringUtils.isNotEmpty(bo.getStatus())) {
            criteria.add(Restrictions.eq("status", bo.getStatus()));
        }
        if (StringUtils.isNotEmpty(bo.getType())) {
            criteria.add(Restrictions.eq("type", bo.getType()));
        }

    }

    @Override
    public String findEmployeeId(String username) {
        if (org.apache.commons.lang3.StringUtils.isBlank(username)) {
            return null;
        }
        return (String) getSession().createCriteria(User.class)
                .setProjection(Projections.property("employeeId"))
                .add(Restrictions.eq("username", username))
                .setMaxResults(1)
                .uniqueResult();
    }
}