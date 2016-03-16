package eccrm.base.im.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.base.im.bo.NewsRealReceiverBo;
import eccrm.base.im.dao.NewsRealReceiverDao;
import eccrm.base.im.domain.NewsRealReceiver;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("newsRealReceiverDao")
public class NewsRealReceiverDaoImpl extends HibernateDaoHelper implements NewsRealReceiverDao {

    @Override
    public String save(NewsRealReceiver newsRealReceiver) {
        return (String) getSession().save(newsRealReceiver);
    }

    @Override
    public void update(NewsRealReceiver newsRealReceiver) {
        getSession().update(newsRealReceiver);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NewsRealReceiver> query(NewsRealReceiverBo bo) {
        Criteria criteria = createCriteria(NewsRealReceiver.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(NewsRealReceiverBo bo) {
        Criteria criteria = createRowCountsCriteria(NewsRealReceiver.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + NewsRealReceiver.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(NewsRealReceiver newsRealReceiver) {
        Assert.notNull(newsRealReceiver, "要删除的对象不能为空!");
        getSession().delete(newsRealReceiver);
    }

    @Override
    public NewsRealReceiver findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (NewsRealReceiver) getSession().get(NewsRealReceiver.class, id);
    }

    private void initCriteria(Criteria criteria, NewsRealReceiverBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

    @Override
    public DetachedCriteria findPersonalUnreadNews(String receiverId) {
        Assert.hasText(receiverId, "新闻公告:查询个人未读公告时,未获得员工ID!");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(NewsRealReceiver.class);
        detachedCriteria.setProjection(Projections.distinct(Projections.property("newsId")))
                .add(Restrictions.eq("receiverId", receiverId))
                .add(Restrictions.eq("hasRead", false));
        return detachedCriteria;
    }

    @Override
    public DetachedCriteria findPersonalNews(String receiverId) {
        Assert.hasText(receiverId, "新闻公告:查询个人公告时,未获得员工ID!");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(NewsRealReceiver.class);
        detachedCriteria.setProjection(Projections.distinct(Projections.property("newsId")))
                .add(Restrictions.eq("receiverId", receiverId));
        return detachedCriteria;
    }

    @Override
    public DetachedCriteria findPersonalReadNews(String receiverId) {
        Assert.hasText(receiverId, "新闻公告:查询个人已读公告时,未获得员工ID!");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(NewsRealReceiver.class);
        detachedCriteria.setProjection(Projections.distinct(Projections.property("newsId")))
                .add(Restrictions.eq("receiverId", receiverId))
                .add(Restrictions.eq("hasRead", true));
        return detachedCriteria;
    }
}