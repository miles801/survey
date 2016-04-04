package eccrm.base.employee.service.impl;

import com.michael.poi.annotation.Col;
import com.michael.poi.annotation.FieldConvert;
import com.michael.poi.annotation.ImportConfig;
import com.michael.poi.core.DTO;

/**
 * @author Michael
 */
@ImportConfig(
        file = "",
        startRow = 2
)
public class EmployeeDTO implements DTO {
    @Col(index = 0)
    private String employeeCode;    // 员工号（用户名）
    @Col(index = 1)
    private String employeeName;
    @Col(index = 2)
    private String orgName;
    @Col(index = 3)
    private String mobile;
    @Col(index = 4)
    private String idNo;
    @Col(index = 5)
    @FieldConvert(convertorClass = BooleanConverter.class)
    private Boolean outer;  // 外协人员
    @Col(index = 6)
    private String company;   // 班组
    @Col(index = 7)
    private String description;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Boolean getOuter() {
        return outer;
    }

    public void setOuter(Boolean outer) {
        this.outer = outer;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
