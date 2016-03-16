package eccrm.base.log.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.base.log.bo.OperateLogBo;
import eccrm.base.log.dao.OperateLogDao;
import eccrm.base.log.domain.OperateLog;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("operateLogDao")
public class OperateLogDaoImpl extends HibernateDaoHelper implements OperateLogDao {

    @Override
    public String save(OperateLog operateLog) {
        return (String) getSession().save(operateLog);
    }

    @Override
    public void update(OperateLog operateLog) {
        getSession().update(operateLog);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OperateLog> query(OperateLogBo bo) {
        Criteria criteria = createCriteria(OperateLog.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(OperateLogBo bo) {
        Criteria criteria = createRowCountsCriteria(OperateLog.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + OperateLog.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(OperateLog operateLog) {
        Assert.notNull(operateLog, "要删除的对象不能为空!");
        getSession().delete(operateLog);
    }

    @Override
    public OperateLog findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (OperateLog) getSession().get(OperateLog.class, id);
    }

    private void initCriteria(Criteria criteria, OperateLogBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}