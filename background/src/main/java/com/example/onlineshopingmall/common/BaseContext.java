package com.example.onlineshopingmall.common;

public class BaseContext {
    //创造一个静态的ThreadLocal对象,并声明泛型类
    private static ThreadLocal<String> threadLocal  = new ThreadLocal<>();
    //存入key值
    public static void setSessionId(String id){
        threadLocal.set(id);
    }
    //获取key值
    public static String getSessionId(){
        return threadLocal.get();
    }
}
