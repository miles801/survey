package eccrm.knowledge.survey.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import com.ycrl.utils.tree.TreeUtils;
import eccrm.knowledge.survey.bo.SubjectCategoryBo;
import eccrm.knowledge.survey.dao.SubjectCategoryDao;
import eccrm.knowledge.survey.domain.SubjectCategory;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("subjectCategoryDao")
public class SubjectCategoryDaoImpl extends HibernateDaoHelper implements SubjectCategoryDao {

    @Override
    public String save(SubjectCategory libraryCategory) {
        String id = (String) getSession().save(libraryCategory);
        // 重置path
        new TreeUtils(SubjectCategory.class, getSession())
                .resetPath(id);
        return id;
    }

    @Override
    public void update(SubjectCategory libraryCategory) {
        // 是否改变了parentId
        Long count = (Long) getSession().createCriteria(SubjectCategory.class)
                .setProjection(Projections.rowCount())
                .add(Restrictions.idEq(libraryCategory.getId()))
                .add(StringUtils.isBlank(libraryCategory.getParentId()) ? Restrictions.isNull("parentId") : Restrictions.eq("parentId", libraryCategory.getParentId()))
                .uniqueResult();
        getSession().update(libraryCategory);

        // 如果上级发生了改变，则重置path
        if (count == 0) {
            new TreeUtils(SubjectCategory.class, getSession())
                    .resetPath(libraryCategory.getId());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SubjectCategory> query(SubjectCategoryBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        criteria.addOrder(Order.asc("sequenceNo"));
        return criteria.list();
    }


    @Override
    public Long getTotal(SubjectCategoryBo bo) {
        Criteria criteria = createRowCountsCriteria(SubjectCategory.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public int deleteById(String id) {
        return getSession().createQuery("delete from " + SubjectCategory.class.getName() + " lc where lc.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public SubjectCategory findById(String id) {
        return (SubjectCategory) getSession().get(SubjectCategory.class, id);
    }

    /**
     * 获得默认的org.hibernate.Criteria对象,并根据bo进行初始化（如果bo为null，则会新建一个空对象）
     * 为了防止新的对象中有数据，建议实体/BO均采用封装类型
     */
    private Criteria getDefaultCriteria(SubjectCategoryBo bo) {
        Criteria criteria = createCriteria(SubjectCategory.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    private void initCriteria(Criteria criteria, SubjectCategoryBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        CriteriaUtils.addCondition(criteria, bo);
    }


}