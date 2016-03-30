package eccrm.knowledge.survey.domain;

import com.ycrl.base.common.CommonDomain;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 成绩单
 *
 * @author Michael
 */
public class SurveyReport extends CommonDomain {
    // 问卷/试卷
    @NotNull
    private String surveyId;
    @NotNull
    private String surveyName;

    // 参与人
    @NotNull
    private String empId;
    @NotNull
    private String empName;

    // 试卷答题有效期
    private Date effectDate;

    // 注册时间
    private Date registerDate;

    // 是否通过注册:在注册通过时从题库中随机获取题目生成试卷
    private Boolean accept;

    // 通过时间
    private Date acceptDate;

    // 是否完成答卷
    private Boolean finish;

    // 开始考试时间
    private Date startDate;
    // 结束考试时间
    private Date endDate;

    // 持续时长（毫秒）
    private Long duration;
    // 满分
    private Integer totalScore;
    // 总得分
    private Integer score;

    // 总题数
    private Integer totalCounts;

    // 当前答题的位置（第几题）
    private Integer current;

    // 考试时所用的机器
    private String ip;

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

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

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
