package eccrm.base.auth.domain;

import java.io.Serializable;

/**
 * 授权数据
 *
 * @author Michael
 */
public class AccreditData extends Accredit implements Serializable {


    /**
     * 资源编号
     */
    private String resourceCode;

    /**
     * 授权类型，不能为空
     *
     * @see AccreditDataType
     */
    private String accreditType;

    /**
     * 额外授权的机构id，多个值使用逗号分隔
     */
    private String otherOrgIds;
    private String otherOrgNames;

    /**
     * 额外授权的系统编号，多个值使用逗号分隔
     */
    private String otherParam;

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getAccreditType() {
        return accreditType;
    }

    public void setAccreditType(String accreditType) {
        this.accreditType = accreditType;
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
