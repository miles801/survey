package eccrm.base.im.bo;

import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;
import com.ycrl.core.hibernate.criteria.MatchModel;

/**
 * @author Michael
 */
public class NewsRealReceiverBo implements BO {
    @Condition(matchMode = MatchModel.EQ)
    private String newsId;

    @Condition(matchMode = MatchModel.EQ)
    private String receiverId;

    @Condition(matchMode = MatchModel.EQ)
    private Boolean hasRead;

    @Condition(matchMode = MatchModel.EQ)
    private Boolean hasReply;

    @Condition(matchMode = MatchModel.EQ)
    private Boolean hasStar;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    public Boolean getHasReply() {
        return hasReply;
    }

    public void setHasReply(Boolean hasReply) {
        this.hasReply = hasReply;
    }

    public Boolean getHasStar() {
        return hasStar;
    }

    public void setHasStar(Boolean hasStar) {
        this.hasStar = hasStar;
    }
}
