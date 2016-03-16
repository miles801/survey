package eccrm.knowledge.survey.service;

import eccrm.knowledge.survey.domain.SubjectItem;
import eccrm.knowledge.survey.vo.SubjectItemVo;

import java.util.List;

/**
 * 题目选项
 * @author Michael
 */
public interface SubjectItemService {

    public void save(SubjectItem subjectItem);

    /**
     * 批量保存
     */
    public void save(List<SubjectItem> subjectItems);

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
    List<SubjectItemVo> queryBySubjectId(String subjectId);

}
