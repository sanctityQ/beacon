package com.fusionspy.beacon.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocalHolder
 * User: ChengQi
 * Date: 13-5-24
 * Time: PM6:05
 * To change this template use File | Settings | File Templates.
 */
public abstract class ThreadLocalHolder {

    private static ThreadLocal<Map<String,Object>>  threadLocal = new ThreadLocal<Map<String,Object>>();

    private static Object lock = new Object();

    private ThreadLocalHolder(){}

    public static <T> T get(String key,Class<T> clazz){
        if(clazz == null)
            throw new IllegalArgumentException();
        synchronized (lock){
            if(threadLocal.get()==null){
                threadLocal.set(new HashMap<String,Object>());
            }
        }
        return (T)threadLocal.get().get(key);
    }

    public static  void set(String key,Object value){
        if(key==null||value ==null)
            throw new IllegalArgumentException();
        synchronized (lock){
            if(threadLocal.get()==null){
                threadLocal.set(new HashMap<String,Object>());
            }
        }
        threadLocal.get().put(key,value);
    }

    //使用例子
    public static void main(String args[]){
        ThreadLocalHolder.set("1","3333");
        System.out.println(ThreadLocalHolder.get("1",String.class));
    }

}
