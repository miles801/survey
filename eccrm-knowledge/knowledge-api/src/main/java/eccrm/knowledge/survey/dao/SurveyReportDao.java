package eccrm.knowledge.survey.dao;

import eccrm.knowledge.survey.bo.SurveyReportBo;
import eccrm.knowledge.survey.domain.SurveyReport;

import java.util.List;

/**
 * @author Michael
 */
public interface SurveyReportDao {

    String save(SurveyReport surveyReport);

    void update(SurveyReport surveyReport);

    /**
     * 高级查询接口
     */
    List<SurveyReport> query(SurveyReportBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(SurveyReportBo bo);

    SurveyReport findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（一般是get或者load得到的对象）
     */
    void delete(SurveyReport surveyReport);
}
