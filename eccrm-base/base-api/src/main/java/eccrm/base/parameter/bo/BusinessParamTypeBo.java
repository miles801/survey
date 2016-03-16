package eccrm.base.parameter.bo;

import eccrm.base.parameter.domain.BusinessParamType;

/**
 * @author miles
 * @datetime 2014-07-02
 */

public class BusinessParamTypeBo extends BusinessParamType {
    private String pid;
    private Boolean containSelf;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Boolean getContainSelf() {
        return containSelf;
    }

    public void setContainSelf(Boolean containSelf) {
        this.containSelf = containSelf;
    }
}
