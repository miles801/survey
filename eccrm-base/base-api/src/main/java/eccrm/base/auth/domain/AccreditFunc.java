package eccrm.base.auth.domain;

import java.io.Serializable;

/**
 * 授权功能
 *
 * @author Michael
 */
public class AccreditFunc extends Accredit implements Serializable{
    /**
     * 已授权操作的编号
     */
    public static final String ACCREDIT_FUNCTION_CODE = "accreditFunctionCode";
    /**
     * 资源编号
     */
    private String resourceCode;

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }
}
