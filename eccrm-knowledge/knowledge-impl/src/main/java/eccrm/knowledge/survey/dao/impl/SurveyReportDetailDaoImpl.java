package eccrm.knowledge.survey.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.knowledge.survey.bo.SurveyReportDetailBo;
import eccrm.knowledge.survey.dao.SurveyReportDetailDao;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.domain.SurveyReportDetail;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("surveyReportDetailDao")
public class SurveyReportDetailDaoImpl extends HibernateDaoHelper implements SurveyReportDetailDao {

    @Override
    public String save(SurveyReportDetail surveyReportDetail) {
        return (String) getSession().save(surveyReportDetail);
    }

    @Override
    public void update(SurveyReportDetail surveyReportDetail) {
        getSession().update(surveyReportDetail);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SurveyReportDetail> query(SurveyReportDetailBo bo) {
        Criteria criteria = createCriteria(SurveyReportDetail.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(SurveyReportDetailBo bo) {
        Criteria criteria = createRowCountsCriteria(SurveyReportDetail.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + SurveyReportDetail.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(SurveyReportDetail surveyReportDetail) {
        Assert.notNull(surveyReportDetail, "要删除的对象不能为空!");
        getSession().delete(surveyReportDetail);
    }


    @Override
    public SurveyReportDetail findBySeq(String surveyReportId, int seq) {
        Assert.hasText(surveyReportId, "获取指定序号的题目失败!请指定试卷!");
        return (SurveyReportDetail) createCriteria(SurveyReportDetail.class)
                .add(Restrictions.eq("surveyReportId", surveyReportId))
                .add(Restrictions.eq("sequenceNo", seq))
                .uniqueResult();
    }

    @Override
    public SurveyReportDetail findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (SurveyReportDetail) getSession().get(SurveyReportDetail.class, id);
    }

    @Override
    public String getAnswer(String surveyReportId, String subjectId) {
        Assert.hasText(surveyReportId, "查看答案失败!试卷ID不能为空!");
        Assert.hasText(subjectId, "查看答案失败!题目ID不能为空!");
        return (String) createCriteria(SurveyReportDetail.class)
                .setProjection(Projections.property("answer"))
                .add(Restrictions.eq("surveyReportId", surveyReportId))
                .add(Restrictions.eq("subjectId", subjectId))
                .uniqueResult();
    }


    @Override
    public SurveyReportDetail findBySubject(String surveyReportId, String subjectId) {
        Assert.hasText(surveyReportId, "获取题目明细失败!试卷ID不能为空!");
        Assert.hasText(subjectId, "获取题目明细失败!题目ID不能为空!");
        return (SurveyReportDetail) createCriteria(SurveyReportDetail.class)
                .add(Restrictions.eq("surveyReportId", surveyReportId))
                .add(Restrictions.eq("subjectId", subjectId))
                .uniqueResult();
    }

    @Override
    public List<Subject> querySubjectById(String surveyReportId) {
        Criteria criteria = createCriteria(Subject.class);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SurveyReportDetail.class)
                .setProjection(Projections.property("subjectId"))
                .add(Restrictions.eq("surveyReportId", surveyReportId));
        return criteria.add(Property.forName("id").in(detachedCriteria))
                .list();
    }

    private void initCriteria(Criteria criteria, SurveyReportDetailBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}