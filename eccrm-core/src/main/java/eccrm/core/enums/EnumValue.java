package eccrm.core.enums;

import java.io.Serializable;

/**
 * 枚举接口，用于获得枚举的值
 *
 * @author miles
 * @datetime 2014/4/2 11:14
 */
public interface EnumValue<T extends Serializable> {
    /**
     * 获得当前的枚举类型对应的值
     *
     * @return
     */
    T getValue();
}
