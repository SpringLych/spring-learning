package com.example.demo.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author LiYingChun
 * @date 2019/8/1
 * MD5加密相关
 */
public class MD5Util {
    private static final String SALT = "mybird";
    private static final String ALGORITHM_NAME = "md5";
    private static final int HASH_ITERATIONS = 2;

    /**
     * 加密密码
     *
     * @return MD5加密后的密码
     */
    public static String encrypt(String pswd) {
        return new SimpleHash(ALGORITHM_NAME, pswd,
                ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
    }

    public static String encrypt(String username, String pswd) {
        return new SimpleHash(ALGORITHM_NAME, pswd,
                ByteSource.Util.bytes(username + SALT), HASH_ITERATIONS).toHex();
    }

 /*   public static void main(String[] args) {
        System.out.println(encrypt("mrbird", "123456"));
    }*/
}
