package eccrm.knowledge.survey.dao.impl;

import com.ycrl.core.HibernateDaoHelper;
import com.ycrl.core.exception.Argument;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import eccrm.knowledge.survey.bo.SurveyBo;
import eccrm.knowledge.survey.dao.SurveyDao;
import eccrm.knowledge.survey.domain.Survey;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @author Michael
 */
@Repository("surveyDao")
public class SurveyDaoImpl extends HibernateDaoHelper implements SurveyDao {

    @Override
    public String save(Survey survey) {
        return (String) getSession().save(survey);
    }

    @Override
    public void update(Survey survey) {
        getSession().update(survey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Survey> query(SurveyBo bo) {
        Criteria criteria = getDefaultCriteria(bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(SurveyBo bo) {
        Criteria criteria = createRowCountsCriteria(Survey.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + Survey.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public Survey findById(String id) {
        Argument.isEmpty(id, "查询时ID不能为空!");
        return (Survey) getSession().get(Survey.class, id);
    }

    private Criteria getDefaultCriteria(SurveyBo bo) {
        Criteria criteria = createCriteria(Survey.class);
        initCriteria(criteria, bo);
        return criteria;
    }

    private void initCriteria(Criteria criteria, SurveyBo bo) {
        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null!");
        }
        CriteriaUtils.addCondition(criteria, bo);
    }

    @Override
    public void batchUpdateStatus(String newStatus, String[] ids) {
        if (ids == null || ids.length < 1) {
            return;
        }
        Assert.hasText(newStatus, "状态不能为空!");
        getSession().createQuery("update " + Survey.class.getName() + " s set s.status=? where s.id in(:ids)")
                .setParameter(0, newStatus)
                .setParameterList("ids", ids)
                .executeUpdate();
    }
}