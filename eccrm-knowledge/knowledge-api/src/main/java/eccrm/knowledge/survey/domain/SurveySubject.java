package eccrm.knowledge.survey.domain;

import com.ycrl.base.common.CommonDomain;

/**
 * 调查试卷的题目(关联关系)
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

    // 下一题目ID
    private String nextSubjectId;

    // 下一题目名称
    private String nextSubjectName;

    // 系统参数：路由类型，直接跳转和条件跳转
    private String routeType;

    // 跳转条件，答案，一般只有当题目类型是选择题或者判断题的时候才设置该值
    private String condition;

    // 是第一题
    private Boolean isFirst;
    // 是最后一题
    private Boolean isLast;
    // 排序号
    private Integer sequenceNo;


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

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getSubjectTypeName() {
        return subjectTypeName;
    }

    public void setSubjectTypeName(String subjectTypeName) {
        this.subjectTypeName = subjectTypeName;
    }

    public String getNextSubjectId() {
        return nextSubjectId;
    }

    public void setNextSubjectId(String nextSubjectId) {
        this.nextSubjectId = nextSubjectId;
    }

    public String getNextSubjectName() {
        return nextSubjectName;
    }

    public void setNextSubjectName(String nextSubjectName) {
        this.nextSubjectName = nextSubjectName;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Boolean getIsLast() {
        return isLast;
    }

    public void setIsLast(Boolean isLast) {
        this.isLast = isLast;
    }

    public Boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }
}
