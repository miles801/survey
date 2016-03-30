package eccrm.knowledge.survey.dao;

import eccrm.knowledge.survey.domain.SubjectItem;

import java.util.List;

/**
 * @author Michael
 */
public interface SubjectItemDao {

    public String save(SubjectItem subjectItem);


    /**
     * 删除指定题目下的所有选项
     *
     * @param subjectId 题目id
     */
    public void deleteBySubjectId(String subjectId);

    /**
     * 查询指定题目下的所有选项
     *
     * @param subjectId 题目id
     */
    List<SubjectItem> queryBySubjectId(String subjectId);
}
