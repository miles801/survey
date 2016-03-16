package eccrm.base.user.vo;

import eccrm.base.tenement.vo.CrmBaseVo;

import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-03
 */

public class GroupVo extends CrmBaseVo {

    private String name;
    private String code;
    private String parentId;
    private String parentName;
    private Integer sequenceNo;
    private List<GroupVo> children;
    private String path;
    private String status;
    private String statusName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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


    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GroupVo> getChildren() {
        return children;
    }

    public void setChildren(List<GroupVo> children) {
        this.children = children;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
