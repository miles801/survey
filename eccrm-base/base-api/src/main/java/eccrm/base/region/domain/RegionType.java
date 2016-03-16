package eccrm.base.region.domain;

import eccrm.core.enums.EnumValue;

/**
 * 行政区域的类型
 * Created by miles on 2014/3/26.
 */
public enum RegionType implements EnumValue<Integer> {
    /**
     * 国家
     */
    NATION {
        public Integer getValue() {
            return 0;
        }
    },
    /**
     * 省、特别行政区、直辖市
     */
    PROVINCE {
        public Integer getValue() {
            return 1;
        }
    },
    /**
     * 城市
     */
    CITY {
        public Integer getValue() {
            return 2;
        }
    },
    /**
     * 区/县
     */
    DISTRICT {
        public Integer getValue() {
            return 3;
        }
    },
    /**
     * 乡镇
     */
    TOWN {
        public Integer getValue() {
            return 4;
        }
    },
    /**
     * 街道
     */
    STREET {
        public Integer getValue() {
            return 5;
        }
    };

}
