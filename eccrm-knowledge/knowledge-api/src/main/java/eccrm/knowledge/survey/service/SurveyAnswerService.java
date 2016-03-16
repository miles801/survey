package eccrm.knowledge.survey.service;

import com.ycrl.core.pager.PageVo;
import eccrm.knowledge.survey.bo.SurveyAnswerBo;
import eccrm.knowledge.survey.domain.SurveyAnswer;
import eccrm.knowledge.survey.vo.SurveyAnswerVo;

import java.util.List;

/**
 * @author Michael
 */
public interface SurveyAnswerService {

    /**
     * 保存
     */
    String save(SurveyAnswer surveyAnswer);


    /**
     * 批量保存
     */
    void batchSave(List<SurveyAnswer> surveyAnswers);

    /**
     * 分页查询
     */
    PageVo pageQuery(SurveyAnswerBo bo);

    /**
     * 根据ID查询对象的信�?
     */
    SurveyAnswerVo findById(String id);

    /**
     * 批量删除
     */
    void deleteByIds(String[] ids);

    /**
     * 指定问卷是否已经被回答
     * @param surveyId   问卷ID
     * @param businessId 绑定的业务ID
     * @param userId     答题人ID
     * @return true已经回答，false未回答
     */
    boolean hasAnswered(String surveyId, String businessId, String userId);

    /**
     * 查询指定问卷，指定用户的回答情况
     * @param surveyId   问卷ID
     * @param businessId 业务ID
     * @param userId     答题人ID
     * @return 答题情况
     */
    List<SurveyAnswerVo> queryAnswer(String surveyId, String businessId, String userId);

}
