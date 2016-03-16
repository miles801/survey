package eccrm.core.enums;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author miles
 * @datetime 2014/4/2 11:22
 */
public class EnumHelper {
    private static Logger logger = Logger.getLogger(EnumHelper.class);

    /**
     * 检查指定对象的可映射枚举的属性的值是否在允许的枚举范围内
     * 一旦有一个属性的值不在枚举范围内，则返回false，否则全部属性都正确才会返回true
     * true表示在范围内，false表示不在范围内
     * 如果value为空，则抛出无效参数异常
     *
     * @param obj
     * @return
     */
    public static <T extends EnumSymbol> boolean checkEnum(T obj) {
        return checkEnum(obj, false);
    }

    /**
     * 检查指定对象的可映射枚举的属性的值是否在允许的枚举范围内
     * 一旦有一个属性的值不在枚举范围内，则返回false，否则全部属性都正确才会返回true
     * true表示在范围内，false表示不在范围内
     * 如果throwException的值为true，当不在范围内时，将会抛出异常；否则将以日志的形式输出信息
     * 如果value为空，则抛出无效参数异常
     *
     * @param obj
     * @param throwException
     * @return
     */
    public static <T extends EnumSymbol> boolean checkEnum(T obj, boolean throwException) {
        if (obj == null) {
            throw new IllegalArgumentException("The obj which will be checked should not be null!");
        }
        //遍历所有的属性
        Field[] fields = FieldUtils.getAllFields(obj.getClass());
        for (Field field : fields) {
            //判断属性是否有EnumClass注解，取出注解的值，并取出属性的值
            EnumClass enumClass = field.getAnnotation(EnumClass.class);
            if (enumClass == null) continue;
            Class<?> clazz = enumClass.value();
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(false);
            }
            if (value == null) continue;
            Object[] e = clazz.getEnumConstants();
            boolean inRange = false;
            for (Object o : e) {
                if (EnumValue.class.isInstance(o)) {
                    EnumValue bar = (EnumValue) o;
                    Object bv = bar.getValue();
                    if (bv == value || bv.equals(value)) {
                        inRange = true;
                        break;//继续判断其他的属性
                    }
                }
            }
            if (!inRange) {
                String msg = obj.getClass().getName() + "-->field:" + field.getName() + "的值[" + value + "]不在[" + clazz.getName() + "]枚举允许的范围内!";
                if (throwException) {
                    throw new EnumException(msg);
                }
                logger.error(msg);
                return false;//属性有值，且不在枚举范围内，则返回false
            }
        }
        return true;
    }

    /**
     * 检查具体的某一个值是否在指定的枚举范围内
     * 两个参数均不能为空，否则将会抛出异常
     *
     * @param clazz
     * @param value
     * @return
     */
    public static boolean checkEnum(Class<? extends Enum<? extends EnumValue>> clazz, Object value) {
        if (clazz == null || value == null) {
            throw new IllegalArgumentException("All parameters should not be null!");
        }
        Object[] e = clazz.getEnumConstants();
        for (Object o : e) {
            if (EnumValue.class.isInstance(o)) {
                EnumValue bar = (EnumValue) o;
                Object bv = bar.getValue();
                if (bv == value || bv.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据枚举类型和枚举的值获得对应的枚举对象
     * 如果值不在范围内，则返回null
     * 如果任意参数为空，则抛出java.lang.IllegalArgumentException()异常
     *
     * @param clazz
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends Enum<? extends EnumValue>> T getEnum(Class<T> clazz, Serializable value) {
        if (clazz == null) {
            throw new IllegalArgumentException("枚举类型不能为空!");
        }
        if (value == null) {
            throw new IllegalArgumentException("枚举的值不能为空!");
        }
        T[] enums = clazz.getEnumConstants();

        if (enums.length < 1) return null;
        for (T foo : enums) {
            EnumValue v = (EnumValue) foo;
            if (v.getValue().equals(value)) return foo;
        }
        return null;
    }
}
