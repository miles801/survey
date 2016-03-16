package eccrm.base.user.enums;

import eccrm.core.enums.EnumValue;

/**
 * 用户类型
 * Created by miles on 2014/4/1.
 */
public enum UserType implements EnumValue<String> {
    /**
     * 正式用户
     */
    OFFICIAL {
        public String getValue() {
            return "OFFICIAL";
        }
    },
    /**
     * 临时用户
     */
    TEMP {
        public String getValue() {
            return "TEMP";
        }
    }
}
