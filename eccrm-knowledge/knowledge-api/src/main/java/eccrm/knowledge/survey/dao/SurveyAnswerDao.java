package eccrm.knowledge.survey.dao;

import eccrm.knowledge.survey.bo.SurveyAnswerBo;
import eccrm.knowledge.survey.domain.SurveyAnswer;
import eccrm.knowledge.survey.vo.SurveyAnswerVo;
import java.util.List;

/**
 * @author Michael
 */
public interface SurveyAnswerDao {

    String save(SurveyAnswer surveyAnswer);

    void update(SurveyAnswer surveyAnswer);

    /**
     * 高级查询接口
     */
    List<SurveyAnswer> query(SurveyAnswerBo bo);

    /**
     * 查询总记录数
     */
    Long getTotal(SurveyAnswerBo bo);

    SurveyAnswer findById(String id);

    void deleteById(String id);

    /**
     * 根据实体对象删除
     * 必须保证该实体是存在的（�?��是get或�?load得到的对象）
     */
    void delete(SurveyAnswer surveyAnswer);
}
