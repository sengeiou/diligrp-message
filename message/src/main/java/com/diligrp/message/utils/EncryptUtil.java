package com.diligrp.message.utils;

import cn.hutool.core.util.StrUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * <B>Description</B>
 * <B>Copyright:本软件源代码版权归农丰时代所有,未经许可不得任意复制与传播.</B>
 * <B>农丰时代科技有限公司</B>
 *
 * @author yuehongbo
 * @date 2019/4/11 9:55
 */
public class EncryptUtil {

    /**
     * MD5加密
     *
     * @param str
     * @return
     */
    public static String MD5HexString(String str) {
        return MD5.md5(str);
    }

    /**
     * URL加密
     *
     * @param s
     * @return
     */
    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    /**
     * URL解密
     *
     * @param s
     * @return
     */
    public static String urlDecode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }

    /**
     * MD5加密类
     *
     * @author yuehongbo
     */
    private static class MD5 {

        private static char md5Chars[] = { '0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        public static String md5(String str) {
            MessageDigest md5 = getMD5Instance();
            try {
                md5.update(str.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
            }
            byte[] digest = md5.digest();
            char[] chars = toHexChars(digest);
            return new String(chars);
        }

        private static MessageDigest getMD5Instance() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ignored) {
                throw new RuntimeException(ignored);
            }
        }

        private static char[] toHexChars(byte[] digest) {
            char[] chars = new char[digest.length * 2];
            int i = 0;
            for (byte b : digest) {
                char c0 = md5Chars[(b & 0xf0) >> 4];
                chars[i++] = c0;
                char c1 = md5Chars[b & 0xf];
                chars[i++] = c1;
            }
            return chars;
        }
    }

    /**
     * 通过参数生成签名信息
     *
     * @param paramsMap
     * @return
     */
    public static String getSign(Map<String, String> paramsMap, String secretKey) {
        if (StrUtil.isEmpty(secretKey)) {
            throw new RuntimeException("密钥不能为空");
        }
        String[] keys = paramsMap.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            sb.append(key);
            sb.append(paramsMap.get(key));
        }
        sb.append(secretKey);
        return MD5HexString(sb.toString());
    }


    /** */
    /**
     * DES加密
     *
     * @param plainText
     *            明文
     * @param key
     *            密钥
     * @return
     * @author yayagepei
     * @date 2008-10-8
     */
    public static String encoderByDES(String plainText, String key) {
        try {
            byte[] result = coderByDES(plainText.getBytes("UTF-8"), key,
                    Cipher.ENCRYPT_MODE);
            return byteArr2HexStr(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * DES解密
     * @param secretText
     *            密文
     * @param key
     *            密钥
     * @return
     * @author yayagepei
     * @date 2008-10-8
     */
    public static String decoderByDES(String secretText, String key) {
        try {
            byte[] result = coderByDES(hexStr2ByteArr(secretText), key,
                    Cipher.DECRYPT_MODE);
            return new String(result, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * DES加解密
     *
     * @param plainText
     *            要处理的byte[]
     * @param key
     *            密钥
     * @param mode
     *            模式
     * @return
     * @author yayagepei
     * @date 2008-10-8
     */
    private static byte[] coderByDES(byte[] plainText, String key, int mode)
            throws Exception{
        SecureRandom sr = new SecureRandom();
        byte[] resultKey = makeKey(key);
        DESKeySpec desSpec = new DESKeySpec(resultKey);
        SecretKey secretKey = SecretKeyFactory.getInstance("DES")
                .generateSecret(desSpec);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(mode, secretKey, sr);
        return cipher.doFinal(plainText);
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * @param strIn
     *            需要转换的字符串
     * @return 转换后的byte数组
     * @throws NumberFormatException
     */
    private static byte[] hexStr2ByteArr(String strIn)
            throws NumberFormatException {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 生产8位的key
     * @param key
     *            字符串形式
     * @return
     * @throws UnsupportedEncodingException
     * @author yayagepei
     * @date 2008-10-8
     */
    private static byte[] makeKey(String key)
            throws UnsupportedEncodingException {
        byte[] keyByte = new byte[8];
        byte[] keyResult = key.getBytes("UTF-8");
        for (int i = 0; i < keyResult.length && i < keyByte.length; i++) {
            keyByte[i] = keyResult[i];
        }
        return keyByte;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     * @param arrB
     *            需要转换的byte数组
     * @return 转换后的字符串
     */
    private static String byteArr2HexStr(byte[] arrB){
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
}
