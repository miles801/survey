package eccrm.knowledge.survey.vo;

import com.ycrl.base.common.CommonVo;

import java.util.Date;

/**
 * @author Michael
 */
public class SurveyVo extends CommonVo {

    // 问卷名称
    private String name;

    // 问卷编号
    private String code;

    // 前导页内容
    private String navContent;

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

    private String statusName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getMultiPage() {
        return multiPage;
    }

    public void setMultiPage(Boolean multiPage) {
        this.multiPage = multiPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getNavContent() {
        return navContent;
    }

    public void setNavContent(String navContent) {
        this.navContent = navContent;
    }
}
