package eccrm.base.datadic.bo;

import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;
import com.ycrl.core.hibernate.criteria.MatchModel;

/**
 * @author Michael
 */
public class DataDicBo implements BO {

    @Condition(matchMode = MatchModel.LIKE)
    private String name;

    @Condition(matchMode = MatchModel.LIKE)
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
