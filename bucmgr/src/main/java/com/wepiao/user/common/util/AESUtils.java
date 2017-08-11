package com.wepiao.user.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密工具类
 * @author Jin Song
 *
 */
public class AESUtils {

    //加密用的种子
    private static final String  SEED = "198003151983072920150126";
    //KeyGenerator 提供对称密钥生成器的功能，支持各种算法
    private static KeyGenerator  keygen;
    //SecretKeySpec 保存对称密钥
    private static SecretKeySpec key;
    //Cipher
    private static Cipher        c;

    static {
        try {
            keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(SEED.getBytes()));
            SecretKey secretKey = keygen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key = new SecretKeySpec(enCodeFormat, "AES");
            c = Cipher.getInstance("AES");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /**
     * 具体操作，加密或者解密
     * @param text 加密或解密的正文
     * @param opMode 加密或者解密的模式
     * @throws NoSuchAlgorithmException
     */
    private static byte[] op(byte[] text, int opMode) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException {
        c.init(opMode, key);
        return c.doFinal(text);
    }

    /**
     * 对字符串加密
     * 
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     * @throws UnsupportedEncodingException 
     */
    public static byte[] encryt(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException, UnsupportedEncodingException {
        // ENCRYPT_MODE表示加密模式
        return op(str.getBytes("UTF-8"), Cipher.ENCRYPT_MODE);
    }

    /**
     * 对字符串解密
     * 
     * @param buff
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    public static byte[] decrypt(byte[] buff) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        // DECRYPT_MODE表示解密模式
        return op(buff, Cipher.DECRYPT_MODE);
    }
}
