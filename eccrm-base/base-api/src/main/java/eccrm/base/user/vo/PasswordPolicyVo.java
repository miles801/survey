package eccrm.base.user.vo;


import eccrm.base.tenement.vo.CrmBaseVo;

/**
 * @author miles
 * @datetime 2014-03-17
 */

public class PasswordPolicyVo extends CrmBaseVo {
    private Integer limitType;
    private Integer minLength;//密码长度最小值
    private Integer maxLength;//密码长度最大值
    private String defaultPassword;//默认密码
    private Integer effectivePeriod;
    private Integer expireHandler;

    public Integer getLimitType() {
        return limitType;
    }

    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public Integer getEffectivePeriod() {
        return effectivePeriod;
    }

    public void setEffectivePeriod(Integer effectivePeriod) {
        this.effectivePeriod = effectivePeriod;
    }

    public Integer getExpireHandler() {
        return expireHandler;
    }

    public void setExpireHandler(Integer expireHandler) {
        this.expireHandler = expireHandler;
    }
}
