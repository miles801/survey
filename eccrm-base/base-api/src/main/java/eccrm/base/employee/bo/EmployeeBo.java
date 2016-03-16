package eccrm.base.employee.bo;

import eccrm.base.employee.domain.Employee;

public class EmployeeBo extends Employee {

    /**
     * 如果该值为true，表示查询状态有启用，且处于在职的员工（正式、实习、调动中）
     */
    private Boolean valid;

    /**
     * 启用数据权限过滤
     */
    private Boolean permission;

    /**
     * 是否拥有直属岗位
     */
    private Boolean hasPosition;


    public Boolean getHasPosition() {
        return hasPosition;
    }

    public void setHasPosition(Boolean hasPosition) {
        this.hasPosition = hasPosition;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }
}
