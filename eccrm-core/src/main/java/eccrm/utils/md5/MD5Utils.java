package eccrm.utils.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by miles on 13-11-15.
 */
public class MD5Utils {
    public static String encode(String code) {
        if (code == null || "".equals(code.trim())) {
            throw new RuntimeException("the code which will be encoded with 'MD5' must not be null or empty!");
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(code.getBytes()); //MD5加密算法只是对字符数组而不是字符串进行加密计算，得到要加密的对象
        byte[] bs = md.digest();   //进行加密运算并返回字符数组
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bs.length; i++) {    //字节数组转换成十六进制字符串，形成最终的密文
            int v = bs[i] & 0xff;
            if (v < 16) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
}
