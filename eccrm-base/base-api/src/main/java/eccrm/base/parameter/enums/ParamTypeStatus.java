package eccrm.base.parameter.enums;

import eccrm.core.enums.EnumValue;

/**
 * 参数类型的状态
 * Created by miles on 2014/6/28.
 */
public enum ParamTypeStatus implements EnumValue<Integer> {
    INACTIVE {
        @Override
        public Integer getValue() {
            return 1;
        }
    },
    ACTIVE {
        @Override
        public Integer getValue() {
            return 2;
        }
    },
    CANCELED {
        @Override
        public Integer getValue() {
            return 3;
        }
    }
}
