package eccrm.base.parameter.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.utils.tree.PathTree;
import eccrm.utils.tree.StaticTree;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miles
 * @datetime 2014-07-02
 */
public class BusinessParamType extends CrmBaseDomain implements PathTree<BusinessParamType, String>, StaticTree<BusinessParamType, String> {

    private String name;
    private String code;
    private BusinessParamType parent;
    private Integer sequenceNo;
    private String status;
    private String path;
    @Transient
    private List<BusinessParamType> children;

    @Override
    public BusinessParamType getParent() {
        return this.parent;
    }

    @Override
    public void setParent(BusinessParamType parent) {
        this.parent = parent;
    }

    @Override
    public BusinessParamType addChild(BusinessParamType child) {
        if (children == null) {
            children = new ArrayList<BusinessParamType>();
        }
        children.add(child);
        return this;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BusinessParamType> getChildren() {
        return children;
    }

    public void setChildren(List<BusinessParamType> children) {
        this.children = children;
    }
}
