package eccrm.knowledge.survey.service;

import eccrm.knowledge.survey.domain.SurveyReportDetail;

import java.util.List;

/**
 * @author Michael
 */
public class SurveyResult {
    // 得分
    private Integer score;

    // 总分
    private Integer totalScore;

    // 总用时
    private Long duration;

    // 错误题目
    private List<SurveyReportDetail> errors;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<SurveyReportDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<SurveyReportDetail> errors) {
        this.errors = errors;
    }
}
