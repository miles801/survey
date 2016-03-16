package eccrm.base.auth.domain;

import com.ycrl.base.common.CommonDomain;

import java.io.Serializable;

/**
 * 授权菜单/授权资源
 *
 * @author Michael
 */
public class AccreditMenu extends CommonDomain implements Serializable {
    /**
     * 资源ID（及菜单ID）
     */
    private String resourceId;
    /**
     * 组织机构下部门/岗位ID
     */
    private String deptId;

    private String orgId;


    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}

