package eccrm.base.handlelog.domain;

import eccrm.core.enums.EnumValue;

public enum HandleLogBusinessType implements EnumValue<String>{

	/**
     * 日报、周报或者月报
     */
    REPORT {
        public String getValue() {
            return "1";
        }
    },
    
    /**
     * 任务
     */
    TASK {
        public String getValue() {
            return "2";
        }
    };
	
}
