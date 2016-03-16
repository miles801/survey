package eccrm.base.im.dao.impl;

import eccrm.base.im.bo.NewsCollectBo;
import eccrm.base.im.domain.NewsCollect;
import eccrm.base.im.dao.NewsCollectDao;
import com.ycrl.core.HibernateDaoHelper;
import org.hibernate.criterion.Example;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import java.util.List;


/**
 * @author Michael
 */
@Repository("newsCollectDao")
public class NewsCollectDaoImpl extends HibernateDaoHelper implements NewsCollectDao {

    @Override
    public String save(NewsCollect newsCollect) {
        return (String) getSession().save(newsCollect);
    }

    @Override
    public void update(NewsCollect newsCollect) {
        getSession().update(newsCollect);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NewsCollect> query(NewsCollectBo bo) {
        Criteria criteria = createCriteria(NewsCollect.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(NewsCollectBo bo) {
        Criteria criteria = createRowCountsCriteria(NewsCollect.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + NewsCollect.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(NewsCollect newsCollect) {
        Assert.notNull(newsCollect, "要删除的对象不能为空!");
        getSession().delete(newsCollect);
    }

    @Override
    public NewsCollect findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (NewsCollect) getSession().get(NewsCollect.class, id);
    }

    private void initCriteria(Criteria criteria, NewsCollectBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}