package eccrm.base.menu.domain;

import com.ycrl.base.common.CommonDomain;

import java.util.Date;

/**
 * 九宫格
 *
 * @author Michael
 */
public class MenuNav extends CommonDomain {
    /**
     * 导航菜单发布状态：通用状态
     */
    public static final String STATUS = "SP_COMMON_STATE";
    /**
     * 导航菜单类型：系统参数（单功能、多功能）
     */
    public static final String TYPE = "SP_NAV_TYPE";

    private String name;
    /**
     * 显示名称
     */
    private String showName;
    private Integer sequenceNo;
    /**
     * 导航菜单类型：单功能、多功能组（多页签）
     */
    private String type;

    /**
     * 显示方式：1、2、3列
     */
    private Integer cols;
    /**
     * 行显示方式：1、2、3行
     */
    private Integer rows;

    private MenuNav parent;
    /**
     * 所属资源
     */
    private String resourceId;

    /**
     * 发布时间
     */
    private Date publishDatetime;
    /**
     * 显示更多
     */
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public MenuNav getParent() {
        return parent;
    }

    public void setParent(MenuNav parent) {
        this.parent = parent;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Date getPublishDatetime() {
        return publishDatetime;
    }

    public void setPublishDatetime(Date publishDatetime) {
        this.publishDatetime = publishDatetime;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Boolean getShowMore() {
        return showMore;
    }

    public void setShowMore(Boolean showMore) {
        this.showMore = showMore;
    }
}
