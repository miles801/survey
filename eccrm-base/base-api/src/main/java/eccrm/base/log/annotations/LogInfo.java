package eccrm.base.log.annotations;

import eccrm.base.log.OperateType;

import java.lang.annotation.*;

/**
 * @author Michael
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LogInfo {

    OperateType type() default OperateType.ADD;

    String describe() default "";
}
