package eccrm.base.log.bo;

import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;
import com.ycrl.core.hibernate.criteria.LikeModel;
import com.ycrl.core.hibernate.criteria.MatchModel;

import java.util.Date;

/**
 * @author Michael
 */
public class OperateLogBo implements BO {

    @Condition(target = "creatorName")
    private String operator;

    @Condition(target = "description", likeMode = LikeModel.ANYWHERE, matchMode = MatchModel.LIKE)
    private String description;

    @Condition(target = "createdDatetime", matchMode = MatchModel.GE)
    private Date startDate;

    @Condition(target = "createdDatetime", matchMode = MatchModel.LT)
    private Date endDate;

    @Condition(target = "operateType")
    private String operateType;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
