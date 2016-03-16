package eccrm.base.parameter.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.utils.tree.PathTree;
import eccrm.utils.tree.StaticTree;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-06-20
 */
public class SysParamType extends CrmBaseDomain implements PathTree<SysParamType, String>, StaticTree<SysParamType, String> {

    private String name;
    private String code;
    private SysParamType parent;
    private Integer sequenceNo;
    private String status;
    private String path;
    @Transient
    private List<SysParamType> children;

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

    @Override
    public SysParamType getParent() {
        return parent;
    }

    public void setParent(SysParamType parent) {
        this.parent = parent;
    }

    @Override
    public SysParamType addChild(SysParamType child) {
        if (children == null) {
            children = new ArrayList<SysParamType>();
        }
        children.add(child);
        return this;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SysParamType> getChildren() {
        return children;
    }

    public void setChildren(List<SysParamType> children) {
        this.children = children;
    }
}
