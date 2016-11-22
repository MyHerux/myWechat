package com.xu.myWechat.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;

/**
 * @author xu
 * @version 1.0
 */
public class Aes {
    private Key key;
    /**
     * AES CBC模式使用的Initialization Vector
     */
    private IvParameterSpec iv;
    /**
     * Cipher 物件
     */
    private Cipher cipher;

    /**
     * 构造方法
     *
     * @param strKey 密钥
     */
    public Aes(String strKey) {
        try {
            this.key = new SecretKeySpec(getHash("MD5", strKey), "AES");
            this.iv = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0});
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 加密方法
     * <p>
     * 说明：采用128位
     *
     * @return 加密结果
     */
    public String encrypt(String strContent) {
        try {
            byte[] data = strContent.getBytes("UTF-8");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encryptData = cipher.doFinal(data);
            return new String(Base64.encodeBase64(
                    encryptData), "UTF-8");
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * @param algorithm
     * @param text
     * @return
     */
    private static byte[] getHash(String algorithm, String text) {
        try {
            byte[] bytes = text.getBytes("UTF-8");
            final MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(bytes);
            return digest.digest();
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
