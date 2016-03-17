package eccrm.knowledge.survey.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;

import java.util.List;

/**
 * 调查文件--题目
 */
public class Subject extends CrmBaseDomain {

    /**
     * 题型：单选、多选。。。
     */
    public static final String TYPE = "SP_SURVEY_SUBJECT_TYPE";

    /*题目*/
    private String title;

    /*题库*/
    private String categoryId;
    private String categoryName;

    /**
     * 系统参数：题型
     */
    private String subjectType;

    /*状态*/
    private String status;
    /*是否必填*/
    private Boolean isRequired;

    /*包含其他*/
    private Boolean containOther;

    /*下拉显示*/
    private Boolean showSelect;

    /*横排*/
    private Boolean showList;

    // 题目答案
    private String answer;

    // 是否手动评卷（一般针对于填空题和简答题）
    private Boolean manualApprove;

    /**
     * 题目选项列表
     */
    private List<SubjectItem> items;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getContainOther() {
        return containOther;
    }

    public void setContainOther(Boolean containOther) {
        this.containOther = containOther;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Boolean getShowList() {
        return showList;
    }

    public void setShowList(Boolean showList) {
        this.showList = showList;
    }

    public Boolean getShowSelect() {
        return showSelect;
    }

    public void setShowSelect(Boolean showSelect) {
        this.showSelect = showSelect;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectItem> getItems() {
        return items;
    }

    public void setItems(List<SubjectItem> items) {
        this.items = items;
    }
}
