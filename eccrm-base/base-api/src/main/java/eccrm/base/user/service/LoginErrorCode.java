package eccrm.base.user.service;

import eccrm.core.enums.EnumMessage;
import eccrm.core.enums.EnumValue;

/**
 * 登录验证结果
 * Created by miles on 2014/3/20.
 */
public enum LoginErrorCode implements EnumValue<Integer>, EnumMessage {
    /**
     * 验证成功
     */
    SUCCESS {
        @Override
        public String getMessage() {
            return "成功";
        }

        @Override
        public Integer getValue() {
            return 0;
        }
    },
    ERROR_TENEMENT {
        @Override
        public Integer getValue() {
            return 1;
        }

        @Override
        public String getMessage() {
            return "租户不存在";
        }
    },
    ERROR_USER {
        @Override
        public Integer getValue() {
            return 2;
        }

        @Override
        public String getMessage() {
            return "用户名错误";
        }
    },
    /**
     * 错误的密码
     */
    ERROR_PASSWORD {
        @Override
        public String getMessage() {
            return "密码错误";
        }

        @Override
        public Integer getValue() {
            return 3;
        }
    },
    INACTIVE {
        @Override
        public String getMessage() {
            return "账户未激活";
        }

        @Override
        public Integer getValue() {
            return 4;
        }
    },
    EXPIRED {
        @Override
        public String getMessage() {
            return "账户已过期";
        }

        @Override
        public Integer getValue() {
            return 5;
        }
    },
    PASSWORD_EXPIRED {
        @Override
        public Integer getValue() {
            return 6;
        }

        @Override
        public String getMessage() {
            return "密码已过期";
        }
    },
    DISABLED {
        @Override
        public String getMessage() {
            return "用户已禁用";
        }

        @Override
        public Integer getValue() {
            return 7;
        }
    },
    PAUSE {
        @Override
        public String getMessage() {
            return "账户已暂停";
        }

        @Override
        public Integer getValue() {
            return 8;
        }
    }
}
