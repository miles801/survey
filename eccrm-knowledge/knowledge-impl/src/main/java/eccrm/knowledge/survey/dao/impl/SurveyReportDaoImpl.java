package eccrm.knowledge.survey.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.knowledge.survey.bo.SurveyReportBo;
import eccrm.knowledge.survey.dao.SurveyReportDao;
import eccrm.knowledge.survey.domain.SurveyReport;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("surveyReportDao")
public class SurveyReportDaoImpl extends HibernateDaoHelper implements SurveyReportDao {

    @Override
    public String save(SurveyReport surveyReport) {
        return (String) getSession().save(surveyReport);
    }

    @Override
    public void update(SurveyReport surveyReport) {
        getSession().update(surveyReport);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SurveyReport> query(SurveyReportBo bo) {
        Criteria criteria = createCriteria(SurveyReport.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(SurveyReportBo bo) {
        Criteria criteria = createRowCountsCriteria(SurveyReport.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + SurveyReport.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(SurveyReport surveyReport) {
        Assert.notNull(surveyReport, "要删除的对象不能为空!");
        getSession().delete(surveyReport);
    }

    @Override
    public SurveyReport findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (SurveyReport) getSession().get(SurveyReport.class, id);
    }

    private void initCriteria(Criteria criteria, SurveyReportBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}