package eccrm.base.auth.domain;

import com.ycrl.base.common.CommonDomain;

/**
 * 可授权对象基类
 *
 * @author Michael
 */
public abstract class Accredit extends CommonDomain {
    /**
     * 资源ID（及菜单ID）
     */
    private String resourceId;

    /**
     * 组织机构ID
     */
    private String orgId;

    /**
     * 组织机构下部门/岗位ID
     */
    private String deptId;

    /**
     * 员工ID
     */
    private String empId;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
