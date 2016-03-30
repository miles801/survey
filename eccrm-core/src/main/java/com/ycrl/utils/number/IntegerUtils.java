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

    /**
     * 判断前面的数字是否大于后面的数字
     * 如果数字为null，则默认转换成0进行比较
     *
     * @param big
     * @param small
     * @return
     */
    public static boolean isBigger(Integer big, Integer small) {
        if (big == null) {
            big = 0;
        }
        if (small == null) {
            small = 0;
        }
        return big > small;
    }

    /**
     * 判断前面的数字是否小于后面的数字
     *
     * @param big
     * @param small
     * @return
     */
    public static boolean isSmaller(Integer big, Integer small) {
        return !isBigger(big, small);
    }

    /**
     * 是否相等，如果参数为null，则转换成0继续比较
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean nullEqual(Integer a, Integer b) {
        if (a == null) {
            a = 0;
        }
        if (b == null) {
            b = 0;
        }
        return a.equals(b);
    }
}
