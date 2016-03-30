package eccrm.knowledge.survey.vo;

import eccrm.base.tenement.vo.CrmBaseVo;
import eccrm.knowledge.survey.domain.SubjectItem;

import java.util.List;

public class SubjectVo extends CrmBaseVo {

    private String title;
    private String categoryId;
    private String categoryName;
    private String subjectType;
    private String subjectTypeName; // 题型
    private String status;          // 状态
    private String statusName;
    private Boolean isRequired;     // 是否必填
    private Boolean containOther;   // 包含其他
    private Boolean showSelect; // 下拉显示
    private Boolean showList;   // 横向显示
    private List radioList;     // 单选
    private List checkboxList;  // 复选
    // 该题目将在哪一个试卷中
    private String surveyReportDetailId;

    // 题目答案
    private String answer;

    // 是否手动评卷（一般针对于填空题和简答题）
    private Boolean manualApprove;

    private List<SubjectItem> items;

    public String getSurveyReportDetailId() {
        return surveyReportDetailId;
    }

    public void setSurveyReportDetailId(String surveyReportDetailId) {
        this.surveyReportDetailId = surveyReportDetailId;
    }

    public Boolean getManualApprove() {
        return manualApprove;
    }

    public void setManualApprove(Boolean manualApprove) {
        this.manualApprove = manualApprove;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public List getCheckboxList() {
        return checkboxList;
    }

    public void setCheckboxList(List checkboxList) {
        this.checkboxList = checkboxList;
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

    public List getRadioList() {
        return radioList;
    }

    public void setRadioList(List radioList) {
        this.radioList = radioList;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getSubjectTypeName() {
        return subjectTypeName;
    }

    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }

    public List<SubjectItem> getItems() {
        return items;
    }

    public void setItems(List<SubjectItem> items) {
        this.items = items;
    }
}
