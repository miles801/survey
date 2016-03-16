package eccrm.base.tenement.domain;

import eccrm.core.enums.EnumValue;

/**
 * Created by miles on 2014/4/3.
 * 租户相关的状态
 */
public enum TenementStatus implements EnumValue<Integer> {
    /**
     * 采集中
     */
    COLLECTING {
        public Integer getValue() {
            return 1;
        }
    },
    /**
     * 待审核
     */
    PRE_CHECK {
        public Integer getValue() {
            return 2;
        }
    },
    /**
     * 审核中
     */
    CHECKING {
        public Integer getValue() {
            return 3;
        }
    },
    /**
     * 资源配置
     */
    RESOURCE_CONFIG {
        public Integer getValue() {
            return 4;
        }
    },
    /**
     * 正常/启用中
     */
    NORMAL {
        public Integer getValue() {
            return 5;
        }
    },
    /**
     * 暂停
     */
    PAUSE {
        public Integer getValue() {
            return 6;
        }
    },
    /**
     * 关闭
     */
    CLOSED {
        public Integer getValue() {
            return 7;
        }
    },
    /**
     * 注销/已注销
     */
    CANCELED {
        public Integer getValue() {
            return 8;
        }
    }


}
