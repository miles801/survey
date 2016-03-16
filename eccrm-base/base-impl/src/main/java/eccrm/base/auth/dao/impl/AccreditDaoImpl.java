package eccrm.base.auth.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.exception.Argument;
import eccrm.base.auth.bo.AccreditBo;
import eccrm.base.auth.dao.AccreditDao;
import eccrm.base.auth.domain.Accredit;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Michael
 */
@Repository("accreditDao")
public class AccreditDaoImpl extends HibernateDaoHelper implements AccreditDao {

    @Override
    public String save(Accredit accredit) {
        return (String) getSession().save(accredit);
    }

    @Override
    public void update(Accredit accredit) {
        getSession().update(accredit);
    }

    @Override
    public List<Accredit> query(AccreditBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(AccreditBo bo) {
        Criteria criteria = createRowCountsCriteria(Accredit.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Accredit.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public Accredit findById(String id) {
        Argument.isEmpty(id, "查询时ID不能为空!");
        return (Accredit) getSession().get(Accredit.class, id);
    }

    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(AccreditBo bo) {
        Criteria criteria = createCriteria(Accredit.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    /**
     * 根据BO初始化org.hibernate.Criteria对象
     * 如果org.hibernate.Criteria为null，则抛出异常
     * 如果BO为null，则新建一个空的对象
     * 不能够在该方法里添加排序操作，否则在聚合查询时将会报错
     */
    private void initCriteria(Criteria criteria, AccreditBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        if (bo == null) bo = new AccreditBo();
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());
    }

}