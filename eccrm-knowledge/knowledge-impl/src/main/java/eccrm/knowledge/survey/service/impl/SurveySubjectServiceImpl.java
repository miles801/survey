package eccrm.knowledge.survey.service.impl;

import com.ycrl.core.beans.BeanWrapBuilder;
import com.ycrl.utils.string.StringUtils;
import eccrm.base.parameter.service.ParameterContainer;
import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.bo.SurveySubjectBo;
import eccrm.knowledge.survey.dao.SubjectDao;
import eccrm.knowledge.survey.dao.SubjectItemDao;
import eccrm.knowledge.survey.dao.SurveySubjectDao;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.domain.SurveySubject;
import eccrm.knowledge.survey.service.SurveySubjectService;
import eccrm.knowledge.survey.vo.SurveySubjectVo;
import eccrm.utils.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Michael
 */
@Service("surveySubjectService")
public class SurveySubjectServiceImpl implements SurveySubjectService {
    @Resource
    private SurveySubjectDao surveySubjectDao;

    @Resource
    private SubjectDao subjectDao;

    @Resource
    private SubjectItemDao subjectItemDao;

    @Override
    public void saveBatch(String surveyId, String[] subjectIds) {
        Assert.hasText(surveyId, "试卷ID不能为空!");
        Assert.notEmpty(subjectIds);
        // 去掉重复添加的题目
        List<String> subjectIdList = surveySubjectDao.exists(surveyId, subjectIds);   // 查询已经存在的id集合
        List<String> ids = new ArrayList<String>();
        for (String id : subjectIds) {
            if (subjectIdList != null && subjectIdList.contains(id)) {
                continue;
            }
            ids.add(id);
        }
        // 查询题目集合
        List<Subject> subjects = subjectDao.queryByIds(ArrayUtils.listToArray(ids), null);

        // 保存关联关系
        if (subjects != null && !subjects.isEmpty()) {
            for (Subject subject : subjects) {
                SurveySubject foo = new SurveySubject();
                foo.setCategoryId(subject.getCategoryId());
                foo.setCategoryName(subject.getCategoryName());
                foo.setSubjectId(subject.getId());
                foo.setSurveyId(surveyId);
                foo.setSubjectName(subject.getTitle());
                foo.setSubjectType(subject.getSubjectType());
                foo.setSubjectTypeName(ParameterContainer.getInstance().getSystemName(Subject.TYPE, subject.getSubjectType()));
                surveySubjectDao.save(foo);
            }
        }

    }

    @Override
    public void delete(String surveyId, String[] subjectIds) {
        Assert.hasText(surveyId);
        Assert.notEmpty(subjectIds);
        surveySubjectDao.deleteBatch(surveyId, subjectIds);
    }

    @Override
    public void updateSequenceNo(Map<String, Integer> sequences) {
        for (Map.Entry<String, Integer> entry : sequences.entrySet()) {
            Integer value = entry.getValue();
            if (value == null) {
                continue;
            }
            String id = entry.getKey();
            SurveySubject ss = surveySubjectDao.findById(id);
            if (ss != null) {
                ss.setSequenceNo(value);
            }
        }
    }

    @Override
    public List<SurveySubjectVo> querySubject(final String surveyId, SubjectBo bo) {
        Assert.hasText(surveyId, "试卷ID不能为空!");
        List<SurveySubject> surveySubjects = surveySubjectDao.queryBySurveyId(surveyId);
        if (surveySubjects == null || surveySubjects.isEmpty()) {
            return null;
        }
        return BeanWrapBuilder.newInstance()
                .wrapList(surveySubjects, SurveySubjectVo.class);
    }

    @Override
    public boolean hasSubject(String surveyId) {
        Assert.hasText(surveyId, "试卷:查询试卷的是否设置了题目是,必须指定试卷ID!");
        SurveySubjectBo bo = new SurveySubjectBo();
        bo.setSurveyId(surveyId);
        Long count = surveySubjectDao.getTotal(bo);
        return count != null && count > 0;
    }

    @Override
    public void setNext(String id, String nextId) {
        setNextSubject(id, nextId, null);
    }

    @Override
    public void setNext(String id, String nextId, String condition) {
        Assert.hasText(condition, "试卷:设置下一题,必须指定跳转条件的值!");
        setNextSubject(id, nextId, condition);
    }

    /**
     * 设置下一题
     *
     * @param id        ID
     * @param nextId    下一题目ID
     * @param condition 条件，可为空
     */
    private void setNextSubject(String id, String nextId, String condition) {
        Assert.hasText(id, "试卷:设置下一题,必须指定要设置的ID!");
        Assert.hasText(nextId, "试卷:设置下一题,必须指定下一题ID!");
        SurveySubject surveySubject = surveySubjectDao.findById(id);
        if (surveySubject == null) {
            throw new EntityNotFoundException(SurveySubject.class.getName() + ":" + id);
        }

        String subjectId = surveySubject.getSubjectId();
        if (subjectId.equals(nextId)) {
            throw new RuntimeException("试卷:设置下一题,当前题的下一题不能是自己!");
        }

        Subject subject = subjectDao.findById(nextId);
        if (subject == null) {
            throw new EntityNotFoundException(Subject.class.getName() + ":" + nextId);
        }

        surveySubject.setNextSubjectId(nextId);
        surveySubject.setNextSubjectName(subject.getTitle());
        if (StringUtils.isNotEmpty(condition)) {
            surveySubject.setSubjectType(SurveySubject.ROUTE_CONDITION);
            surveySubject.setCondition(condition);
        } else {
            surveySubject.setSubjectType(SurveySubject.ROUTE_DIRECT);
        }
    }
}
