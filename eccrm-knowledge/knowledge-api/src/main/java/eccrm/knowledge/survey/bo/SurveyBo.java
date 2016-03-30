package eccrm.knowledge.survey.bo;


import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;
import com.ycrl.core.hibernate.criteria.LikeModel;
import com.ycrl.core.hibernate.criteria.MatchModel;

import java.util.Date;

/**
 * @author Michael
 */
public class SurveyBo implements BO {
    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.ANYWHERE)
    private String name;

    @Condition
    private String code;

    @Condition
    private String status;

    @Condition(matchMode = MatchModel.GE)
    private Date startTime;

    @Condition(matchMode = MatchModel.LT)
    private Date endTime;

    // 如果值为true，表示还没有注册过答题（需要依赖当前登录人）
    private Boolean notRegister;

    public Boolean getNotRegister() {
        return notRegister;
    }

    public void setNotRegister(Boolean notRegister) {
        this.notRegister = notRegister;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
