package cn.YouWei.STD.robot.security;

import cn.YouWei.STD.robot.util.StringUtil;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;

/**
 * 加解密算法工具类 信息摘要算法(md5, sha1)是单向不可逆加密,多用于数字签名(字符流), 且是定长
 * Base64是用64个字符(特殊字符"+,/,=")替换明文, 严格说不是加密, 多用于混淆作用 DES是对称加密算法,加解密使用同一个密钥
 * RSA非对称加密算法,有公钥私钥两个密钥
 * 
 * @author tgf(Dec 30, 2010)
 * 
 */
public class EncryptUtil {
    private static final String MD5 = "md5";
    private static final String SHA1 = "sha1";
    private static final String DES = "DES";
    /** 加密私钥 */
    private static final byte[] PRIVATE_KEY = "r23dfs*%#?@%&**&&123".getBytes();
    /** 加密因子 */
    private static final String ENCRYPT_KEY_KICT = "0*&`K^^#d5f+\"d)d'f?d2@!d/ia1";
    /** DES加密使用的密码器 */
    private static Cipher desEncodeCipher;
    /** DES解密使用的密码器 */
    private static Cipher desDecodeCipher;

    /**
     * 使用md5算法单向加密字符串,返回定长32个字符
     * 
     * @param plaintext
     * @return
     */
    public static String md5(String plaintext) {
        if(StringUtil.isEmpty(plaintext)){
            return plaintext;
        }

        // 增加加密因子
        byte[] bytes = md5Bytes(plaintext + ENCRYPT_KEY_KICT);
        // 对返回的字符流不能直接new String(bytes)
        // 应该使用byte2hex实现
        return byte2hex(bytes);
    }

    /**
     * 使用sha1算法单向加密字符串,返回定长40个字符
     * 
     * @param plaintext
     * @return
     */
    public static String sha1(String plaintext) {
        if(StringUtil.isEmpty(plaintext)){
            return plaintext;
        }

        // 增加加密因子
        byte[] bytes = sha1Bytes(plaintext + ENCRYPT_KEY_KICT);
        // 对返回的字符流不能直接new String(bytes)
        // 应该使用byte2hex实现
        return byte2hex(bytes);
    }

