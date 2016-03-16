package eccrm.knowledge.survey.bo;

import com.ycrl.core.hibernate.criteria.BO;
import com.ycrl.core.hibernate.criteria.Condition;
import com.ycrl.core.hibernate.criteria.LikeModel;
import com.ycrl.core.hibernate.criteria.MatchModel;

public class SubjectCategoryBo implements BO {
    @Condition
    private String id;

    @Condition(matchMode = MatchModel.LIKE, likeMode = LikeModel.START)
    private String name;

    @Condition
    private String status;

    @Condition
    private String parentId;

    /**
     * 用于查询当前节点即所有的子节点
     */
    @Condition(matchMode = MatchModel.LIKE,likeMode = LikeModel.ANYWHERE)
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
