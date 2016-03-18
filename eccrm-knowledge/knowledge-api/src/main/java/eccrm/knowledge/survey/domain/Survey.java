package eccrm.knowledge.survey.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 试卷（试卷）
 *
 * @author Michael
 */
public class Survey extends CrmBaseDomain {

    /**
     * 试卷状态：系统参数
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

    // 试卷名称
    @NotNull
    private String name;

    // 前导页内容
    private String navContent;

    // 试卷编号
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
    @NotNull
    private String status;

    // 试卷的总分数
    @NotNull
    private Integer totalScore;

    // 试卷的总题目数
    @NotNull
    private Integer totalSubjects;

    // 单选题个数
    private Integer xzCounts;
    // 单选题单题分数
    private Integer xzScore;
    // 单选总分数
    private Integer xzTotalScore;

    // 多选题个数
    private Integer dxCounts;
    // 多选题单题分数
    private Integer dxScore;
    // 多选题总分数
    private Integer dxTotalScore;

    // 判断题个数
    private Integer pdCounts;
    // 判断题分数
    private Integer pdScore;
    // 判断题总分数
    private Integer pdTotalScore;

    // 填空题个数
    private Integer tkCounts;
    // 填空题分数
    private Integer tkScore;
    // 填空题总分数
    private Integer tkTotalScore;

    // 简答题个数
    private Integer jdCounts;
    // 简答题分数
    private Integer jdScore;
    // 简答题总分数
    private Integer jdTotalScore;

    // 选题模式：随机选题、手动选题
    private Boolean isRandomSubject;


    public Boolean getRandomSubject() {
        return isRandomSubject;
    }

    public void setRandomSubject(Boolean randomSubject) {
        isRandomSubject = randomSubject;
    }

    public Integer getJdTotalScore() {
        return jdTotalScore;
    }

    public void setJdTotalScore(Integer jdTotalScore) {
        this.jdTotalScore = jdTotalScore;
    }

    public Integer getTkTotalScore() {
        return tkTotalScore;
    }

    public void setTkTotalScore(Integer tkTotalScore) {
        this.tkTotalScore = tkTotalScore;
    }

    public Integer getPdTotalScore() {
        return pdTotalScore;
    }

    public void setPdTotalScore(Integer pdTotalScore) {
        this.pdTotalScore = pdTotalScore;
    }

    public Integer getDxTotalScore() {
        return dxTotalScore;
    }

    public void setDxTotalScore(Integer dxTotalScore) {
        this.dxTotalScore = dxTotalScore;
    }

    public Integer getXzTotalScore() {
        return xzTotalScore;
    }

    public void setXzTotalScore(Integer xzTotalScore) {
        this.xzTotalScore = xzTotalScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalSubjects() {
        return totalSubjects;
    }

    public void setTotalSubjects(Integer totalSubjects) {
        this.totalSubjects = totalSubjects;
    }

    public Integer getXzCounts() {
        return xzCounts;
    }

    public void setXzCounts(Integer xzCounts) {
        this.xzCounts = xzCounts;
    }

    public Integer getXzScore() {
        return xzScore;
    }

    public void setXzScore(Integer xzScore) {
        this.xzScore = xzScore;
    }

    public Integer getDxCounts() {
        return dxCounts;
    }

    public void setDxCounts(Integer dxCounts) {
        this.dxCounts = dxCounts;
    }

    public Integer getDxScore() {
        return dxScore;
    }

    public void setDxScore(Integer dxScore) {
        this.dxScore = dxScore;
    }

    public Integer getPdCounts() {
        return pdCounts;
    }

    public void setPdCounts(Integer pdCounts) {
        this.pdCounts = pdCounts;
    }

    public Integer getPdScore() {
        return pdScore;
    }

    public void setPdScore(Integer pdScore) {
        this.pdScore = pdScore;
    }

    public Integer getTkCounts() {
        return tkCounts;
    }

    public void setTkCounts(Integer tkCounts) {
        this.tkCounts = tkCounts;
    }

    public Integer getTkScore() {
        return tkScore;
    }

    public void setTkScore(Integer tkScore) {
        this.tkScore = tkScore;
    }

    public Integer getJdCounts() {
        return jdCounts;
    }

    public void setJdCounts(Integer jdCounts) {
        this.jdCounts = jdCounts;
    }

    public Integer getJdScore() {
        return jdScore;
    }

    public void setJdScore(Integer jdScore) {
        this.jdScore = jdScore;
    }

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
