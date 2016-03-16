package eccrm.base.user.enums;

import eccrm.core.enums.EnumValue;

/**
 * 密码过期处理方式
 * Created by miles on 2014/6/26.
 */
public enum PasswordExpiredHandle implements EnumValue<Integer> {
    FORCE_RESET {
        @Override
        public Integer getValue() {
            return 1;
        }
    }
}
