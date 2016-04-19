package eccrm.knowledge.survey.dao;

import eccrm.knowledge.survey.bo.SurveySubjectBo;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.domain.SurveySubject;

import java.util.List;

/**
 * @author Michael
 */
public interface SurveySubjectDao {

    String save(SurveySubject surveySubject);

    void update(SurveySubject surveySubject);

    /**
     * 高级查询接口
     */
    List<SurveySubject> query(SurveySubjectBo bo);

    /**
     * 查询指定试卷下的所有题目的id
     *
     * @param surveyId 试卷id
     * @return 题目id集合
     */
    List<String> querySubjectIds(String surveyId);

    /**
     * 查询指定试卷下的所有的题目
     *
     * @param surveyId 试卷ID
     */
    List<SurveySubject> queryBySurveyId(String surveyId);

    /**
     * 查询总记录数
     */
    Long getTotal(SurveySubjectBo bo);

    SurveySubject findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(SurveySubject surveySubject);

    /**
     * 批量删除
     */
    void deleteBatch(String[] ids);

    /**
     * 批量删除
     *
     * @param surveyId   试卷ID
     * @param subjectIds 题目ID
     */
    void deleteBatch(String surveyId, String[] subjectIds);

    /**
     * 查询出指定试卷中已经存在的试题id
     *
     * @param surveyId   试卷ID
     * @param subjectIds 试题ID
     */
    List<String> exists(String surveyId, String[] subjectIds);

    /**
     * 查询指定试卷是否设置了题目
     *
     * @param surveyId 试卷ID
     * @return true:设置了题目，false:未设置题目
     * @throws IllegalArgumentException surveyId不能为空
     */
    boolean hasSubject(String surveyId);

    /**
     * 重新指定指定试卷的第一题，会清空之前的第一题的设置
     *
     * @param surveyId  试卷ID
     * @param subjectId 题目ID
     * @throws IllegalArgumentException surveyId和subjectId均不能为空
     */
    void resetFirstSubject(String surveyId, String subjectId);

    /**
     * 乱序获取指定试卷的题目，按照题型进行排序
     *
     * @param surveyId 试卷ID
     */
    List<Subject> randomQuery(String surveyId);

}
