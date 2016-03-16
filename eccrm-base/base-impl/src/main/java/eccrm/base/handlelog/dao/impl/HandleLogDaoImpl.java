package eccrm.base.handlelog.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import eccrm.base.handlelog.bo.HandleLogBo;
import eccrm.base.handlelog.dao.HandleLogDao;
import eccrm.base.handlelog.domain.HandleLog;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: wangsd
 * @datetime: 2014-04-16
 */
@Repository("handleLogDao")
public class HandleLogDaoImpl extends HibernateDaoHelper implements HandleLogDao {

    @Override
    public String save(HandleLog handleLog) {
        return (String) getSession().save(handleLog);
    }

    @Override
    public void update(HandleLog handleLog) {
        getSession().update(handleLog);
    }

    @Override
    public List<HandleLog> query(HandleLogBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(HandleLogBo bo) {
        Criteria criteria = createRowCountsCriteria(HandleLog.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public int deleteById(String id) {
        getSession().delete(getSession().load(HandleLog.class, id));
        return 1;
    }

    @Override
    public HandleLog findById(String id) {
        return (HandleLog) getSession().get(HandleLog.class, id);
    }

    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(HandleLogBo bo) {
        Criteria criteria = createCriteria(HandleLog.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    /**
     * 根据BO初始化org.hibernate.Criteria对象
     * 如果org.hibernate.Criteria为null，则抛出异常
     * 如果BO为null，则新建一个空的对象
     */
    private void initCriteria(Criteria criteria, HandleLogBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("cirteria must not be null!");
        }
        if (bo == null) bo = new HandleLogBo();
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());
    }

    //根据日报、周报 或者 月报的id获得对应的处理记录集合,按照时间降序的方式
    @Override
    public List<HandleLog> findByReportId(String reportId) {
        Criteria criteria = createCriteria(HandleLog.class);

        if (reportId != null) {
            criteria.add(Restrictions.eq("businessId", reportId));
            criteria.addOrder(Order.asc("createdDatetime"));
            return criteria.list();
        }
        return null;
    }

}