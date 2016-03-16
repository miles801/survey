package eccrm.base.im.vo;

import com.ycrl.base.common.CommonVo;

import java.util.Date;

/**
 * @author Michael
 */
public class NewsVo extends CommonVo {
    private String title;       // 标题
    private String summary;     // 摘要
    private String content;     // 内容
    private String category;    // 业务参数（公告分类）
    private String categoryName;

    private Date startTime;     // 生效时间
    private Date endTime;       // 失效时间
    private Boolean isTop;      // 是否顶置
    private Boolean recordRead; // 是否记录阅读
    private String publisherId;     // 发布人ID
    private String publisherName;   // 发布人名称
    private String publishState;    // 系统参数（发布状态）
    private String publishStateName;
    private Date publishTime;       // 发布时间

    private String receiverType;    // 接收者类型

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Boolean getRecordRead() {
        return recordRead;
    }

    public void setRecordRead(Boolean recordRead) {
        this.recordRead = recordRead;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublishState() {
        return publishState;
    }

    public void setPublishState(String publishState) {
        this.publishState = publishState;
    }

    public String getPublishStateName() {
        return publishStateName;
    }

    public void setPublishStateName(String publishStateName) {
        this.publishStateName = publishStateName;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }
}
