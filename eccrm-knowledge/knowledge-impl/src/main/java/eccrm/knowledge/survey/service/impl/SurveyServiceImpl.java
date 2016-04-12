package eccrm.knowledge.survey.service.impl;

import com.ycrl.base.common.CommonStatus;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.hibernate.validator.ValidatorUtils;
import com.ycrl.core.pager.PageVo;
import com.ycrl.core.pager.Pager;
import com.ycrl.utils.number.IntegerUtils;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.base.parameter.vo.BusinessParamItemVo;
import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.bo.SurveyBo;
import eccrm.knowledge.survey.dao.*;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.domain.Survey;
import eccrm.knowledge.survey.domain.SurveyReport;
import eccrm.knowledge.survey.domain.SurveyReportDetail;
import eccrm.knowledge.survey.service.SurveyService;
import eccrm.knowledge.survey.vo.SurveyVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    @Resource
    private SurveyReportDao surveyReportDao;

    @Resource
    private SubjectDao subjectDao;

    @Resource
    private SurveyReportDetailDao surveyReportDetailDao;

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
        /*boolean hasSubject = surveySubjectDao.hasSubject(id);
        if (!hasSubject) {
            throw new RuntimeException("发布失败!该试卷还未设置题目!");
        }*/

        // 检查题库是否有足够的题


        survey.setStatus(Survey.STATUS_PUBLISHED);
    }


    @Override
    public List<SurveyVo> queryAllCanRegister() {
        SurveyBo bo = new SurveyBo();
        bo.setStatus(Survey.STATUS_PUBLISHED);
        Date now = new Date();
        bo.setStartTime(now);
        bo.setEndTime(now);
        bo.setNotRegister(true);
        List<Survey> surveys = surveyDao.query(bo);
        return BeanWrapBuilder.newInstance().wrapList(surveys, SurveyVo.class);
    }

    @Override
    public void register(String surveyId, String ip) {
        Logger logger = Logger.getLogger(this.getClass());
        logger.info(SecurityContext.getEmpName() + "--申请考试....");
        Assert.hasText(surveyId, "未知错误!申请考试时没有获取试卷ID!");
        Assert.hasText(ip, "未知错误!申请考试时没有获取到当前机器的IP!");
        ParameterContainer parameterContainer = ParameterContainer.getInstance();
        List<BusinessParamItemVo> vos = parameterContainer.getBusinessItems("IP");
        Assert.notEmpty(vos, "数据错误!没有获取到可以考试的机器的IP，请与管理员联系!");
        boolean flag = false;
        for (BusinessParamItemVo vo : vos) {
            if (vo.getValue().equals(ip)) {
                flag = true;
                break;
            }
        }
        Assert.isTrue(flag, "操作错误!申请考试的这台机器[" + ip + "]没有在系统中注册!请与管理员联系!");

        Survey survey = surveyDao.findById(surveyId);
        Assert.notNull(survey, "申请考试失败!试卷不存在，请刷新后重试!");
        Date now = new Date();
        long nowTime = now.getTime();
        Assert.isTrue(survey.getEndTime().getTime() > nowTime, "申请考试失败!试卷已过期，请刷新后重试!");
        Assert.isTrue(survey.getStartTime().getTime() < nowTime, "申请考试失败!试卷还未开放，请耐心等待!");
        Assert.isTrue(Survey.STATUS_PUBLISHED.equals(survey.getStatus()), "申请考试失败!试卷还未发布,请刷新后重试!");

        // 生成试卷
        SurveyReport report = new SurveyReport();
        report.setFinish(false);
        report.setAccept(true);
        report.setAcceptDate(now);
        report.setStartDate(now);   // 开始答题
        report.setRegisterDate(now);
        report.setEffectDate(survey.getEndTime());
        report.setEmpId(SecurityContext.getEmpId());
        report.setEmpName(SecurityContext.getEmpName());
        report.setIp(SecurityContext.getIp());
        report.setScore(0);
        report.setSurveyId(surveyId);
        report.setSurveyName(survey.getName());
        report.setTotalCounts(survey.getTotalSubjects());
        report.setTotalScore(survey.getTotalScore());
        report.setCurrent(0);
        report.setIp(ip);
        String reportId = surveyReportDao.save(report);

        // 往试卷中插入题目

        SubjectBo bo = new SubjectBo();
        bo.setCategoryId(survey.getCategoryId());   // 只选择指定类型下的题目
        bo.setRandom(true);
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        Integer danxuan = survey.getXzCounts();
        int index = 1;
        if (IntegerUtils.isBigger(danxuan, 0)) {
            bo.setSubjectType("1");// 单选
            Pager.setLimit(danxuan);
            List<Subject> subjects = subjectDao.query(bo);
            Assert.notEmpty(subjects, "试卷生成失败!没有足够的单选题，请与管理员联系或刷新后重试!");
            index = addReportDetail(surveyId, reportId, index, subjects, survey.getXzScore());
        }

        Integer duoxuan = survey.getDxCounts();
        if (IntegerUtils.isBigger(duoxuan, 0)) {
            bo.setSubjectType("2"); // 多选
            Pager.setLimit(duoxuan);
            List<Subject> subjects = subjectDao.query(bo);
            Assert.notEmpty(subjects, "试卷生成失败!没有足够的多选题，请与管理员联系或刷新后重试!");
            index = addReportDetail(surveyId, reportId, index, subjects, survey.getDxScore());
        }

        Integer panduan = survey.getPdCounts();
        if (IntegerUtils.isBigger(panduan, 0)) {
            bo.setSubjectType("3"); // 判断
            Pager.setLimit(panduan);
            List<Subject> subjects = subjectDao.query(bo);
            Assert.notEmpty(subjects, "试卷生成失败!没有足够的判断题，请与管理员联系或刷新后重试!");
            index = addReportDetail(surveyId, reportId, index, subjects, survey.getPdScore());
        }

        Integer tiankong = survey.getTkCounts();
        if (IntegerUtils.isBigger(tiankong, 0)) {
            bo.setSubjectType("4"); // 填空
            Pager.setLimit(tiankong);
            List<Subject> subjects = subjectDao.query(bo);
            Assert.notEmpty(subjects, "试卷生成失败!没有足够的填空题，请与管理员联系或刷新后重试!");
            index = addReportDetail(surveyId, reportId, index, subjects, survey.getXzScore());
        }

        logger.info(SecurityContext.getEmpName() + "--申请考试成功，共计" + index + "题....");

    }

    /**
     * 生成题目明细
     *
     * @param surveyId 试卷
     * @param reportId 所属注册的试卷
     * @param index    索引
     * @param subjects 题目
     * @return 更新后索引
     */
    private int addReportDetail(String surveyId, String reportId, int index, List<Subject> subjects, int score) {
        for (Subject subject : subjects) {
            SurveyReportDetail detail = new SurveyReportDetail();
            detail.setSurveyId(surveyId);
            detail.setSurveyReportId(reportId);
            detail.setSubjectId(subject.getId());
            detail.setSubjectName(subject.getTitle());
            detail.setSubjectType(subject.getSubjectType());
            detail.setScore(0);
            detail.setRightAnswer(subject.getAnswer());
            detail.setEmpId(SecurityContext.getEmpId());
            detail.setEmpName(SecurityContext.getEmpName());
            detail.setSequenceNo(index++);
            detail.setScore(score);
            surveyReportDetailDao.save(detail);
        }
        return index;
    }
}