    /**
     * 二进制字符流转字符串
     * 
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        if(b == null || b.length == 0){
            return null;
        }

        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for(int n = 0; n < b.length; n++){
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if(stmp.length() == 1){
                hs.append("0").append(stmp);
            }else{
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 跟byte2hex互逆
     * 
     * @param b
     * @return
     */
    private static byte[] hex2byte(byte[] bytes) {
        int len = bytes.length;
        // 字节数组长度应该偶数
        if(len % 2 != 0){
            return null;
        }

        byte[] b2 = new byte[len / 2];
        for(int n = 0; n < len; n += 2){
            String item = new String(bytes, n, 2);
            b2[n / 2] = (byte)Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 对字符串进行md5加密,返回字符流
     * 
     * @param plaintext
     * @return
     */
    public static byte[] md5Bytes(String plaintext) {
        return digest(plaintext, MD5);
    }

    /**
     * 对字符串进行sha1加密, 返回字符流
     * 
     * @param plaintext
     * @return
     */
    public static byte[] sha1Bytes(String plaintext) {
        return digest(plaintext, SHA1);
    }

    /**
     * 对字符进行信息摘要 返回字符流(bytes)
     * 
     * @param plaintext
     * @param type
     * @return
     */
    private static byte[] digest(String plaintext, String type) {
        try{
            byte[] plaintextbytes = plaintext.getBytes();
            MessageDigest alga = MessageDigest.getInstance(type);
            alga.update(plaintextbytes);
            return alga.digest();
        }catch (Exception e){           
            return null;
        }
    }

    /**
     * 异或加密
     * 
     * @param str
     * @param key
     * @return
     */
    public static String xorEncrypt(String str, String key) {
        BigInteger strbi = new BigInteger(str.getBytes());
        BigInteger keybi = new BigInteger(key.getBytes());
        BigInteger encryptbi = strbi.xor(keybi);

        return new String(encryptbi.toByteArray());
    }

    /**
     * 异或解密
     * 
     * @param encryptStr
     * @param key
     * @return
     */
    public static String xorDecrypt(String encryptStr, String key) {
        BigInteger encryptbi = new BigInteger(encryptStr.getBytes());
        BigInteger keybi = new BigInteger(key.getBytes());
        BigInteger decryptbi = encryptbi.xor(keybi);
        return new String(decryptbi.toByteArray());
    }

    /**
     * 对字符串进行base64编码
     * 
     * @param plain
     * @return
     */
    public static String base64encode(String plain) {
        if(StringUtil.isEmpty(plain)){
            return plain;
        }
        byte[] plaintextbytes = plain.getBytes();
        return base64encode(plaintextbytes);
    }

    /**
     * 对字符流(bytes)进行base64编码
     * 
     * @param bytes
     * @return
     */
    public static String base64encode(byte[] bytes) {
        try{
            byte[] encodedbytes = Base64.encodeBase64(bytes);
            return new String(encodedbytes);
        }catch (Exception e){           
            return null;
        }
    }

    /**
     * 对字符串进行base64解码
     * 
     * @param ciphertext
     * @return
     */
    public static String base64decode(String ciphertext) {
        if(StringUtil.isEmpty(ciphertext)){
            return ciphertext;
        }
        try{
            byte[] ciphertextbytes = ciphertext.getBytes();
            byte[] bytes = Base64.decodeBase64(ciphertextbytes);
            return new String(bytes);
        }catch (Exception e){            
            return null;
        }
    }

    /**
     * 根据字符串流的编码对字符串进行解码
     * 
     * @param ciphertext
     * @param encoding
     * @return
     */
    public static String base64decode(String ciphertext, String encoding) {
        if(StringUtil.isEmpty(ciphertext)){
            return ciphertext;
        }
        try{
            byte[] ciphertextbytes = ciphertext.getBytes();
            byte[] bytes = Base64.decodeBase64(ciphertextbytes);
            return new String(bytes, encoding);
        }catch (Exception e){           
            return null;
        }
    }

    /**
     * 使用DES对称加密算法加密字符串
     * 
     * @param plain
     * @return
     */
    public static String desEncode(String plain) {
        if(StringUtil.isEmpty(plain)){
            return plain;
        }
        try{
            Cipher cipher = getDESEncodeCipher();
            byte[] bytes = cipher.doFinal(plain.getBytes());
            return byte2hex(bytes);
        }catch (Exception e){            
            return null;
        }

    }

    /**
     * 使用DES对称加密算法解密字符串
     * 
     * @param cipher
     * @return
     */
    public static String desDecode(String ciphertext) {
        if(StringUtil.isEmpty(ciphertext)){
            return ciphertext;
        }
        try{
            byte[] hxBytes = hex2byte(ciphertext.getBytes());
            Cipher cipher = getDESDecodeCipher();
            return new String(cipher.doFinal(hxBytes));
        }catch (Exception e){            
            return null;
        }

    }

    /**
     * 获取DES加密使用的密码器
     * 
     * @return
     */
    private static Cipher getDESEncodeCipher() {
        if(desEncodeCipher == null){
            desEncodeCipher = initDESEncodeCipher();
        }
        return desEncodeCipher;
    }

    /**
     * 初始化DES加密使用的密码器
     * 
     * @return
     */
    private static Cipher initDESEncodeCipher() {
        try{
            Cipher ciper = Cipher.getInstance(DES);
            ciper.init(Cipher.ENCRYPT_MODE, getDESKey());
            return ciper;
        }catch (Exception e){            
            return null;
        }

    }

    /**
     * 得到DES解密使用的密码器
     * 
     * @return
     */
    private static Cipher getDESDecodeCipher() {
        if(desDecodeCipher == null){
            desDecodeCipher = initDESDecodeCipher();
        }
        return desDecodeCipher;
    }

    /**
     * 初始化DES解密使用的密码器
     * 
     * @return
     */
    private static Cipher initDESDecodeCipher() {
        try{
            Cipher ciper = Cipher.getInstance(DES);
            ciper.init(Cipher.DECRYPT_MODE, getDESKey());
            return ciper;
        }catch (Exception e){           
            return null;
        }
    }

    /**
     * 生成DES密钥
     * 
     * @return
     */
    private static Key getDESKey() {
        try{
            DESKeySpec spec = new DESKeySpec(PRIVATE_KEY);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            return keyFactory.generateSecret(spec);
        }catch (Exception e){            
            return null;
        }
    }

    
}
