package eccrm.base.im.vo;

import com.ycrl.base.common.CommonVo;

/**
 * @author Michael
 */
public class NewsReceiverVo extends CommonVo {
    private String receiverType;
    private String receiverTypeName;
    private String receiverId;      // 接收者ID
    private String receiverName;    // 接收者名称
    private String newsId;          // 新闻公告ID
    private String newsTitle;       // 新闻公告标题

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverTypeName() {
        return receiverTypeName;
    }

    public void setReceiverTypeName(String receiverTypeName) {
        this.receiverTypeName = receiverTypeName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
}
