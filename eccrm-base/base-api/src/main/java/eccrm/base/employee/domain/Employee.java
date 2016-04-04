package eccrm.base.employee.domain;

import com.ycrl.base.common.CommonDomain;
import eccrm.base.attachment.AttachmentSymbol;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by qy on 14-10-15.
 */

public class Employee extends CommonDomain implements AttachmentSymbol {
    // 常量：职务
    public static final String PARAM_DUTY = "BP_ZHIW";
    private String employeeCode;
    @NotNull
    private String employeeName;
    // 性别
    private String gender;
    // 出生年月
    private Date birthday;
    // 电话
    private String mobile;
    // QQ
    private String qq;
    // 邮箱
    private String email;
    // 地址
    private String address;
    // 民族
    private String nation;
    // 政治面貌
    private String zzmm;
    // 婚姻状况
    private String marriage;
    // 证件号码
    private String idNo;
    // 职务
    private String duty;
    // 直属机构
    @NotNull
    private String orgId;
    private String orgName;
    // 直属岗位
    private String positionId;
    private String positionName;
    private String positionCode;    // 直属岗位的编号

    private Boolean outer;  // 是否为外协
    // 公司&班组
    private String company;
    // 头像
    private String picture;

    public Boolean getOuter() {
        return outer;
    }

    public void setOuter(Boolean outer) {
        this.outer = outer;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    @Override
    public String businessId() {
        return getId();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
