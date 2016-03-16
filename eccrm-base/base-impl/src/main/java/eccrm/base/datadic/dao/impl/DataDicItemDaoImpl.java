package eccrm.base.datadic.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.base.datadic.bo.DataDicItemBo;
import eccrm.base.datadic.dao.DataDicItemDao;
import eccrm.base.datadic.domain.DataDicItem;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("dataDicItemDao")
public class DataDicItemDaoImpl extends HibernateDaoHelper implements DataDicItemDao {

    @Override
    public String save(DataDicItem dataDicItem) {
        return (String) getSession().save(dataDicItem);
    }

    @Override
    public void update(DataDicItem dataDicItem) {
        getSession().update(dataDicItem);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DataDicItem> query(DataDicItemBo bo) {
        Criteria criteria = createCriteria(DataDicItem.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(DataDicItemBo bo) {
        Criteria criteria = createRowCountsCriteria(DataDicItem.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + DataDicItem.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(DataDicItem dataDicItem) {
        Assert.notNull(dataDicItem, "要删除的对象不能为空!");
        getSession().delete(dataDicItem);
    }

    @Override
    public DataDicItem findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (DataDicItem) getSession().get(DataDicItem.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<DataDicItem> queryByDicCode(String code) {
        if (code == null || "".equals(code.trim())) {
            return null;
        }
        return createCriteria(DataDicItem.class)
                .add(Restrictions.eq("dicCode", code))
                .list();
    }

    @Override
    public void deleteByDicId(String dicId) {
        if (dicId == null || "".equals(dicId.trim())) {
            return;
        }
        getSession().createQuery("delete from " + DataDicItem.class.getName() + " ddi where ddi.dicId=?")
                .setParameter(0, dicId)
                .executeUpdate();
    }

    private void initCriteria(Criteria criteria, DataDicItemBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}