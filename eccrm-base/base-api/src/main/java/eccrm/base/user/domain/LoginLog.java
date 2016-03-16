package eccrm.base.user.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;

import java.util.Date;

/**
 * @author miles
 * @datetime 2014-04-14
 * 登录日志：
 * 登录的人：创建者
 * 登录时间：创建时间
 */
public class LoginLog extends CrmBaseDomain implements EnumSymbol {
    /**
     * 登录状态
     */
    private String status;
    /**
     * 退出时间
     */
    private Date logoutDatetime;
    /**
     * 持续时间:毫秒数
     */
    private Long duration;

    private User user;

    /**
     * 退出方式：强制下线、session到期、自己退出
     */
    @EnumClass(LogoutType.class)
    private Integer logoutType;

    private String ip;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLogoutDatetime() {
        return logoutDatetime;
    }

    public void setLogoutDatetime(Date logoutDatetime) {
        this.logoutDatetime = logoutDatetime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getLogoutType() {
        return logoutType;
    }

    public void setLogoutType(Integer logoutType) {
        this.logoutType = logoutType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
