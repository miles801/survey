package eccrm.knowledge.survey.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.hibernate.validator.ValidatorUtils;
import com.ycrl.core.pager.PageVo;
import eccrm.knowledge.survey.bo.SurveyAnswerBo;
import eccrm.knowledge.survey.dao.SurveyAnswerDao;
import eccrm.knowledge.survey.domain.SurveyAnswer;
import eccrm.knowledge.survey.service.SurveyAnswerService;
import eccrm.knowledge.survey.vo.SurveyAnswerVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
@Service("surveyAnswerService")
public class SurveyAnswerServiceImpl implements SurveyAnswerService {
    @Resource
    private SurveyAnswerDao surveyAnswerDao;

    @Override
    public String save(SurveyAnswer surveyAnswer) {
        surveyAnswer.setAnswerDate(new Date());
        ValidatorUtils.validate(surveyAnswer);
        String id = surveyAnswerDao.save(surveyAnswer);
        return id;
    }

    @Override
    public void batchSave(List<SurveyAnswer> surveyAnswers) {
        for (SurveyAnswer surveyAnswer : surveyAnswers) {
            save(surveyAnswer);
        }
    }

    @Override
    public PageVo pageQuery(SurveyAnswerBo bo) {
        PageVo vo = new PageVo();
        Long total = surveyAnswerDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<SurveyAnswer> surveyAnswerList = surveyAnswerDao.query(bo);
        List<SurveyAnswerVo> vos = BeanWrapBuilder.newInstance()
                .wrapList(surveyAnswerList, SurveyAnswerVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public SurveyAnswerVo findById(String id) {
        SurveyAnswer surveyAnswer = surveyAnswerDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(surveyAnswer, SurveyAnswerVo.class);
    }

    @Override
    public boolean hasAnswered(String surveyId, String businessId, String userId) {
        Assert.hasText(surveyId, "缺少参数：问卷ID!");
        Assert.hasText(businessId, "缺少参数：业务ID!");
        Assert.hasText(userId, "缺少参数：答题人ID!");
        SurveyAnswerBo bo = new SurveyAnswerBo();
        bo.setSurveyId(surveyId);
        bo.setBusinessId(businessId);
        bo.setAnswerUserId(userId);
        Long total = surveyAnswerDao.getTotal(bo);
        return total != null && total > 0;
    }

    @Override
    public List<SurveyAnswerVo> queryAnswer(String surveyId, String businessId, String userId) {
        Assert.hasText(surveyId, "缺少参数：问卷ID!");
        Assert.hasText(businessId, "缺少参数：业务ID!");
        Assert.hasText(userId, "缺少参数：答题人ID!");
        SurveyAnswerBo bo = new SurveyAnswerBo();
        bo.setSurveyId(surveyId);
        bo.setBusinessId(businessId);
        bo.setAnswerUserId(userId);
        List<SurveyAnswer> surveyAnswers = surveyAnswerDao.query(bo);
        return BeanWrapBuilder.newInstance().wrapList(surveyAnswers, SurveyAnswerVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            surveyAnswerDao.deleteById(id);
        }
    }


}
