package eccrm.knowledge.survey.bo;

import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;
import com.ycrl.core.hibernate.criteria.LikeModel;
import com.ycrl.core.hibernate.criteria.MatchModel;

public class SubjectBo implements BO {

    @Condition(matchMode = MatchModel.LIKE,likeMode = LikeModel.ANYWHERE)
    private String title;

    @Condition
    private String status;

    @Condition
    private String subjectType;            // 题型

    @Condition
    private String categoryId;      // 题目所属题库

    /**
     * 如果指定该值，则表示排除掉已经在指定试卷中存在的题目
     */
    private String surveyId;        // 试卷ID

    // 是否随机获取题目
    private Boolean random;

    public Boolean getRandom() {
        return random;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }
}
