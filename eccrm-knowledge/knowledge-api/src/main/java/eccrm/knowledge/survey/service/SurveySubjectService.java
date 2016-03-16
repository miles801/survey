package eccrm.knowledge.survey.service;

import eccrm.knowledge.survey.bo.SubjectBo;
import eccrm.knowledge.survey.vo.SubjectVo;
import eccrm.knowledge.survey.vo.SurveySubjectVo;

import java.util.List;
import java.util.Map;

/**
 * @author Michael
 */
public interface SurveySubjectService {


    /**
     * 批量添加题目
     */
    void saveBatch(String surveyId, String[] subjectIds);

    /**
     * 更新序号
     * key为id，value为值
     */
    void updateSequenceNo(Map<String, Integer> sequences);

    /**
     * 查询问卷的题目,不进行分页
     *
     * @param surveyId 问卷ID
     * @param bo       高级查询条件
     */
    List<SurveySubjectVo> querySubject(String surveyId, SubjectBo bo);

    /**
     * 批量删除
     *
     * @param surveyId   问卷ID
     * @param subjectIds 题目id
     */
    void delete(String surveyId, String[] subjectIds);

    /**
     * 查询问卷的题目，并同时返回题目对应的选项
     *
     * @param surveyId 问卷ID
     */
    List<SubjectVo> querySubjectWithItems(String surveyId);

    /**
     * 检查指定的问卷是否设置了题目
     *
     * @param surveyId 问卷ID
     * @return true 设置了题目，false 未添加题目
     * @throws IllegalArgumentException surveyId不能为空
     */
    boolean hasSubject(String surveyId);


    /**
     * 设置下一题（直接跳转）
     *
     * @param id     id
     * @param nextId 下一题ID
     * @throws IllegalArgumentException id和nextId均不能为空
     */

    void setNext(String id, String nextId);

    /**
     * 设置下一题（条件跳转）
     *
     * @param id        id
     * @param nextId    下一题ID
     * @param condition 题目选项/值，用来确定题目的跳转
     * @throws IllegalArgumentException id、nextId以及condition均不能为空
     */
    void setNext(String id, String nextId, String condition);
}
