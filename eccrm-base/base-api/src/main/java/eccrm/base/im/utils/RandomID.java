package eccrm.base.im.utils;

import java.util.Random;

/**
 * 用于从A-Za-z0-9中随机产生一个长度的字符串
 *
 * @author Michael
 */
public class RandomID {
    private static final String idIndex = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 产生一个指定长度的字符串（如果希望作为数据库的表的唯一主键ID，推荐长度为8）
     * 注意：第一个字符串只会是字母
     *
     * @param length 字符串的长度，大于1，小于1000
     * @return 随机字符串
     */
    public static String generate(int length) {
        if (length < 1) {
            return "";
        }
        if (length > 1000) {
            throw new RuntimeException("长度超出限制!");
        }
        StringBuilder builder = new StringBuilder(length);
        Random random = new Random();
        builder.append(idIndex.charAt(random.nextInt(52))); // 第一个字符只能是数字
        for (int i = 1; i < length; i++) {
            builder.append(idIndex.charAt(random.nextInt(62)));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(generate(6));
        System.out.println(generate(10));
        System.out.println(generate(12));
        System.out.println(generate(16));
        System.out.println(generate(32));
    }
}
