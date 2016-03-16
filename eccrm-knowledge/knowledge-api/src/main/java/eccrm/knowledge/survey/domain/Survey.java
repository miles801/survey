package eccrm.knowledge.survey.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;

import java.util.Date;

/**
 * 调查问卷
 *
 * @author Michael
 */
public class Survey extends CrmBaseDomain {

    /**
     * 问卷状态：系统参数
     */
    public static final String STATUS = "SP_SURVEY_STATUS";
    // 未启用
    public static final String STATUS_INACTIVE = "INACTIVE";
    // 启用
    public static final String STATUS_ACTIVE = "ACTIVE";
    // 发布
    public static final String STATUS_PUBLISHED = "PUBLISHED";
    // 关闭
    public static final String STATUS_CLOSED = "CLOSED";
    // 注销
    public static final String STATUS_CANCELED = "CANCELED";

    // 问卷名称
    private String name;

    // 前导页内容
    private String navContent;

    // 问卷编号
    private String code;

    // 开始时间
    private Date startTime;

    // 截止时间
    private Date endTime;

    // 是否显示前导页
    private Boolean showNavPage;

    // 是否多页显示
    private Boolean multiPage;

    // 允许查看结果
    private Boolean showResult;

    // 状态
    private String status;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }


    public Boolean getMultiPage() {
        return multiPage;
    }

    public void setMultiPage(Boolean multiPage) {
        this.multiPage = multiPage;
    }

    public Boolean getShowNavPage() {
        return showNavPage;
    }

    public void setShowNavPage(Boolean showNavPage) {
        this.showNavPage = showNavPage;
    }

    public Boolean getShowResult() {
        return showResult;
    }

    public void setShowResult(Boolean showResult) {
        this.showResult = showResult;
    }

    public String getNavContent() {
        return navContent;
    }

    public void setNavContent(String navContent) {
        this.navContent = navContent;
    }
}
