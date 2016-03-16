package eccrm.base.im.vo;

import com.ycrl.base.common.CommonVo;

import java.util.Date;

/**
 * @author Michael
 */
public class NewsRealReceiverVo extends CommonVo {
    private String newsId;          // 公告ID
    private String newsTitle;       // 公告标题
    private String category;        // （冗余）公告类型
    private String categoryName;
    private Date publishTime;       // （冗余）发布时间
    private String receiverId;      // 接收者ID
    private String receiverName;    // 接收者名称
    private Boolean hasRead;        // 是否已读
    private Date readTime;          // 阅读时间
    private Boolean hasReply;       // 是否回复
    private Date replyTime;         // 回复时间
    private Boolean hasStar;          // 是否收藏
    private Date starTime;          // 加入收藏的时间

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

    public Boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Boolean getHasReply() {
        return hasReply;
    }

    public void setHasReply(Boolean hasReply) {
        this.hasReply = hasReply;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Boolean getHasStar() {
        return hasStar;
    }

    public void setHasStar(Boolean hasStar) {
        this.hasStar = hasStar;
    }

    public Date getStarTime() {
        return starTime;
    }

    public void setStarTime(Date starTime) {
        this.starTime = starTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
