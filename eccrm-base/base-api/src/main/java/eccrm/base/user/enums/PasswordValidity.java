package eccrm.base.user.enums;

import eccrm.core.enums.EnumValue;

/**
 * 密码有效期
 * Created by miles on 2014/6/26.
 */
public enum PasswordValidity implements EnumValue<Integer> {
    FOREVER {
        @Override
        public Integer getValue() {
            return 0;
        }
    },
    ONE_WEEK {
        @Override
        public Integer getValue() {
            return 1;
        }
    },
    ONE_MONTH {
        @Override
        public Integer getValue() {
            return 2;
        }
    },
    THREE_MONTH {
        @Override
        public Integer getValue() {
            return 3;
        }
    },
    HALF_YEAR {
        @Override
        public Integer getValue() {
            return 4;
        }
    },
    ONE_YEAR {
        @Override
        public Integer getValue() {
            return 5;
        }
    };


    public abstract Integer getValue();
}
