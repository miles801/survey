package eccrm.knowledge.survey.domain;

import com.ycrl.base.common.CommonDomain;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 试卷--用户对试卷进行作答
 * @author Michael
 */
public class SurveyAnswer extends CommonDomain {
    // 试卷
    @NotNull(message = "没有获取到试卷!")
    private String surveyId;
    private String surveyName;

    // 题目
    @NotNull(message = "没有获取到题目!")
    private String subjectId;
    private String subjectName;
    private String subjectType;

    // 答案
    @NotNull(message = "答案不能为空!")
    private String answer;

    // 答题时间
    @NotNull
    private Date answerDate;
    // 答题人
    @NotNull(message = "答题人ID不能为空!")
    private String answerUserId;
    @NotNull(message = "答题人名称不能为空!")
    private String answerUserName;
    // 答题人类型：员工、客户
    @NotNull(message = "答题人类型不能为空!")
    private String answerUserType;

    // 与业务ID进行绑定
    @NotNull(message = "试卷必须与指定的业务ID进行绑定!")
    private String businessId;

    // 批次ID
    private String batchId;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public String getAnswerUserId() {
        return answerUserId;
    }

    public void setAnswerUserId(String answerUserId) {
        this.answerUserId = answerUserId;
    }

    public String getAnswerUserName() {
        return answerUserName;
    }

    public void setAnswerUserName(String answerUserName) {
        this.answerUserName = answerUserName;
    }

    public String getAnswerUserType() {
        return answerUserType;
    }

    public void setAnswerUserType(String answerUserType) {
        this.answerUserType = answerUserType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

}
