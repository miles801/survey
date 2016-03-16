package eccrm.core.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by miles on 2014/4/2.
 * 状态码注解：表示该类中的某些属性是使用枚举的方式,与EnumSymbol作用类似，只是起一个标识作用
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Deprecated
public @interface StatusCode {
}
