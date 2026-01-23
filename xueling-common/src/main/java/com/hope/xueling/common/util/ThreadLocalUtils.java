package com.hope.xueling.common.util;
/**
 * 线程本地变量工具类
 * 提供线程本地变量的存取和移除功能
 * @author 谢光湘
 * @since 2026/1/22
 */
public class ThreadLocalUtils {
    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    @SuppressWarnings("unchecked")
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }

    public static  void set(Object value){
        THREAD_LOCAL.set(value);
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
