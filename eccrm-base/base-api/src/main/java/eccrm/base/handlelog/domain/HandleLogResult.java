package eccrm.base.handlelog.domain;

import eccrm.core.enums.EnumValue;

/**
 * 处理结果    
 * 日报、周报和日报中的处理结果有两个  同意  不同意          其他业务模块中如果有其他的结果则可以再添加
 * @author wangsd
 *
 */
public enum HandleLogResult implements EnumValue<String>{

	/**
     * 同意
     */
    AGREE {
        public String getValue() {
            return "1";
        }
    },
    
    /**
     * 不同意
     */
    NOT_AGREE {
        public String getValue() {
            return "2";
        }
    };
	
}
