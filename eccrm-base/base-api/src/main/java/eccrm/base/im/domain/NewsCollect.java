package eccrm.base.im.domain;

import com.ycrl.base.common.CommonDomain;

import java.util.Date;

/**
 * 收藏公告
 *
 * @author Michael
 */
public class NewsCollect extends CommonDomain {

    private String newsId;          // 公告ID
    private String newsTitle;       // 公告标题
    private String collectorId;     // 收藏人ID
    private String collectorName;   // 收藏人名称
    private Date collectTime;       // 收藏时间

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

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
    }

    public String getCollectorName() {
        return collectorName;
    }

    public void setCollectorName(String collectorName) {
        this.collectorName = collectorName;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }
}
