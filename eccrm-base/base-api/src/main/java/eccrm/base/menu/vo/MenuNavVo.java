package eccrm.base.menu.vo;

import com.ycrl.base.common.CommonVo;

import java.util.Date;

/**
 * 九宫格
 * @author Michael
 */
public class MenuNavVo extends CommonVo {

    private String name;
    /**
     * 显示名称
     */
    private String showName;
    private int sequenceNo;
    /**
     * 导航菜单类型：单个、多个
     */
    private String type;
    private String typeName;

    /**
     * 显示方式：1、2、3列
     */
    private int cols;
    /**
     * 行显示方式：1、2、3行
     */
    private int rows;

    private String parentId;
    private String parentName;
    /**
     * 所属资源
     */
    private String resourceId;
    private String resourceName;
    private String resourceUrl;

    private String statusName;
    private Date publishDatetime;

    private Boolean showMore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Date getPublishDatetime() {
        return publishDatetime;
    }

    public void setPublishDatetime(Date publishDatetime) {
        this.publishDatetime = publishDatetime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Boolean getShowMore() {
        return showMore;
    }

    public void setShowMore(Boolean showMore) {
        this.showMore = showMore;
    }
}
