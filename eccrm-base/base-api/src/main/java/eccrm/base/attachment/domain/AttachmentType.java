package eccrm.base.attachment.domain;

import eccrm.core.enums.EnumValue;

/**
 * Created by miles on 2014/5/13.
 */
public enum AttachmentType implements EnumValue<Integer> {
    /**
     * 本地附件
     */
    FILE {
        @Override
        public Integer getValue() {
            return 1;
        }
    },
    /**
     * 远程URL
     */
    URL {
        @Override
        public Integer getValue() {
            return 2;
        }
    }
}
