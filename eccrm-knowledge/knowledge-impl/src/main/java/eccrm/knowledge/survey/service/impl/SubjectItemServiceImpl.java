package eccrm.knowledge.survey.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.utils.string.StringUtils;
import eccrm.knowledge.survey.dao.SubjectItemDao;
import eccrm.knowledge.survey.domain.SubjectItem;
import eccrm.knowledge.survey.service.SubjectItemService;
import eccrm.knowledge.survey.vo.SubjectItemVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Michael
 */
@Service("subjectItemService")
public class SubjectItemServiceImpl implements SubjectItemService {
    @Resource
    private SubjectItemDao subjectItemDao;

    @Override
    public void save(List<SubjectItem> subjectItems) {
        if (subjectItems == null || subjectItems.isEmpty()) {
            return;
        }
        for (SubjectItem item : subjectItems) {
            save(item);
        }
    }

    @Override
    public void save(SubjectItem subjectItem) {
        // 排除空选项
        if (StringUtils.isEmpty(subjectItem.getName())) {
            return;
        }
        subjectItemDao.save(subjectItem);
    }

    @Override
    public List<SubjectItemVo> queryBySubjectId(String subjectId) {
        List<SubjectItem> data = subjectItemDao.queryBySubjectId(subjectId);
        return BeanWrapBuilder.newInstance()
                .wrapList(data, SubjectItemVo.class);
    }

    @Override
    public void deleteBySubjectId(String subjectId) {
        subjectItemDao.deleteBySubjectId(subjectId);
    }
}
