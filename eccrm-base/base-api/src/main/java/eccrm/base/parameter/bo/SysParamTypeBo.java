package eccrm.base.parameter.bo;

import eccrm.base.parameter.domain.SysParamType;

/**
 * @author miles
 * @datetime 2014-06-20
 */

public class SysParamTypeBo extends SysParamType {
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
