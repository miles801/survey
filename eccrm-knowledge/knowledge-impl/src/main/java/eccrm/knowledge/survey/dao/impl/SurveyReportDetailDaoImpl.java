package eccrm.knowledge.survey.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.knowledge.survey.bo.SurveyReportDetailBo;
import eccrm.knowledge.survey.dao.SurveyReportDetailDao;
import eccrm.knowledge.survey.domain.SurveyReportDetail;
import org.hibernate.Criteria;
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

    private void initCriteria(Criteria criteria, SurveyReportDetailBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}