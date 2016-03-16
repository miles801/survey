package eccrm.knowledge.survey.domain;

import com.ycrl.utils.tree.TreeSymbol;
import eccrm.base.tenement.domain.CrmBaseDomain;

/**
 * 题库分类
 */
@TreeSymbol
public class SubjectCategory extends CrmBaseDomain {

    private String name;
    private String parentId;
    private String parentName;
    /* 用于查找节点 */
    private String path;
    /**
     * 排序
     */
    private Integer sequenceNo;
    /**
     * 来自于通用状态CommonUtils
     */
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
