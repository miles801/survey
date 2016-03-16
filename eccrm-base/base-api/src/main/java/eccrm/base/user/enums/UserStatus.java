package eccrm.base.user.enums;

import eccrm.core.enums.EnumValue;

/**
 * 用户状态
 * Created by miles on 2014/7/2.
 */
public enum UserStatus implements EnumValue<String> {
    INACTIVE {
        @Override
        public String getValue() {
            return "INACTIVE";
        }
    },
    ACTIVE {
        @Override
        public String getValue() {
            return "ACTIVE";
        }
    },
    CANCELED {
        @Override
        public String getValue() {
            return "CANCELED";
        }
    },
    PAUSE {
        @Override
        public String getValue() {
            return "PAUSE";
        }
    }
}
