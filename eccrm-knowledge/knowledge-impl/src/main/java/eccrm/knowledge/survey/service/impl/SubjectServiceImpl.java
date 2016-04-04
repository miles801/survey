package eccrm.knowledge.survey.service.impl;

import com.ycrl.base.common.CommonStatus;
import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.core.beans.BeanWrapCallback;
import com.ycrl.core.pager.PageVo;
import com.ycrl.utils.string.StringUtils;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.dao.SubjectDao;
import eccrm.knowledge.survey.dao.SubjectItemDao;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.domain.SubjectItem;
import eccrm.knowledge.survey.service.SubjectService;
import eccrm.knowledge.survey.vo.SubjectVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 题目
 */
@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {
    @Resource
    private SubjectDao subjectDao;

    @Resource
    private SubjectItemDao subjectItemDao;

    @Override
    public void deleteByIds(String[] ids) {
        if (ids == null || ids.length < 1) {
            return;
        }
        for (String id : ids) {
            Subject subject = subjectDao.findById(id);
            if (subject == null) {
                continue;
            }
            String status = subject.getStatus();
            if (CommonStatus.ACTIVE.getValue().equals(status)) {
                subject.setStatus(CommonStatus.CANCELED.getValue());
            } else if (CommonStatus.INACTIVE.getValue().equals(status)) {
                subjectDao.deleteById(id);
            }
        }
    }

    @Override
    public SubjectVo findById(String id) {
        Subject subject = subjectDao.findById(id);
        return BeanWrapBuilder.newInstance()
                .wrap(subject, SubjectVo.class);
    }

    @Override
    public PageVo pageQuery(SubjectBo bo) {
        PageVo vo = new PageVo();
        Long total = subjectDao.getTotal(bo);
        if (total == null || total == 0) return vo;
        vo.setTotal(total);
        List<Subject> data = subjectDao.query(bo);
        vo.setData(BeanWrapBuilder.newInstance()
                .setCallback(new BeanWrapCallback<Subject, SubjectVo>() {
                    @Override
                    public void doCallback(Subject subject, SubjectVo vo) {
                        vo.setStatusName(ParameterContainer.getInstance().getSystemName("SP_COMMON_STATE", subject.getStatus()));
                        vo.setSubjectTypeName(ParameterContainer.getInstance().getSystemName(Subject.TYPE, subject.getSubjectType()));
                    }
                }).wrapList(data, SubjectVo.class));
        return vo;
    }

    @Override
    public void save(Subject subject) {
        String id = subjectDao.save(subject);
        saveSubjectItems(subject, id);
        Assert.hasText(subject.getAnswer(), "新增题目失败!请填写该题目的正确答案!");
    }

    private void saveSubjectItems(Subject subject, String id) {
        List<SubjectItem> items = subject.getItems();
        if (items != null && !items.isEmpty()) {
            String rightAnswer = "";
            int i = 1;
            for (SubjectItem item : items) {
                if (StringUtils.isEmpty(item.getName())) {
                    continue;
                }
                item.setSubjectId(id);
                item.setSubjectName(subject.getTitle());
                item.setSequenceNo(i++);
                String answerId = subjectItemDao.save(item);
                if (item.getRight() != null && item.getRight()) {
                    rightAnswer += "," + answerId;
                }
            }
            Assert.isTrue(rightAnswer.length() > 0, "题目新增失败,请保证至少有一个选项是正确的!");
            subject.setAnswer(rightAnswer.substring(1));
        }
    }

    @Override
    public void update(Subject subject) {
        subjectDao.update(subject);
        // 删除历史关系
        String id = subject.getId();
        subjectItemDao.deleteBySubjectId(id);
        // 重建关系
        saveSubjectItems(subject, id);
        Assert.hasText(subject.getAnswer(), "新增题目失败!请填写该题目的正确答案!");
    }

    @Override
    public List<SubjectVo> exportData(SubjectBo bo) {
        Assert.notNull(bo, "导出数据失败!请指定题目类型等过滤条件!");
        Assert.hasText(bo.getSubjectType(), "导出数据失败!请指定要导出的题目类型!");
        bo.setStatus(CommonStatus.ACTIVE.getValue());
        List<Subject> data = subjectDao.query(bo);
        return BeanWrapBuilder.newInstance()
                .setCallback(new BeanWrapCallback<Subject, SubjectVo>() {
                    @Override
                    public void doCallback(Subject subject, SubjectVo vo) {
                        String subjectType = subject.getSubjectType();
                        vo.setSubjectTypeName(ParameterContainer.getInstance().getSystemName(Subject.TYPE, subjectType));
                        String title = org.apache.commons.lang3.StringUtils.removePattern(subject.getTitle(), "<[^>]+>");
                        vo.setTitle(title);
                        Assert.hasText(subjectType, "数据错误!题目[" + subject.getTitle() + "]的类型不可能为空!请与管理员联系!");
                        if ("1,2".contains(subjectType)) {
                            // 设置选项
                            List<SubjectItem> items = subjectItemDao.queryBySubjectId(subject.getId());
                            Assert.notEmpty(items, "数据错误!题目[" + subject.getTitle() + "]下没有设置题目选项，请与管理员联系!");
                            int size = items.size() + 1;
                            String answer = "";
                            for (int i = 1; i < size; i++) {
                                try {
                                    SubjectItem item = items.get(i - 1);
                                    vo.getClass().getMethod("setItem" + i, String.class).invoke(vo, item.getName());
                                    if (item.getRight() != null && item.getRight()) {
                                        answer += "," + i;
                                    }
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                            vo.setAnswer(answer.substring(1));
                        } else if ("3".equals(subjectType)) {
                            if ("true".equals(subject.getAnswer())) {
                                vo.setAnswer("对");
                            } else {
                                vo.setAnswer("错");
                            }
                        }
                    }
                }).wrapList(data, SubjectVo.class);
    }
}
