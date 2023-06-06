package com.example.onlineshopingmall.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;


public class MD5Encrypt {
    public static String encryptToMD5(String str) {
       String s1 = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
       return encrypt(s1);
    }
    private static String encrypt(String str){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int i1 = str.charAt(i);
            String s = i1+"e";
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
