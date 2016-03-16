package eccrm.base.user.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.base.user.enums.UserStatus;
import eccrm.base.user.enums.UserType;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author miles
 * @datetime 2014-03-14
 */
public class User extends CrmBaseDomain implements EnumSymbol {
    @NotNull(message = "用户名不能为空!")
    private String username;
    @NotNull(message = "密码不能为空!")
    private String password;
    /**
     * 密码的失效时间
     */
    private Date passwordDeadline;
    private String code;
    @EnumClass(UserType.class)
    private String type;
    private Date startDate;
    private Date endDate;
    private String email;
    @EnumClass(UserStatus.class)
    private String status;

    /**
     * 用户组的id列表，多个值使用逗号分隔
     */
    @Transient
    private String groupIds;

    // 员工信息
    private String employeeId;
    private String employeeName;
    private String employeeNo;
    private String gender;// 性别
    private String mobilePhone;
    private String officePhone;

    private String deptId;// 机构
    private String deptName;// 机构名称
    private String duty; // 职务
    private String position;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getPasswordDeadline() {
        return passwordDeadline;
    }

    public void setPasswordDeadline(Date passwordDeadline) {
        this.passwordDeadline = passwordDeadline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
