package eccrm.base.tenement.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import eccrm.base.tenement.bo.DocumentBo;
import eccrm.base.tenement.dao.DocumentDao;
import eccrm.base.tenement.domain.Document;
import eccrm.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: miles
 * @datetime: 2014-07-01
 */
@Repository("documentDao")
public class DocumentDaoImpl extends HibernateDaoHelper implements DocumentDao {

    @Override
    public String save(Document document) {
        return (String) getSession().save(document);
    }

    @Override
    public void update(Document document) {
        getSession().update(document);
    }

    @Override
    public List<Document> query(DocumentBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public long getTotal(DocumentBo bo) {
        Criteria criteria = createRowCountsCriteria(Document.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public int deleteById(String id) {
        return getSession().createQuery("delete from " + Document.class.getName() + " d where d.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public Document findById(String id) {
        return (Document) getSession().get(Document.class, id);
    }

    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(DocumentBo bo) {
        Criteria criteria = createCriteria(Document.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    /**
     * 根据BO初始化org.hibernate.Criteria对象
     * 如果org.hibernate.Criteria为null，则抛出异常
     * 如果BO为null，则新建一个空的对象
     */
    private void initCriteria(Criteria criteria, DocumentBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        if (bo == null) bo = new DocumentBo();
        criteria.add(Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Document> queryByTenementId(String tenementId) {
        if (StringUtils.isEmpty(tenementId)) {
            throw new IllegalArgumentException("租户ID不能为空!");
        }
        Criteria criteria = createCriteria(Document.class);
        criteria.add(Restrictions.eq("tenementId", tenementId));
        return criteria.list();
    }
}