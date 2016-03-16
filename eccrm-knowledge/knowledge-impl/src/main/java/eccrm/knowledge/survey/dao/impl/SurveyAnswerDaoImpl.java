package eccrm.knowledge.survey.dao.impl;

import eccrm.knowledge.survey.bo.SurveyAnswerBo;
import eccrm.knowledge.survey.domain.SurveyAnswer;
import eccrm.knowledge.survey.dao.SurveyAnswerDao;
import com.ycrl.core.HibernateDaoHelper;
import org.hibernate.criterion.Example;
import com.ycrl.core.hibernate.criteria.CriteriaUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import java.util.List;


/**
 * @author Michael
 */
@Repository("surveyAnswerDao")
public class SurveyAnswerDaoImpl extends HibernateDaoHelper implements SurveyAnswerDao {

    @Override
    public String save(SurveyAnswer surveyAnswer) {
        return (String) getSession().save(surveyAnswer);
    }

    @Override
    public void update(SurveyAnswer surveyAnswer) {
        getSession().update(surveyAnswer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SurveyAnswer> query(SurveyAnswerBo bo) {
        Criteria criteria = createCriteria(SurveyAnswer.class);
        initCriteria(criteria, bo);
        return criteria.list();
    }

    @Override
    public Long getTotal(SurveyAnswerBo bo) {
        Criteria criteria = createRowCountsCriteria(SurveyAnswer.class);
        initCriteria(criteria, bo);
        return (Long) criteria.uniqueResult();
    }


    @Override
    public void deleteById(String id) {
        getSession().createQuery("delete from " + SurveyAnswer.class.getName() + " e where e.id=?")
                .setParameter(0, id)
                .executeUpdate();
    }

    @Override
    public void delete(SurveyAnswer surveyAnswer) {
        Assert.notNull(surveyAnswer, "要删除的对象不能为空!");
        getSession().delete(surveyAnswer);
    }

    @Override
    public SurveyAnswer findById(String id) {
        Assert.hasText(id, "ID不能为空!");
        return (SurveyAnswer) getSession().get(SurveyAnswer.class, id);
    }

    private void initCriteria(Criteria criteria, SurveyAnswerBo bo) {
        Assert.notNull(criteria, "criteria must not be null!");
        CriteriaUtils.addCondition(criteria, bo);
    }

}