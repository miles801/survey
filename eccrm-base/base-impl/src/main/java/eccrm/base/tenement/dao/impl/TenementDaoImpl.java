package eccrm.base.tenement.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import eccrm.base.tenement.bo.TenementBo;
import eccrm.base.tenement.dao.TenementDao;
import eccrm.base.tenement.domain.Tenement;
import eccrm.utils.Argument;
import eccrm.utils.date.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author: miles
 * @datetime: 2014-03-14
 */
@Repository("tenementDao")
public class TenementDaoImpl extends HibernateDaoHelper implements TenementDao {
    @Override
    public String save(Tenement tenement) {
        return (String) getSession().save(tenement);
    }

    @Override
    public void update(Tenement tenement) {
        getSession().update(tenement);
    }

    @Override
    public List<Tenement> query(TenementBo bo) {
        Criteria criteria = createCriteria(Tenement.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(TenementBo bo) {
        Criteria criteria = createRowCountsCriteria(Tenement.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Tenement.class.getName() + " t where t.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public Tenement findById(String id) {
        return (Tenement) getSession().get(Tenement.class, id);
    }

    private void initCriteria(Criteria criteria, TenementBo bo) {
        if (bo == null) bo = new TenementBo();
        Date invalidStartDate = bo.getInvalidStartDate();
        if (invalidStartDate != null) {
            criteria.add(Restrictions.ge("endDate", DateUtils.getDayBegin(invalidStartDate)));
        }
        Date invalidEndDate = bo.getInvalidEndDate();
        if (invalidEndDate != null) {
            criteria.add(Restrictions.lt("endDate", DateUtils.getNextDayBegin(invalidEndDate)));
        }
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());

    }

    @Override
    public Tenement findByCode(String code) {
        Argument.isEmpty(code, "根据租户编号查询时,没有获得编号!");
        return (Tenement) createCriteria(Tenement.class)
                .add(Restrictions.eq("code", code))
                .uniqueResult();
    }
}