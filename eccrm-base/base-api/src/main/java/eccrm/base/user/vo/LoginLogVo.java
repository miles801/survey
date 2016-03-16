package eccrm.base.user.vo;

import eccrm.base.tenement.vo.CrmBaseVo;

import java.util.Date;

/**
 * @author miles
 * @datetime 2014-04-14
 */

public class LoginLogVo extends CrmBaseVo {

    private String username;
    private String employeeName;
    private String userId;
    private String employeeNo;
    private String position;
    private String duty;

    private String ip;
    private Date logoutDatetime;
    private Integer logoutType;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLogoutDatetime() {
        return logoutDatetime;
    }

    public void setLogoutDatetime(Date logoutDatetime) {
        this.logoutDatetime = logoutDatetime;
    }

    public Integer getLogoutType() {
        return logoutType;
    }

    public void setLogoutType(Integer logoutType) {
        this.logoutType = logoutType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }
}
