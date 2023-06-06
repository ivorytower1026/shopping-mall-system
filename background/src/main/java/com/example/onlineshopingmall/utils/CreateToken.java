package com.example.onlineshopingmall.utils;

import java.util.Random;

public class CreateToken {
    public static String token(String phone){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        String code ="qwertyuiopasdfghjklzxcvmbnQWERTYUIOPALKSJDFGHMNBVCXZ0987654321";
        for (int i = 0; i < phone.length(); i++) {
            String s1 = phone.substring(i, i + 1);
            for (int j = 0; j <  random.nextInt(10); j++) {
                int i1 = random.nextInt(code.length());
                String s2 = code.substring(i1, i1 + 1);
                stringBuilder.append(s2);
            }
            stringBuilder.append(s1);
        }
        return stringBuilder.toString();
    }
}
