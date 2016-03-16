package eccrm.knowledge.survey.dao;

import eccrm.knowledge.survey.bo.SurveyBo;
import eccrm.knowledge.survey.domain.Survey;

import java.util.List;

/**
 * @author Michael
 */
public interface SurveyDao {

    String save(Survey survey);

    void update(Survey survey);

    /**
     * 高级查询接口
     */
    List<Survey> query(SurveyBo bo);

    Long getTotal(SurveyBo bo);

    Survey findById(String id);

    void deleteById(String id);

    /**
     * 批量更改状态
     *
     * @param newStatus 新的状态
     * @param ids       要更改状态的id集合
     */
    void batchUpdateStatus(String newStatus, String[] ids);
}
