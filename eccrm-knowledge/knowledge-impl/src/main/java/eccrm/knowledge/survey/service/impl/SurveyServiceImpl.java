package eccrm.knowledge.survey.service.impl;

import com.ycrl.base.common.CommonStatus;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.hibernate.validator.ValidatorUtils;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.number.IntegerUtils;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.knowledge.survey.bo.SurveyBo;
import eccrm.knowledge.survey.dao.SurveyDao;
import eccrm.knowledge.survey.dao.SurveySubjectDao;
import eccrm.knowledge.survey.domain.Survey;
import eccrm.knowledge.survey.service.SurveyService;
import eccrm.knowledge.survey.vo.SurveyVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {
    @Resource
    private SurveyDao surveyDao;

    @Resource
    private SurveySubjectDao surveySubjectDao;

    @Override
    public String save(Survey survey) {
        init(survey);
        ValidatorUtils.validate(survey);
        String id = surveyDao.save(survey);
        return id;
    }

    /**
     * 设置试卷的分数、个数等信息
     */
    private void init(Survey survey) {
        survey.setXzTotalScore(IntegerUtils.multi(survey.getXzCounts(), survey.getXzScore()));
        survey.setDxTotalScore(IntegerUtils.multi(survey.getDxCounts(), survey.getDxScore()));
        survey.setPdTotalScore(IntegerUtils.multi(survey.getPdCounts(), survey.getPdScore()));
        survey.setTkTotalScore(IntegerUtils.multi(survey.getTkCounts(), survey.getTkScore()));
        survey.setJdTotalScore(IntegerUtils.multi(survey.getJdCounts(), survey.getJdScore()));
        survey.setTotalScore(IntegerUtils.add(survey.getXzTotalScore(), survey.getDxTotalScore(), survey.getPdTotalScore(), survey.getTkTotalScore(), survey.getJdTotalScore()));
        survey.setTotalSubjects(IntegerUtils.add(survey.getXzCounts(), survey.getDxCounts(), survey.getPdCounts(), survey.getTkCounts(), survey.getJdCounts()));
    }

    @Override
    public void update(Survey survey) {
        init(survey);
        ValidatorUtils.validate(survey);
        surveyDao.update(survey);
    }

    @Override
    public PageVo pageQuery(SurveyBo bo) {
        PageVo vo = new PageVo();
        Long total = surveyDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<Survey> surveyList = surveyDao.query(bo);
        List<SurveyVo> vos = BeanWrapBuilder.newInstance()
                .setCallback(new BeanWrapCallback<Survey, SurveyVo>() {
                    @Override
                    public void doCallback(Survey o, SurveyVo vo) {
                        vo.setStatusName(ParameterContainer.getInstance().getSystemName(Survey.STATUS, o.getStatus()));
                    }
                })
                .wrapList(surveyList, SurveyVo.class);
        vo.setData(vos);
        return vo;
    }

    @Override
    public List<SurveyVo> queryValid(SurveyBo bo) {
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        List<Survey> surveyList = surveyDao.query(bo);
        List<SurveyVo> vos = BeanWrapBuilder.newInstance()
                .wrapList(surveyList, SurveyVo.class);
        return vos;
    }

    @Override
    public SurveyVo findById(String id) {
        Survey survey = surveyDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(survey, SurveyVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length < 1) {
            return;
        }
        for (String id : ids) {
            Survey survey = surveyDao.findById(id);
            if (survey != null) {
                String status = survey.getStatus();
                if (CommonStatus.ACTIVE.getValue().equals(status)) {
                    survey.setStatus(CommonStatus.INACTIVE.getValue());
                } else if (CommonStatus.INACTIVE.getValue().equals(status)) {
                    surveyDao.deleteById(id);
                }
            }
        }
    }

    @Override
    public void publish(String id) {
        Survey survey = surveyDao.findById(id);
        // 检查状态
        String status = survey.getStatus();
        if (!Survey.STATUS_ACTIVE.equals(status)) {
            throw new RuntimeException("发布失败!非[启用]状态的试卷无法启用!");
        }
        // 检查有效期
        Date endDate = survey.getEndTime();
        if (new Date().after(endDate)) {
            throw new RuntimeException("发布失败!该试卷已经过期!");
        }
        // 检查是否包含题目
        boolean hasSubject = surveySubjectDao.hasSubject(id);
        if (!hasSubject) {
            throw new RuntimeException("发布失败!该试卷还未设置题目!");
        }


        survey.setStatus(Survey.STATUS_PUBLISHED);
    }
}