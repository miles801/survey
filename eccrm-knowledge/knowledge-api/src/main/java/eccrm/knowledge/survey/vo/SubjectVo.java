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
    private Integer currentIndex;   // 当前答题个数
    private Integer currentScore;   // 当前分数

    // 题目答案
    private String answer;

    // 是否手动评卷（一般针对于填空题和简答题）
    private Boolean manualApprove;

    private List<SubjectItem> items;

    public Integer getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    private String item6;
    private String item7;
    private String item8;
    private String item9;

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public String getItem7() {
        return item7;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    public String getItem8() {
        return item8;
    }

    public void setItem8(String item8) {
        this.item8 = item8;
    }

    public String getItem9() {
        return item9;
    }

    public void setItem9(String item9) {
        this.item9 = item9;
    }

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
