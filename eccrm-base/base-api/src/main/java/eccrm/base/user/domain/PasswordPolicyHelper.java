package eccrm.base.user.domain;

import eccrm.base.user.enums.PasswordValidity;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * 密码策略的帮助类，用于提供一些辅助功能
 *
 * @author miles
 * @datetime 2014/6/27 15:10
 */
public class PasswordPolicyHelper {
    private PasswordPolicy policy;

    public PasswordPolicyHelper(PasswordPolicy policy) {
        this.policy = policy;
    }

    /**
     * 根据密码策略计算从当前时间起的下一个截止时间
     * 如果返回null，则表示永久
     *
     * @return null/date
     */
    public Date nextDeadline() {
        Integer period = policy.getEffectivePeriod();
        if (period == null || period.equals(PasswordValidity.FOREVER.getValue())) {//永久有效
            return null;
        }
        if (period.equals(PasswordValidity.ONE_WEEK.getValue())) {
            return DateUtils.addWeeks(new Date(), 1);
        }
        if (period.equals(PasswordValidity.ONE_MONTH.getValue())) {
            return DateUtils.addMonths(new Date(), 1);
        }
        if (period.equals(PasswordValidity.THREE_MONTH.getValue())) {
            return DateUtils.addMonths(new Date(), 3);
        }
        return null;
    }

}
