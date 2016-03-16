package eccrm.base.user.domain;

import eccrm.base.common.enums.CommonStatus;
import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;
import eccrm.utils.tree.PathTree;
import eccrm.utils.tree.StaticTree;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-03
 */
public class Group extends CrmBaseDomain implements EnumSymbol, PathTree<Group, String>, StaticTree<Group, String> {

    private String name;
    private String code;
    private Group parent;
    private Integer sequenceNo;
    private String path;
    @EnumClass(CommonStatus.class)
    private String status;

    @Transient
    private List<Group> children;

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

    public List<Group> getChildren() {
        return children;
    }

    public void setChildren(List<Group> children) {
        this.children = children;
    }


    @Override
    public Group addChild(Group child) {
        if (children == null) {
            children = new ArrayList<Group>();
        }
        children.add(child);
        return this;
    }

    public Group getParent() {
        return parent;
    }

    @Override
    public void setParent(Group parent) {
        this.parent = parent;
    }
}
