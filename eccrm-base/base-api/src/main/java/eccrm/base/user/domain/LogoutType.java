package eccrm.base.user.domain;

import eccrm.core.enums.EnumValue;

/**
 * Created by miles on 2014/4/17.
 * 退出方式
 */
public enum LogoutType implements EnumValue<Integer> {
    /**
     * 正常退出
     */
    REGULAR {
        @Override
        public Integer getValue() {
            return 1;
        }
    },
    /**
     * 超时
     */
    TIMEOUT {
        @Override
        public Integer getValue() {
            return 2;
        }
    },
    /**
     * 强制下线
     */
    FORCE {
        @Override
        public Integer getValue() {
            return 3;
        }
    },
    /**
     * 重新登录
     */
    RELOGIN {
        @Override
        public Integer getValue() {
            return 4;
        }
    },
    /**
     * 系统关闭
     */
    SYSTEM_DOWN {
        public Integer getValue() {
            return 5;
        }
    }
}
