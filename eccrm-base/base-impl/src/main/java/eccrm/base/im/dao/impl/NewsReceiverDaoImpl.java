package eccrm.base.im.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.base.im.bo.NewsReceiverBo;
import eccrm.base.im.dao.NewsReceiverDao;
import eccrm.base.im.domain.NewsReceiver;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("newsReceiverDao")
public class NewsReceiverDaoImpl extends HibernateDaoHelper implements NewsReceiverDao {

    @Override
    public String save(NewsReceiver newsReceiver) {
        return (String) getSession().save(newsReceiver);
    }

    @Override
    public void update(NewsReceiver newsReceiver) {
        getSession().update(newsReceiver);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NewsReceiver> query(NewsReceiverBo bo) {
        Criteria criteria = createCriteria(NewsReceiver.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(NewsReceiverBo bo) {
        Criteria criteria = createRowCountsCriteria(NewsReceiver.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + NewsReceiver.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(NewsReceiver newsReceiver) {
        Assert.notNull(newsReceiver, "要删除的对象不能为空!");
        getSession().delete(newsReceiver);
    }

    @Override
    public NewsReceiver findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (NewsReceiver) getSession().get(NewsReceiver.class, id);
    }

    private void initCriteria(Criteria criteria, NewsReceiverBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }


    @Override
    public void deleteByNewsId(String newsId) {
        Assert.hasText(newsId, "新闻公告:删除公告的接收对象时,新闻公告ID不能为空!");
        getSession().createQuery("delete from " + NewsReceiver.class.getName() + " nr where nr.newsId=?")
                .setParameter(0, newsId)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findByNewsId(String newsId) {
        Assert.hasText(newsId, "新闻公告:获得公告的接收对象时,新闻公告ID不能为空!");
        return createCriteria(NewsReceiver.class)
                .setProjection(Projections.property("receiverId"))
                .add(Restrictions.eq("newsId", newsId))
                .list();
    }
}