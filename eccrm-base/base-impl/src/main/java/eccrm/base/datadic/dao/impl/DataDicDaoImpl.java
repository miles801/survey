package eccrm.base.datadic.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.base.datadic.bo.DataDicBo;
import eccrm.base.datadic.dao.DataDicCondition;
import eccrm.base.datadic.dao.DataDicConditionParser;
import eccrm.base.datadic.dao.DataDicDao;
import eccrm.base.datadic.domain.DataDic;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("dataDicDao")
public class DataDicDaoImpl extends HibernateDaoHelper implements DataDicDao {

    @Override
    public String save(DataDic dataDic) {
        return (String) getSession().save(dataDic);
    }

    @Override
    public void update(DataDic dataDic) {
        getSession().update(dataDic);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DataDic> query(DataDicBo bo) {
        Criteria criteria = createCriteria(DataDic.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(DataDicBo bo) {
        Criteria criteria = createRowCountsCriteria(DataDic.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + DataDic.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(DataDic dataDic) {
        Assert.notNull(dataDic, "要删除的对象不能为空!");
        getSession().delete(dataDic);
    }

    @Override
    public DataDic findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (DataDic) getSession().get(DataDic.class, id);
    }

    @Override
    public DataDic load(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (DataDic) getSession().load(DataDic.class, id);
    }

    @Override
    public DataDic findByCode(String code) {
        Assert.hasText(code, "编号不能为空!");
        return (DataDic) createCriteria(DataDic.class)
                .add(Restrictions.eq("code", code))
                .setMaxResults(1)
                .uniqueResult();
    }

    private void initCriteria(Criteria criteria, DataDicBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> queryData(Class<T> clazz, DataDicCondition condition) {
        Session session = getSession();
        DataDicConditionParser parser = new DataDicConditionParser(session, condition);
        DetachedCriteria criteria = parser.parse();
        return criteria.getExecutableCriteria(session).list();
    }
}