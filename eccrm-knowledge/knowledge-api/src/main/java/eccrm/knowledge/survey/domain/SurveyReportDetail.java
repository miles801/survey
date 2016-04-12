package eccrm.knowledge.survey.domain;

import com.ycrl.base.common.CommonDomain;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 试卷明细：答题明细
 *
 * @author Michael
 */
public class SurveyReportDetail extends CommonDomain {

    // 所属试卷
    @NotNull
    private String surveyId;

    // 所属注册的试卷
    @NotNull
    private String surveyReportId;

    // 题目信息
    @NotNull
    private String subjectId;
    @NotNull
    private String subjectName;
    @NotNull
    private String subjectType;
    // 正确答案
//    @NotNull
    private String rightAnswer;

    // 答题人
    @NotNull
    private String empId;
    @NotNull
    private String empName;

    // 回答答案
    @Length(max = 1000, message = "目前答案过长，只支持1000个字符以内!")
    private String answer;

    // 答题时间
    private Date answerDate;

    // 是否正确
    private Boolean right;
    // 得分
    private Integer score;

    // 排序号
    private Integer sequenceNo;


    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

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

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
