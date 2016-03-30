package eccrm.knowledge.survey.vo;

import com.ycrl.base.common.CommonVo;

import java.util.Date;

/**
 * @author Michael
 */
public class SurveyReportDetailVo extends CommonVo {

    // 所属试卷
    private String surveyId;

    // 所属注册的试卷
    private String surveyReportId;

    // 题目信息
    private String subjectId;
    private String subjectName;
    private String subjectType;
    // 正确答案
    private String rightAnswer;

    // 答题人
    private String empId;
    private String empName;

    // 回答答案
    private String answer;

    private Date answerDate;

    // 是否正确
    private Boolean right;
    // 得分
    private Integer score;

    // 排序号
    private String sequenceNo;


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

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
}
