package com.ycrl.core.hibernate.validator;

import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Michael
 */
public class ValidatorUtils {

    /**
     * 验证指定对象是否满足约束条件
     * @param obj    要验证的对象
     * @param groups 可选的组
     * @throws IllegalArgumentException 当验证失败后将会抛出该异常
     */
    public static <T> void validate(T obj, Class<?>... groups) {
        Validator validator = ValidatorHelper.getInstance().getValidator();
        Set<ConstraintViolation<T>> errors = validator.validate(obj, groups);
        if (errors.size() > 0) {
            ConstraintViolation<T> errorField = errors.iterator().next();
            Assert.isTrue(false, String.format("[%s]%s", errorField.getPropertyPath().toString(), errorField.getMessage()));
        }
    }
}
