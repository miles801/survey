package eccrm.base.auth.vo;

import com.ycrl.base.common.CommonVo;

/**
 * @author Michael
 */

public class AccreditDataVo extends CommonVo {

    private String resourceCode;
    private String accreditType;
    private String otherOrgIds;
    private String otherOrgNames;
    private String otherParam;

    public String getAccreditType() {
        return accreditType;
    }

    public void setAccreditType(String accreditType) {
        this.accreditType = accreditType;
    }



    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getOtherOrgIds() {
        return otherOrgIds;
    }

    public void setOtherOrgIds(String otherOrgIds) {
        this.otherOrgIds = otherOrgIds;
    }

    public String getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(String otherParam) {
        this.otherParam = otherParam;
    }

    public String getOtherOrgNames() {
        return otherOrgNames;
    }

    public void setOtherOrgNames(String otherOrgNames) {
        this.otherOrgNames = otherOrgNames;
    }
}
