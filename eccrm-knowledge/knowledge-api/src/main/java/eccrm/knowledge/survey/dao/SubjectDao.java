package eccrm.knowledge.survey.dao;

import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.domain.Subject;

import java.util.List;

/**
 * 调查试卷--题目
 */
public interface SubjectDao {

    String save(Subject subject);

    void update(Subject subject);

    List<Subject> query(SubjectBo bo);

    Long getTotal(SubjectBo bo);

    Subject findById(String id);

    int deleteById(String id);

    /**
     * 根据id批量查询
     */
    List<Subject> queryByIds(String[] ids, SubjectBo bo);

}
