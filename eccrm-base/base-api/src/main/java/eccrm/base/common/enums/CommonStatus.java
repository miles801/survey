package eccrm.base.common.enums;

import eccrm.core.enums.EnumValue;

/**
 * 通用的状态
 * Created by miles on 2014/6/28.
 */
public enum CommonStatus implements EnumValue<String> {
    /**
     * 未激活/未启用
     */
    INACTIVE {
        @Override
        public String getValue() {
            return "INACTIVE";
        }
    },
    /**
     * 启用中/正常
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
    }
}
