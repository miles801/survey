package eccrm.knowledge.survey.bo;

import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;
import com.ycrl.core.hibernate.criteria.MatchModel;

import java.util.Date;

/**
 * @author Michael
 */
public class SurveyReportDetailBo implements BO {
    // 所属试卷
    @Condition
    private String surveyId;

    // 所属注册的试卷
    @Condition
    private String surveyReportId;

    @Condition
    private String subjectId;
    // 题目信息
    @Condition
    private String subjectType;

    // 答题人
    @Condition
    private String empId;


    // true表示已经完成了答题，false表示未完成
    @Condition(matchMode = MatchModel.NULL, target = "answerDate")
    private Date hasAnswer;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyReportId() {
        return surveyReportId;
    }

    public void setSurveyReportId(String surveyReportId) {
        this.surveyReportId = surveyReportId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Date getHasAnswer() {
        return hasAnswer;
    }

    public void setHasAnswer(Date hasAnswer) {
        this.hasAnswer = hasAnswer;
    }
}
