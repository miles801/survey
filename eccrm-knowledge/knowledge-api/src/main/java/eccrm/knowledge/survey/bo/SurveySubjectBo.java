package eccrm.knowledge.survey.bo;

import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;

/**
 * @author Michael
 */
public class SurveySubjectBo implements BO {
    // 问卷ID
    @Condition
    private String surveyId;


    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyId() {
        return surveyId;
    }
}
