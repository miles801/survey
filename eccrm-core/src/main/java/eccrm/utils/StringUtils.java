package eccrm.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author miles
 * @datetime 2014/4/16 16:55
 */
public class StringUtils {
    /**
     * 判断一个字符串是否为空或者空字符串
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean hasEmpty(String... str) {
        if (str == null || str.length == 0) {
            throw new IllegalArgumentException("要判断为空的字符串数组不能为空!");
        }
        for (String foo : str) {
            if (isEmpty(foo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字节转字符串
     */
    public static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        return new String(tempArr);
    }

    /**
     * 将字节数组转换为十六进制字符串
     */
    public static String byteToStr(byte[] byteArray) {
        StringBuilder buffer = new StringBuilder();
        for (byte aByteArray : byteArray) {
            buffer.append(byteToHexStr(aByteArray));
        }
        return buffer.toString();
    }

    /**
     * 判断两个字符串是否相等
     */
    public static boolean equals(String src, String dest) {
        if (src == dest) return true;

        if (isEmpty(src) || isEmpty(dest)) {
            return false;
        }

        return src.trim().equals(dest.trim());
    }

    /**
     * 将一个字符串使用utf-8进行两次解码
     */
    public static String decodeByUTF8(String str) {
        if (isEmpty(str)) return str;
        try {
            return URLDecoder.decode(URLDecoder.decode(str, "utf-8"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("无法解析的字符串或错误的编码!" + str);
    }

    /**
     * 将一个字符串使用iso-8859-1解码，然后使用utf-8编码
     */
    public static String encodeByUTF8(String str) {
        if (isEmpty(str)) return null;
        try {
            return new String(str.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("无法解析的字符串或错误的编码!" + str);
    }

    /**
     * 获得字符串的长度
     * 字母、英文标点一个长度
     * 汉字、中文标点、非法字符都认为2个字符
     */
    public static int getStringLength(String str) {
        int length = 0;
        byte[] bytes = null;
        for (char cn : str.toCharArray()) {
            try {
                bytes = (String.valueOf(cn)).getBytes("GBK");
            } catch (UnsupportedEncodingException ex) {
            }

            if (bytes == null || bytes.length > 2 || bytes.length <= 0) { //错误
                length += 2;
            } else if (bytes.length == 1) { //英文字符
                length++;
            } else if (bytes.length == 2) { //中文字符
                length += 2;
            }
        }
        return length;
    }

    public static boolean isAnyEmpty(String ...str) {
        if (str == null || str.length < 1) {
            return true;
        }
        for (String s : str) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }
}
