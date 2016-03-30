package eccrm.knowledge.survey.service.impl;

import com.ycrl.core.SystemContainer;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.hibernate.validator.ValidatorUtils;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.number.IntegerUtils;
import eccrm.knowledge.survey.bo.SurveyReportBo;
import eccrm.knowledge.survey.dao.SubjectDao;
import eccrm.knowledge.survey.dao.SubjectItemDao;
import eccrm.knowledge.survey.dao.SurveyReportDao;
import eccrm.knowledge.survey.dao.SurveyReportDetailDao;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.domain.SubjectItem;
import eccrm.knowledge.survey.domain.SurveyReport;
import eccrm.knowledge.survey.domain.SurveyReportDetail;
import eccrm.knowledge.survey.service.SurveyReportService;
import eccrm.knowledge.survey.vo.SubjectVo;
import eccrm.knowledge.survey.vo.SurveyReportVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Michael
 */
@Service("surveyReportService")
public class SurveyReportServiceImpl implements SurveyReportService {
    @Resource
    private SurveyReportDao surveyReportDao;

    @Resource
    private SubjectDao subjectDao;

    @Resource
    private SurveyReportDetailDao surveyReportDetailDao;

    @Override
    public String save(SurveyReport surveyReport) {
        surveyReport.setFinish(false);
        surveyReport.setRegisterDate(new Date());
        // 不需要审核，直接通过
        surveyReport.setAccept(true);
        surveyReport.setAcceptDate(new Date());
        ValidatorUtils.validate(surveyReport);
        String id = surveyReportDao.save(surveyReport);
        return id;
    }

    @Override
    public void update(SurveyReport surveyReport) {
        surveyReportDao.update(surveyReport);
    }

    @Override
    public PageVo pageQuery(SurveyReportBo bo) {
        PageVo vo = new PageVo();
        Long total = surveyReportDao.getTotal(bo);
        vo.setTotal(total);
        if (total == null || total == 0) return vo;
        List<SurveyReport> surveyReportList = surveyReportDao.query(bo);
        List<SurveyReportVo> vos = BeanWrapBuilder.newInstance()
                .wrapList(surveyReportList, SurveyReportVo.class);
        vo.setData(vos);
        return vo;
    }


    @Override
    public SurveyReportVo findById(String id) {
        SurveyReport surveyReport = surveyReportDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(surveyReport, SurveyReportVo.class);
    }

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length == 0) return;
        for (String id : ids) {
            surveyReportDao.deleteById(id);
        }
    }

    @Override
    public void start(SurveyReport surveyReport) {
        Assert.isTrue(false, "答题功能暂未实现!");
    }

    @Override
    public List<SurveyReportVo> queryFinish() {
        SurveyReportBo bo = new SurveyReportBo();
        bo.setFinish(true);
        bo.setEmpId(SecurityContext.getEmpId());
        bo.setAccept(true);
        List<SurveyReport> data = surveyReportDao.query(bo);
        return BeanWrapBuilder.newInstance().wrapList(data, SurveyReportVo.class);
    }

    @Override
    public List<SurveyReportVo> queryUnfinish() {
        SurveyReportBo bo = new SurveyReportBo();
        bo.setFinish(false);
        bo.setEmpId(SecurityContext.getEmpId());
        bo.setAccept(true);
        Date now = new Date();
//        bo.setEffectDate1(now);
//        bo.setEffectDate2(now);
        List<SurveyReport> data = surveyReportDao.query(bo);
        return BeanWrapBuilder.newInstance().wrapList(data, SurveyReportVo.class);
    }

    @Override
    public SubjectVo getNextSubject(String id) {
        Assert.hasText(id, "获取题目失败!没有指定试卷!");
        SurveyReport report = surveyReportDao.findById(id);
        Assert.notNull(report, "获取题目失败!该试卷不存在，请刷新后重试!");
        // 如果试卷已完成则直接返回null
        if (report.getFinish() || IntegerUtils.nullEqual(report.getCurrent(), report.getTotalCounts())) {
            return null;
        }
        final SurveyReportDetail detail = surveyReportDetailDao.findBySeq(id, IntegerUtils.add(report.getCurrent(), 1));
        Assert.notNull(detail, "获取题目信息失败!没有获取到下一题!");
        String subjectId = detail.getSubjectId();
        Subject subject = subjectDao.findById(subjectId);
        Assert.notNull(subject, "获取下一题失败!试卷中的题目已经不存在!");
        final SubjectItemDao subjectItemDao = SystemContainer.getInstance().getBean(SubjectItemDao.class);
        return BeanWrapBuilder.newInstance()
                .setCallback(new BeanWrapCallback<Subject, SubjectVo>() {
                    @Override
                    public void doCallback(Subject o, SubjectVo vo) {
                        vo.setSurveyReportDetailId(detail.getId());
                        List<SubjectItem> items = subjectItemDao.queryBySubjectId(o.getId());
                        vo.setItems(items);
                    }
                })
                .addProperties(new String[]{"answer", "status"})
                .exclude()
                .wrap(subject, SubjectVo.class);
    }
}
