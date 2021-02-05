package com.haiseer.html2pdf.util;

/*
 *
 * Copyright (c) 2001-2017 泛微软件.
 * 泛微协同商务系统,版权所有.
 *
 */

import cn.hutool.core.lang.Console;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES加密解密
 *
 * @author chengf
 */
public class RJDes {

    private String pwd = null;

    /**
     * 构造器
     */
    public RJDes() {

    }

    /**
     * 构造器
     *
     * @param pwd 密码
     */
    public RJDes(String pwd) {
        this.pwd = pwd;
    }

    /**
     * 将传入的明文转换为密文
     *
     * @param str 明文字符串
     * @return 加密后的密文
     */
    public String encode(String str) {
        byte[] result = null;
        try {
            if (pwd == null) {
                pwd = "ecology9";
            }
            DESKeySpec keySpec = new DESKeySpec(pwd.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteContent = str.getBytes();
            result = cipher.doFinal(byteContent);

        } catch (Exception e) {
            Console.error("加密异常！", e);
            return null;
        }

        return parseByte2HexStr(result);
    }

    /**
     * 将传入的密文转换为明文
     *
     * @param str 密文字符串
     * @return 解密后的明文
     */
    public String decode(String str) {
        byte[] result = null;
        byte[] content = parseHexStr2Byte(str);
        try {
            if (pwd == null) {
                pwd = "ecology9";
            }
            DESKeySpec keySpec = new DESKeySpec(pwd.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(content);

        } catch (Exception e) {
            Console.error("解密异常！", e);
            return null;
        }

        return new String(result);
    }

    /**
     * 设置加密和解密的密钥
     *
     * @param pwd 加密密钥
     * @return true：成功，false：失败
     */
    public boolean setPwd(String pwd) {
        this.pwd = pwd;
        return true;
    }

    /**
     * 设置加密和解密的向量
     *
     * @param iv 加密向量
     * @return true：成功，false：失败
     */
    public boolean setIv(String iv) {
        return true;
    }

    /**
     * 将二进制转换成十六进制
     *
     * @param buf
     * @return
     */
    private String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将十六进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 主程序
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        RJDes des = new RJDes();
        String content = "yxl";
        // DES的密钥长度必须是8位(小于8位则会报错，8位之后对加密结果不会产生影响)
        String password = "26ec7719b81d4fa7a9d9c992a13eed64";
        des.setPwd(password);
        // 加密
        System.out.println("加密前：" + content);
        String encodeResultStr = des.encode(content);
        System.out.println("加密后：" + encodeResultStr);
        //解密
        String decodeResultStr = des.decode(encodeResultStr);
        System.out.println("解密后：" + decodeResultStr);
    }

}

