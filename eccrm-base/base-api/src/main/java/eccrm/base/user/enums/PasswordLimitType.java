package eccrm.base.user.enums;

import eccrm.core.enums.EnumValue;

/**
 * 密码策略中密码的限制类型
 * Created by miles on 2014/6/26.
 */
public enum PasswordLimitType implements EnumValue<Integer> {
    NO {
        @Override
        public Integer getValue() {
            return 1;
        }
    },
    LETTER {
        @Override
        public Integer getValue() {
            return 2;
        }
    }
}
