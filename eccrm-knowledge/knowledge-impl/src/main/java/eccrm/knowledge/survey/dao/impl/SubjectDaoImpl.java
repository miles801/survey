package eccrm.knowledge.survey.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import com.ycrl.utils.string.StringUtils;
import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.dao.SubjectDao;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.domain.SurveySubject;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("subjectDao")
public class SubjectDaoImpl extends HibernateDaoHelper implements SubjectDao {

    @Override
    public String save(Subject subject) {
        return (String) getSession().save(subject);
    }

    @Override
    public void update(Subject subject) {
        getSession().update(subject);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Subject> query(SubjectBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(SubjectBo bo) {
        Criteria criteria = createRowCountsCriteria(Subject.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public int deleteById(String id) {
        return getSession().createQuery("delete from " + Subject.class.getName() + " s where s.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }


    @Override
    public Subject findById(String id) {
        return (Subject) getSession().get(Subject.class, id);
    }

    private Criteria getDefaultCriteria(SubjectBo bo) {
        Criteria criteria = createCriteria(Subject.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    private void initCriteria(Criteria criteria, SubjectBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        CriteriaUtils.addCondition(criteria, bo);
        String surveyId = bo.getSurveyId();

        // 排除指定问卷的题目
        if (StringUtils.isNotEmpty(surveyId)) {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SurveySubject.class);
            detachedCriteria.setProjection(Projections.property("subjectId"))
                    .add(Restrictions.eq("surveyId", surveyId));
            criteria.add(Property.forName("id").notIn(detachedCriteria));
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Subject> queryByIds(String[] ids, SubjectBo bo) {
        if (ids == null || ids.length < 1) {
            return null;
        }
        Criteria criteria = createCriteria(Subject.class)
                .add(Restrictions.in("id", ids));
        CriteriaUtils.addCondition(criteria, bo);
        return criteria.list();
    }
}