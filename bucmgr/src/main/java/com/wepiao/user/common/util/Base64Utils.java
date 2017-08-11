package com.wepiao.user.common.util;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64 encode/decode 工具类
 * @author Jin Song
 *
 */
public class Base64Utils {
    /** 
     * 调用Apache的编码方法 
     */
    public static String encode(byte[] b) {
        if (b == null)
            return null;
        return new String((new Base64()).encode(b));
    }

    /** 
     * 调用Apache的解码方法 
     */
    public static byte[] decode(String s) {
        if (s == null)
            return null;
        return new Base64().decode(s.getBytes());
    }
}
