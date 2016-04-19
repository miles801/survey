package eccrm.knowledge.survey.domain;

import com.ycrl.base.common.CommonDomain;

/**
 * 试卷的题目(关联关系)
 *
 * @author Michael
 */
public class SurveySubject extends CommonDomain {

    /**
     * 跳转规则：直接跳转
     */
    public static final String ROUTE_DIRECT = "1";
    /**
     * 跳转规则：条件跳转
     */
    public static final String ROUTE_CONDITION = "2";

    // 试卷ID
    private String surveyId;

    // 题目ID
    private String subjectId;
    private String subjectName;

    // 题型：单选、多选...
    private String subjectType;
    private String subjectTypeName;

    // 题库分类
    private String categoryId;
    private String categoryName;


    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyId() {
        return this.surveyId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return this.categoryId;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubjectTypeName() {
        return subjectTypeName;
    }

    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }


}
