package com.ycrl.utils.decode;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 编码辅助类，提供将字符串使用各种编码进行解码的工具方法
 * Created by Michael on 2014/10/18.
 */
public class DecodeUtils {

    public static String utf8decode(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        try {
            return URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
