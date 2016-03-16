package eccrm.base.im.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.base.im.bo.NewsBo;
import eccrm.base.im.dao.NewsDao;
import eccrm.base.im.dao.NewsRealReceiverDao;
import eccrm.base.im.domain.News;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Michael
 */
@Repository("newsDao")
public class NewsDaoImpl extends HibernateDaoHelper implements NewsDao {

    @Resource
    private NewsRealReceiverDao newsRealReceiverDao;

    @Override
    public String save(News news) {
        return (String) getSession().save(news);
    }

    @Override
    public void update(News news) {
        getSession().update(news);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<News> query(NewsBo bo) {
        Criteria criteria = createCriteria(News.class);
        initCriteria(criteria, bo);
        criteria.addOrder(Order.desc("isTop"));
        criteria.addOrder(Order.desc("publishTime"));
        criteria.addOrder(Order.desc("createdDatetime"));
        return criteria.list();
    }

    @Override
    public Long getTotal(NewsBo bo) {
        Criteria criteria = createRowCountsCriteria(News.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + News.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(News news) {
        Assert.notNull(news, "要删除的对象不能为空!");
        getSession().delete(news);
    }

    @Override
    public News findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (News) getSession().get(News.class, id);
    }

    private void initCriteria(Criteria criteria, NewsBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<News> personalReadNews(NewsBo bo) {
        DetachedCriteria detachedCriteria = newsRealReceiverDao.findPersonalReadNews(SecurityContext.getUserId());
        Criteria criteria = createCriteria(News.class);
        initCriteria(criteria, bo);
        criteria.add(Property.forName("id").in(detachedCriteria));
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<News> personalUnreadNews(NewsBo bo) {
        DetachedCriteria detachedCriteria = newsRealReceiverDao.findPersonalUnreadNews(SecurityContext.getUserId());
        Criteria criteria = createCriteria(News.class);
        initCriteria(criteria, bo);
        criteria.add(Property.forName("id").in(detachedCriteria));
        criteria.addOrder(Order.desc("isTop"));
        criteria.addOrder(Order.desc("publishTime"));
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<News> personalNews(NewsBo bo) {
        DetachedCriteria detachedCriteria = newsRealReceiverDao.findPersonalNews(SecurityContext.getUserId());
        Criteria criteria = createCriteria(News.class);
        initCriteria(criteria, bo);
        criteria.add(Property.forName("id").in(detachedCriteria));
        criteria.addOrder(Order.desc("isTop"));
        criteria.addOrder(Order.desc("publishTime"));
        return criteria.list();
    }
}