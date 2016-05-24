package eccrm.knowledge.survey.bo;

import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;
import com.ycrl.core.hibernate.criteria.LikeModel;
import com.ycrl.core.hibernate.criteria.MatchModel;

import java.util.Date;

/**
 * @author Michael
 */
public class SurveyReportBo implements BO {

    // 问卷/试卷
    @Condition
    private String surveyId;

    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String surveyName;
    // 参与人
    @Condition
    private String empId;

    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String empName;


    // 试卷答题有效期
    @Condition(matchMode = MatchModel.GE, target = "effectDate")
    private Date effectDate1;
    @Condition(matchMode = MatchModel.LT, target = "effectDate")
    private Date effectDate2;

    // 注册时间
    @Condition(matchMode = MatchModel.GE, target = "registerDate")
    private Date registerDate1;
    @Condition(matchMode = MatchModel.LT, target = "registerDate")
    private Date registerDate2;

    // 是否通过注册:在注册通过时从题库中随机获取题目生成试卷
    @Condition
    private Boolean accept;


    // 是否完成答卷
    @Condition
    private Boolean finish;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Date getEffectDate1() {
        return effectDate1;
    }

    public void setEffectDate1(Date effectDate1) {
        this.effectDate1 = effectDate1;
    }

    public Date getEffectDate2() {
        return effectDate2;
    }

    public void setEffectDate2(Date effectDate2) {
        this.effectDate2 = effectDate2;
    }

    public Date getRegisterDate1() {
        return registerDate1;
    }

    public void setRegisterDate1(Date registerDate1) {
        this.registerDate1 = registerDate1;
    }

    public Date getRegisterDate2() {
        return registerDate2;
    }

    public void setRegisterDate2(Date registerDate2) {
        this.registerDate2 = registerDate2;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }
}
