package eccrm.base.role.domain;

import eccrm.core.enums.EnumValue;

/**
 * 角色状态
 * Created by miles on 2014/3/26.
 */
public enum RoleStatus implements EnumValue<String> {
    /**
     * 未启用
     */
    INACTIVE {
        @Override
        public String getValue() {
            return "INACTIVE";
        }
    },
    /**
     * 启用中
     */
    ACTIVE {
        @Override
        public String getValue() {
            return "ACTIVE";
        }
    },
    /**
     * 已注销
     */
    CANCELED {
        @Override
        public String getValue() {
            return "CANCELED";
        }
    },
    /**
     * 已关闭
     */
    CLOSED {
        @Override
        public String getValue() {
            return "CLOSED";
        }
    }
}
