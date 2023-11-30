package com.css.common.util.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256加密算法
 * Created by kids on 2019/6/24.
 */
public class SHA256 {

    private static final Logger logger = LoggerFactory.getLogger(SHA256.class);
    /**
     * SHA256 加密算法
     */
    public static String sha256(String str) {
        String encodeStr = "";
        byte[] bt = str.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bt);
            encodeStr = byteArrayToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA256加密出错:{}", e);
        }
        return encodeStr;
    }

    /**
     * HMAC-SHA256加密
     * @param str 加密内容
     * @param secret 秘钥
     * @return String
     */
    public static String hmacSHA256(String str, String secret) {
        String hash = "";
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("utf-8"), "HmacSHA256");
            mac.init(secretKey);
            byte[] bytes = mac.doFinal(str.getBytes("utf-8"));
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            logger.error("Error HmacSHA256 ------> {}", e.getMessage());
        }
        return hash;
    }

    public static String byteArrayToHexString(byte[] bts) {
        StringBuffer sb = new StringBuffer();
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String password = "1";
        System.out.println("SHA256加密:{} " + SHA256.sha256(password));
    }
}
