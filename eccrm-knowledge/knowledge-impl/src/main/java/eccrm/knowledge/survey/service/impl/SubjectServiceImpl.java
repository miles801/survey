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

import javax.annotation.Resource;
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
    }

    private void saveSubjectItems(Subject subject, String id) {
        List<SubjectItem> items = subject.getItems();
        if (items != null && !items.isEmpty()) {
            int i = 1;
            for (SubjectItem item : items) {
                if (StringUtils.isEmpty(item.getName())) {
                    continue;
                }
                item.setSubjectId(id);
                item.setSubjectName(subject.getTitle());
                item.setSequenceNo(i++);
                subjectItemDao.save(item);
            }
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
    }
}
