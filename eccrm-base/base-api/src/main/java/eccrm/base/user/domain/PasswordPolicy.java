package eccrm.base.user.domain;

import eccrm.base.tenement.domain.CrmBaseDomain;
import eccrm.base.user.enums.PasswordExpiredHandle;
import eccrm.base.user.enums.PasswordLimitType;
import eccrm.base.user.enums.PasswordValidity;
import eccrm.core.enums.EnumClass;
import eccrm.core.enums.EnumSymbol;

/**
 * @author miles
 * @datetime 2014-03-17
 */
public class PasswordPolicy extends CrmBaseDomain implements EnumSymbol {
    /**
     * 密码限制的类型
     */
    @EnumClass(PasswordLimitType.class)
    private Integer limitType;
    private Integer minLength;//密码长度最小值
    private Integer maxLength;//密码长度最大值
    private String defaultPassword;//默认密码
    /**
     * 有效期
     */
    @EnumClass(PasswordValidity.class)
    private Integer effectivePeriod;
    /**
     * 过期处理方式
     */
    @EnumClass(PasswordExpiredHandle.class)
    private Integer expireHandler;

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

    public Integer getLimitType() {
        return limitType;
    }

    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
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
