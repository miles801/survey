package eccrm.knowledge.survey.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.knowledge.survey.bo.SurveySubjectBo;
import eccrm.knowledge.survey.dao.SurveySubjectDao;
import eccrm.knowledge.survey.domain.SurveySubject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("surveySubjectDao")
public class SurveySubjectDaoImpl extends HibernateDaoHelper implements SurveySubjectDao {

    @Override
    public String save(SurveySubject surveySubject) {
        return (String) getSession().save(surveySubject);
    }

    @Override
    public void update(SurveySubject surveySubject) {
        getSession().update(surveySubject);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SurveySubject> query(SurveySubjectBo bo) {
        Criteria criteria = createCriteria(SurveySubject.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> querySubjectIds(String surveyId) {
        Assert.hasText(surveyId, "查询试卷题目时,试卷ID不能为空!");
        return createCriteria(SurveySubject.class)
                .setProjection(Projections.property("subjectId"))
                .add(Restrictions.eq("surveyId", surveyId))
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SurveySubject> queryBySurveyId(String surveyId) {
        Assert.hasText(surveyId, "查询试卷题目时,试卷ID不能为空!");
        return createCriteria(SurveySubject.class)
                .add(Restrictions.eq("surveyId", surveyId))
                .addOrder(Order.asc("sequenceNo"))
                .list();
    }

    @Override
    public Long getTotal(SurveySubjectBo bo) {
        Criteria criteria = createRowCountsCriteria(SurveySubject.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + SurveySubject.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(SurveySubject surveySubject) {
        Assert.notNull(surveySubject, "要删除的对象不能为空!");
        getSession().delete(surveySubject);
    }

    @Override
    public void deleteBatch(String[] ids) {
        if (ids == null || ids.length < 1) {
            return;
        }
        getSession().createQuery("delete from " + SurveySubject.class.getName() + " s where s.id in(:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public SurveySubject findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (SurveySubject) getSession().get(SurveySubject.class, id);
    }

    private void initCriteria(Criteria criteria, SurveySubjectBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

    @Override
    public void deleteBatch(String surveyId, String[] subjectIds) {
        Assert.hasText(surveyId);
        Assert.notEmpty(subjectIds);
        getSession().createQuery("delete from " + SurveySubject.class.getName() + " s where s.surveyId=? and s.subjectId in(:subjectIds)")
                .setParameter(0, surveyId)
                .setParameterList("subjectIds", subjectIds)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> exists(String surveyId, String[] subjectIds) {
        Assert.hasText(surveyId);
        Assert.notEmpty(subjectIds);
        return createCriteria(SurveySubject.class)
                .setProjection(Projections.property("subjectId"))
                .add(Restrictions.eq("surveyId", surveyId))
                .add(Restrictions.in("subjectId", subjectIds))
                .list();
    }

    @Override
    public boolean hasSubject(String surveyId) {
        Assert.hasText(surveyId, "试卷:查询试卷的是否设置了题目是,必须指定试卷ID!");
        Long count = (Long) createRowCountsCriteria(SurveySubject.class)
                .add(Restrictions.eq("surveyId", surveyId))
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public void resetFirstSubject(String surveyId, String subjectId) {
        Assert.hasText(surveyId, "试卷:设置试卷第一题时,必须指定试卷ID!");
        Assert.hasText(surveyId, "试卷:设置试卷第一题时,必须指定题目ID!");
        // 清空之前的设置
        Session session = getSession();
        session.createQuery("update " + SurveySubject.class.getName() + " ss set ss.isFirst=false where ss.surveyId=? and ss.isFirst=true")
                .setParameter(0, surveyId)
                .executeUpdate();

        // 重新设置
        session.createQuery("update " + SurveySubject.class.getName() + " ss set ss.isFirst=true where ss.surveyId=? and ss.subjectId=?")
                .setParameter(0, surveyId)
                .setParameter(1, subjectId)
                .executeUpdate();

    }
}