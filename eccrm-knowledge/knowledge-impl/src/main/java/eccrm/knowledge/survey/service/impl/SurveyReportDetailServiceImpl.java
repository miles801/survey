package eccrm.knowledge.survey.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.number.IntegerUtils;
import eccrm.knowledge.survey.bo.SurveyReportDetailBo;
import eccrm.knowledge.survey.dao.SurveyReportDao;
import eccrm.knowledge.survey.dao.SurveyReportDetailDao;
import eccrm.knowledge.survey.domain.SurveyReport;
import eccrm.knowledge.survey.domain.SurveyReportDetail;
import eccrm.knowledge.survey.service.SurveyReportDetailService;
import eccrm.knowledge.survey.service.SurveyResult;
import eccrm.knowledge.survey.vo.SurveyReportDetailVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
@Service("surveyReportDetailService")
public class SurveyReportDetailServiceImpl implements SurveyReportDetailService {
    @Resource
    private SurveyReportDetailDao surveyReportDetailDao;

    @Resource
    private SurveyReportDao surveyReportDao;

    @Override
    public String save(SurveyReportDetail surveyReportDetail) {
        String id = surveyReportDetailDao.save(surveyReportDetail);
        return id;
    }

    @Override
    public void update(SurveyReportDetail surveyReportDetail) {
        surveyReportDetailDao.update(surveyReportDetail);
    }

    @Override
    public PageVo pageQuery(SurveyReportDetailBo bo) {
        PageVo vo = new PageVo();
        Long total = surveyReportDetailDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<SurveyReportDetail> surveyReportDetailList = surveyReportDetailDao.query(bo);
        List<SurveyReportDetailVo> vos = BeanWrapBuilder.newInstance()
                .wrapList(surveyReportDetailList, SurveyReportDetailVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public SurveyReportDetailVo findById(String id) {
        SurveyReportDetail surveyReportDetail = surveyReportDetailDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(surveyReportDetail, SurveyReportDetailVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            surveyReportDetailDao.deleteById(id);
        }
    }

    @Override
    public boolean answer(String id, String answer) {
        Assert.hasText(id, "答题失败!没有获取答题信息!");
        Assert.hasText(answer, "答题失败!没有指定答案!");
        SurveyReportDetail detail = surveyReportDetailDao.findById(id);
        Assert.notNull(detail, "答题失败!没有获取到题目信息!");
        detail.setAnswer(answer);
        Date now = new Date();
        detail.setAnswerDate(now);
        boolean isRight = detail.getRightAnswer().equals(answer);
        detail.setRight(isRight);

        SurveyReport report = surveyReportDao.findById(detail.getSurveyReportId());
        Assert.notNull(report, "答题失败!没有获取到试卷信息!");
        int seq = detail.getSequenceNo();
        Assert.isTrue(seq == report.getCurrent() + 1, "答题失败!当前题目的索引与实际答题的索引不符!");
        report.setCurrent(seq);

        // 计算到目前为止的个人总分
        if (isRight) {
            report.setScore(IntegerUtils.add(report.getScore(), detail.getScore()));
        }

        // 是否为第一题
        if (seq == 1) {
            report.setStartDate(now);
        }

        // 是否为最后一题
        if (seq == report.getTotalCounts()) {
            report.setFinish(true);
            report.setEndDate(now);
            report.setDuration(now.getTime() - report.getStartDate().getTime());
        }
        return isRight;
    }

    @Override
    public SurveyResult answer(SurveyReportDetail[] details) {
        Assert.notEmpty(details, "答题失败!没有获得答题结果!");
        SurveyResult result = new SurveyResult();
        Date now = new Date();
        String surveyReportId = details[0].getSurveyReportId();
        Assert.hasText(surveyReportId, "答题失败!没有获得试卷ID!");
        SurveyReport surveyReport = surveyReportDao.findById(surveyReportId);
        Assert.notNull(surveyReport, "答题失败!试卷不存在!请刷新后重试!");
        Integer score = 0;  // 答题得分
        List<SurveyReportDetail> errors = new ArrayList<SurveyReportDetail>();  // 错误题目
        for (SurveyReportDetail detail : details) {
            String subjectId = detail.getSubjectId();
            Assert.notNull(subjectId, "答题失败!题目ID不能为空!");
            SurveyReportDetail srd = surveyReportDetailDao.findBySubject(surveyReportId, subjectId);
            srd.setAnswer(detail.getAnswer());
            srd.setAnswerDate(now);
            srd.setRight(detail.getAnswer().equals(srd.getRightAnswer()));
            if (srd.getRight()) {
                score += srd.getScore();
            } else {
                errors.add(srd);
            }
        }
        // 覆写试卷结果
        surveyReport.setScore(score);   // 分数
        surveyReport.setFinish(true);
        surveyReport.setEndDate(now);

        long duration = now.getTime() - surveyReport.getStartDate().getTime();
        surveyReport.setDuration(duration);
        // 回显结果
        result.setTotalScore(surveyReport.getTotalScore());
        result.setScore(score);
        result.setErrors(errors);
        result.setDuration(duration);

        return result;
    }

    @Override
    public String getAnswer(String surveyReportId, String subjectId) {

        return surveyReportDetailDao.getAnswer(surveyReportId, subjectId);
    }
}
