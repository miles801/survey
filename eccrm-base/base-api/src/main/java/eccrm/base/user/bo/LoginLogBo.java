package eccrm.base.user.bo;

import eccrm.base.user.domain.LoginLog;

import java.util.Date;

/**
 * @author miles
 * @datetime 2014-04-14
 */

public class LoginLogBo extends LoginLog {
    /**
     * 查询用：登录时间开始
     */
    private Date startDate;
    /**
     * 查询用：登录时间截止
     */
    private Date endDate;


    private String username;
    private String employeeName;
    private String employeeNo;

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

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }
}
