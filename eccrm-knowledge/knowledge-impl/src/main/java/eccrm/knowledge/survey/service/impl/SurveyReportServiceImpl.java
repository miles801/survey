package eccrm.knowledge.survey.service.impl;

import com.michael.poi.adapter.AnnotationCfgAdapter;
import com.michael.poi.core.Handler;
import com.michael.poi.core.ImportEngine;
import com.michael.poi.imp.cfg.Configuration;
import com.ycrl.base.common.CommonStatus;
import com.ycrl.core.SystemContainer;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.context.SecurityContext;
import com.ycrl.core.hibernate.validator.ValidatorUtils;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.number.IntegerUtils;
import com.ycrl.utils.string.StringUtils;
import eccrm.base.attachment.AttachmentProvider;
import eccrm.base.attachment.utils.AttachmentHolder;
import eccrm.base.attachment.vo.AttachmentVo;
import eccrm.knowledge.survey.bo.SurveyReportBo;
import eccrm.knowledge.survey.dao.*;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.domain.SubjectItem;
import eccrm.knowledge.survey.domain.SurveyReport;
import eccrm.knowledge.survey.domain.SurveyReportDetail;
import eccrm.knowledge.survey.service.SurveyReportService;
import eccrm.knowledge.survey.vo.SubjectVo;
import eccrm.knowledge.survey.vo.SurveyReportVo;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
    public PageVo queryFinish(SurveyReportBo bo) {
        if (bo == null) {
            bo = new SurveyReportBo();
        }
        bo.setFinish(true);
        bo.setAccept(true);
        Long total = surveyReportDao.getTotal(bo);
        PageVo vo = new PageVo();
        vo.setTotal(total);
        if (total == null || total == 0) {
            return vo;
        }
        List<SurveyReport> data = surveyReportDao.query(bo);
        List<SurveyReportVo> vos = BeanWrapBuilder.newInstance().wrapList(data, SurveyReportVo.class);
        vo.setData(vos);
        return vo;
    }

    @Override
    public List<SurveyReportVo> queryUnfinish() {
        SurveyReportBo bo = new SurveyReportBo();
        bo.setFinish(false);
        bo.setEmpId(SecurityContext.getEmpId());
        bo.setAccept(true);
        List<SurveyReport> data = surveyReportDao.query(bo);
        return BeanWrapBuilder.newInstance().wrapList(data, SurveyReportVo.class);
    }

    @Override
    public SubjectVo getNextSubject(String id) {
        Assert.hasText(id, "获取题目失败!没有指定试卷!");
        final SurveyReport report = surveyReportDao.findById(id);
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
                        vo.setCurrentIndex(report.getCurrent());
                        vo.setCurrentScore(report.getScore());
                        List<SubjectItem> items = subjectItemDao.queryBySubjectId(o.getId());
                        vo.setItems(items);
                    }
                })
                .addProperties(new String[]{"answer", "status"})
                .exclude()
                .wrap(subject, SubjectVo.class);
    }

    @Override
    public SubjectVo getPrevSubject(String id, Integer index) {
        Assert.hasText(id, "获取题目失败!没有指定试卷!");
        final SurveyReport report = surveyReportDao.findById(id);
        Assert.notNull(report, "获取题目失败!该试卷不存在，请刷新后重试!");
        // 如果试卷已完成则直接返回null
        if (report.getCurrent() == null || report.getCurrent() == 0) {
            return null;
        }

        final SurveyReportDetail detail = surveyReportDetailDao.findBySeq(id, index);
        Assert.notNull(detail, "获取题目信息失败!没有获取到上一题!");
        String subjectId = detail.getSubjectId();
        Subject subject = subjectDao.findById(subjectId);
        Assert.notNull(subject, "获取上一题失败!试卷中的题目已经不存在!");
        final SubjectItemDao subjectItemDao = SystemContainer.getInstance().getBean(SubjectItemDao.class);
        return BeanWrapBuilder.newInstance()
                .setCallback(new BeanWrapCallback<Subject, SubjectVo>() {
                    @Override
                    public void doCallback(Subject o, SubjectVo vo) {
                        vo.setSurveyReportDetailId(detail.getId());
                        vo.setCurrentIndex(report.getCurrent());
                        vo.setCurrentScore(report.getScore());
                        List<SubjectItem> items = subjectItemDao.queryBySubjectId(o.getId());
                        // 不回显这个字段
                        for (SubjectItem item : items) {
                            item.setRight(null);
                        }
                        vo.setItems(items);
                    }
                })
                .addProperties(new String[]{"answer", "status"})
                .exclude()
                .wrap(subject, SubjectVo.class);
    }

    @Override
    public void importData(String[] attachmentIds, final String type) {
        Assert.notEmpty(attachmentIds, "导入题目失败!附件不存在，请刷新后重试!");
        Assert.hasText(type, "导入题目失败!请指定题型!");

        Logger logger = Logger.getLogger(SurveyReportServiceImpl.class);

        for (String id : attachmentIds) {
            AttachmentVo vo = AttachmentProvider.getInfo(id);
            Assert.notNull(vo, "附件已经不存在，请刷新后重试!");
            File file = AttachmentHolder.newInstance().getTempFile(id);
            logger.info("准备导入数据：" + file.getAbsolutePath());
            logger.info("初始化导入引擎....");
            long start = System.currentTimeMillis();

            Configuration configuration = new AnnotationCfgAdapter(SubjectDTO.class).parse();
            configuration.setStartRow(1);
            String newFilePath = file.getAbsolutePath() + vo.getFileName().substring(vo.getFileName().lastIndexOf(".")); //获取路径
            final Map<String, String> category = new HashMap<String, String>();   // 用于存放各个分类的名称和ID
            final SubjectCategoryDao categoryDao = SystemContainer.getInstance().getBean(SubjectCategoryDao.class);
            try {
                FileUtils.copyFile(file, new File(newFilePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            configuration.setPath(newFilePath);
            configuration.setHandler(new Handler<SubjectDTO>() {
                @Override
                public void execute(SubjectDTO dto) {
                    // 当标题和分类都是空的的时候，跳过
                    if (StringUtils.isEmpty(dto.getCategoryName()) && StringUtils.isEmpty(dto.getTitle())) {
                        return;
                    }

                    Assert.hasText(dto.getCategoryName(), "数据错误，分类不能为空!");
                    Assert.hasText(dto.getTitle(), "数据错误，题目不能为空!");
                    Assert.hasText(dto.getAnswer(), "数据错误，请指定正确答案!");
                    // 分类
                    String categoryId = category.get(dto.getCategoryName());
                    if (StringUtils.isEmpty(categoryId)) {
                        categoryId = categoryDao.findByName(dto.getCategoryName());
                        Assert.hasText(categoryId, String.format("数据错误!第%d行的分类[%s]不存在", 0, dto.getCategoryName()));
                        category.put(dto.getCategoryName(), categoryId);
                    }
                    dto.setCategoryId(categoryId);
                    dto.setSubjectType(type);

                    // 保存题目
                    Subject subject = new Subject();
                    if ("3".equals(type)) {
                        dto.setAnswer("对".equals(dto.getAnswer()) ? "true" : "false");
                    }
                    subject.setStatus(CommonStatus.ACTIVE.getValue());
                    org.springframework.beans.BeanUtils.copyProperties(dto, subject);
                    SubjectDao subjectDao = SystemContainer.getInstance().getBean(SubjectDao.class);
                    String subjectId = subjectDao.save(subject);

                    // 保存题目选项
                    SubjectItemDao subjectItemDao = SystemContainer.getInstance().getBean(SubjectItemDao.class);
                    if ("1".equals(type) || "2".equals(type)) {
                        Set<String> rightSet = new HashSet<String>();
                        String[] array = dto.getAnswer().split("\\D");
                        Collections.addAll(rightSet, array);
                        for (int i = 1; i < 10; i++) {
                            try {
                                String value = (String) dto.getClass().getMethod("getItem" + i).invoke(dto);
                                if (StringUtils.isNotEmpty(value)) {
                                    SubjectItem item = new SubjectItem();
                                    item.setName(value);
                                    if (rightSet.contains(i + "")) {
                                        item.setRight(true);
                                    }
                                    item.setSequenceNo(i);
                                    item.setSubjectId(subjectId);
                                    item.setSubjectName(dto.getTitle());
                                    subjectItemDao.save(item);
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            logger.info("开始导入数据....");
            ImportEngine engine = new ImportEngine(configuration);
            engine.execute();
            logger.info(String.format("导入数据成功,用时(%d)s....", (System.currentTimeMillis() - start) / 1000));
            new File(newFilePath).delete();
        }
    }


    @Override
    public List<SubjectVo> querySubjectWithItems(String surveyReportId) {
        Assert.hasText(surveyReportId, "获取题目失败!试卷ID不能为空!");
        List<Subject> subjects = surveyReportDetailDao.querySubjectById(surveyReportId);
        // 查询题目对应的选项
        return BeanWrapBuilder.newInstance()
                .setCallback(new BeanWrapCallback<Subject, SubjectVo>() {
                    @Override
                    public void doCallback(Subject o, SubjectVo vo) {
                        // 不将答案返回
                        vo.setAnswer(null);
                        // 如果是选择题则查询子选项
                        if (o.getSubjectType().equals("1") || o.getSubjectType().equals("2")) {
                            List<SubjectItem> items = SystemContainer.getInstance().getBean(SubjectItemDao.class).queryBySubjectId(o.getId());
                            for (SubjectItem item : items) {
                                item.setRight(null);
                            }
                            vo.setItems(items);
                        }
                    }
                })
                .wrapList(subjects, SubjectVo.class);
    }

    @Override
    public List<SurveyReportVo> queryAllOnlineIP() {
        List<SurveyReport> data = surveyReportDao.queryAllOnlineIP();
        return BeanWrapBuilder.newInstance().wrapList(data, SurveyReportVo.class);
    }

}
