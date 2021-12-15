package com.yiping.gao.common.utils.encrypt;

import com.yiping.gao.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @Author: 高一平
 * @Date: 2019/9/5 17:10
 * @Description: 加密算法
 **/
public class EncryptUtils {

    private static Logger logger = LoggerFactory.getLogger(EncryptUtils.class);
    public static String DEFAULT_CHARSET = "utf-8";
    private static final String SHA256 = "SHA-256";
    private static final String MD5 = "MD5";

    /**
     * 加密算法
     */
    private static Algorithm ALGORITHM = Algorithm.AES;
    /**
     * 加密模式
     */
    private static Model MODEL = Model.ECB;
    /**
     * 加密算法/加密模式/填充策略
     */
    private static String ALGORITHM_MODEL = ALGORITHM.name + "/" + MODEL.name + "/PKCS5Padding";

    /**
     * 加密算法类型枚举类
     */
    public enum Algorithm {
        AES("AES", 16),
        DES3("DESede", 24);
        String name;
        int secretKeyLength;

        Algorithm(String name, int secretKeyLength) {
            this.name = name;
            this.secretKeyLength = secretKeyLength;
        }
    }

    /**
     * 加密模式枚举类
     */
    public enum Model {
        ECB("ECB"),
        CBC("CBC");
        String name;

        Model(String name) {
            this.name = name;
        }
    }

    /**
     * SHA256加密
     * SHA（Secure Hash Algorithm）安全散列算法
     * 这种算法的特点是数据的少量更改会在Hash值中产生不可预知的大量更改
     * SHA256算法的hash值大小为256位
     *
     * @param input
     * @return
     */
    public static String encryptBySHA256(String input) {
        String result = encrypt(input, SHA256);
        return result;
    }

    /**
     * MD5加密
     *
     * @param input
     * @return
     */
    public static String encryptByMD5(String input) {
        String result = encrypt(input, MD5);
        return result;
    }

    /**
     * 加密
     *
     * @param input
     * @return
     */
    public static String encrypt(String input, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密算法和加密逻辑的设置
     *
     * @param algorithm
     * @param model
     */
    public static void setAlgorithmModel(Algorithm algorithm, Model model) {
        ALGORITHM = algorithm;
        MODEL = model;
        ALGORITHM_MODEL = algorithm.name + "/" + model.name + "/PKCS5Padding";
    }

    /**
     * 密码对象初始化
     *
     * @param key
     * @param cipherModel
     * @return
     */
    private static Cipher initCipher(String key, int cipherModel) {
        if (!StringUtils.isEmpty(key)) {
            try {
                //根据密码生成秘钥
                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), ALGORITHM.name);
                //获取加密对象
                Cipher cipher = Cipher.getInstance(ALGORITHM_MODEL);
                //初始化密码对象
                cipher.init(cipherModel, secretKeySpec);
                return cipher;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Cipher对象初始化失败");
            }
        } else {
            logger.error("密码为空");
            throw new IllegalArgumentException("密码不可为空");
        }
        return null;
    }

    /**
     * ASE加密
     *
     * @param text 明文
     * @param key  密码
     * @return
     */
    public static String aseEncrypt(String text, String key) {
        illegalTest(text, key);
        try {
            //初始化加密对象
            Cipher cipher = initCipher(key, Cipher.ENCRYPT_MODE);
            //加密
            byte[] aesEncode = cipher.doFinal(text.getBytes(DEFAULT_CHARSET));
            //Base64编码防止乱码
            String base64Encode = Base64.getEncoder().encodeToString(aesEncode);
            return base64Encode;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("加密失败");
        }
        return null;
    }

    /**
     * ASE解密
     *
     * @param text 密文
     * @param key  密码
     * @return
     */
    public static String aseDecrypt(String text, String key) {
        illegalTest(text, key);
        try {
            //base64解码
            byte[] base64Decode = Base64.getDecoder().decode(text.getBytes(DEFAULT_CHARSET));
            //初始化密码对象
            Cipher cipher = initCipher(key, Cipher.DECRYPT_MODE);
            //解码
            byte[] aesDecode = cipher.doFinal(base64Decode);
            //返回字符串
            return new String(aesDecode, DEFAULT_CHARSET);
        } catch (Exception e) {
            logger.error("解密失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密/解密合法性验证
     *
     * @param content   密文/明文
     * @param secretKey 密码
     */
    private static void illegalTest(String content, String secretKey) {
        if (StringUtils.isEmpty(secretKey)) {
            logger.error("密码为空");
            throw new IllegalArgumentException("密码不可为空");
        }

        if (secretKey.length() != ALGORITHM.secretKeyLength) {
            System.out.println(secretKey);
            logger.error("密码长度错误");
            throw new IllegalArgumentException("密码长度必须为" + ALGORITHM.secretKeyLength + "位");
        }

        if (StringUtils.isEmpty(content)) {
            logger.error("加密解密内容为空");
            throw new IllegalArgumentException("加密/解密内容不可为空");
        }
    }

}
