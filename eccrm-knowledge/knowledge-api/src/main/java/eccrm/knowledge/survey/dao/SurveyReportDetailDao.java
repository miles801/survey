package eccrm.knowledge.survey.dao;

import eccrm.knowledge.survey.bo.SurveyReportDetailBo;
import eccrm.knowledge.survey.domain.Subject;
import eccrm.knowledge.survey.domain.SurveyReportDetail;

import java.util.List;

/**
 * @author Michael
 */
public interface SurveyReportDetailDao {

    String save(SurveyReportDetail surveyReportDetail);

    void update(SurveyReportDetail surveyReportDetail);

    /**
     * 高级查询接口
     */
    List<SurveyReportDetail> query(SurveyReportDetailBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(SurveyReportDetailBo bo);

    SurveyReportDetail findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(SurveyReportDetail surveyReportDetail);

    /**
     * 获取指定序号的题目
     *
     * @param surveyReportId 指定的试卷
     * @param seq            索引
     */
    SurveyReportDetail findBySeq(String surveyReportId, int seq);

    String getAnswer(String surveyReportId, String subjectId);


    /**
     * 根据试卷和题目ID获取具体的一道题
     *
     * @param surveyReportId 试卷ID
     * @param subjectId      题目ID
     * @return
     */
    SurveyReportDetail findBySubject(String surveyReportId, String subjectId);

    List<Subject> querySubjectById(String surveyReportId);
}
