package eccrm.core.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by miles on 2014/4/2.
 * 用于标注属性的值对应指定枚举类型
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumClass {
    /**
     * 该值必须是EnumValue的子类，且必须是枚举类型
     *
     * @return
     */
    public Class<? extends Enum<? extends EnumValue>> value();
}
