package com.ycrl.utils.number;

/**
 * @author Michael
 */
public class IntegerUtils {

    /**
     * 多个Integer相加，如果为空则跳过
     */
    public static int add(Integer... numbers) {
        int i = 0;
        for (Integer n : numbers) {
            if (n != null) {
                i += n;
            }
        }
        return i;
    }

    /**
     * 两个数相乘，如果任意一个为空，则返回0
     */
    public static int multi(Integer a, Integer b) {
        if (a == null || b == null) {
            return 0;
        }
        return a * b;
    }

}
