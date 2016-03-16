package eccrm.base.im.bo;

import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;
import com.ycrl.core.hibernate.criteria.MatchModel;

/**
 * @author Michael
 */
public class NewsReceiverBo implements BO {
    @Condition(matchMode = MatchModel.EQ)
    private String newsId;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
