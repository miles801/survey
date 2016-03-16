package eccrm.base.user.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import eccrm.base.user.bo.LoginLogBo;
import eccrm.base.user.dao.LoginLogDao;
import eccrm.base.user.domain.LoginLog;
import eccrm.base.user.domain.LogoutType;
import com.ycrl.core.pager.Pager;
import eccrm.utils.StringUtils;
import eccrm.utils.date.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author miles
 * @datetime 2014-04-14
 */
@Repository("loginLogDao")
public class LoginLogDaoImpl extends HibernateDaoHelper implements LoginLogDao {

    @Override
    public String save(LoginLog loginLog) {
        return (String) getSession().save(loginLog);
    }

    @Override
    public void update(LoginLog loginLog) {
        getSession().update(loginLog);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LoginLog> query(LoginLogBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(LoginLogBo bo) {
        Criteria criteria = createRowCountsCriteria(LoginLog.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().delete(getSession().load(LoginLog.class, id));
    }

    @Override
    public LoginLog last(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("查询最近的一次登录信息时，没有指名用户的ID!");
        }
        return (LoginLog) createCriteria(LoginLog.class)
                .add(Restrictions.eq("creatorId", userId))
                .setMaxResults(1)
                .addOrder(Order.desc("createdDatetime"))
                .uniqueResult();
    }

    @Override
    public void logoutAllBySystemDown() {

        getSession().createQuery("update " + LoginLog.class.getName() + " l set l.logoutDatetime=?, l.logoutType=? where l.logoutDatetime is null")
                .setParameter(0, new Date())
                .setParameter(1, LogoutType.SYSTEM_DOWN.getValue())
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> queryAllOnlineUserId() {
        return getDefaultCriteria(null)
                .setProjection(Projections.property("creatorId"))
                .add(Restrictions.isNull("logoutDatetime"))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LoginLog> queryAllOnlineUser(LoginLogBo bo) {
        return getDefaultCriteria(bo)
                .add(Restrictions.isNull("logoutDatetime"))
                .list();
    }

    @Override
    public LoginLog findById(String id) {
        return (LoginLog) getSession().get(LoginLog.class, id);
    }

    @Override
    public boolean userIsOnline(String userId) {
        LoginLog loginLog = last(userId);
        //获得最后一次登录信息
        //如果信息不为空，切没有退出时间，则默认为在线状态
        //可能由于系统的问题导致用户没有手动的退出（可以在关闭服务器的时候批处理所有的在线用户）
        if (loginLog != null && (loginLog.getLogoutDatetime() == null || loginLog.getLogoutType() == null)) {
            return true;
        }
        return false;
    }

    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(LoginLogBo bo) {
        Criteria criteria = createCriteria(LoginLog.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    /**
     * 根据BO初始化org.hibernate.Criteria对象
     * 如果org.hibernate.Criteria为null，则抛出异常
     * 如果BO为null，则新建一个空的对象
     */
    private void initCriteria(Criteria criteria, LoginLogBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        if (bo == null) bo = new LoginLogBo();
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());
        if (bo.getStartDate() != null) {
            Date start = DateUtils.getDayBegin(bo.getStartDate());
            criteria.add(Restrictions.ge("createdDatetime", start));
        }
        if (bo.getEndDate() != null) {
            Date end = DateUtils.getNextDayBegin(bo.getEndDate());
            criteria.add(Restrictions.lt("createdDatetime", end));
        }
        Criteria userCriteria = criteria.createCriteria("user");
        if (StringUtils.isNotEmpty(bo.getUsername())) {
            userCriteria.add(Restrictions.like("username", bo.getUsername(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotEmpty(bo.getEmployeeName())) {
            userCriteria.add(Restrictions.like("employeeName", bo.getEmployeeName(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotEmpty(bo.getEmployeeNo())) {
            userCriteria.add(Restrictions.like("employeeNo", bo.getEmployeeNo(), MatchMode.ANYWHERE));
        }
        if (Pager.getOrder() == null || !Pager.getOrder().hasNext()) {
            criteria.addOrder(Order.desc("createdDatetime"));
        }
    }

}