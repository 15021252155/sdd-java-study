package cn.com.sdd.study.concurrent.unsafedemo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author suidd
 * @name UnsafeTest
 * @description 使用Unsafe操作堆外内存
 * @date 2020/5/20 16:35
 * Version 1.0
 **/
public class UnsafeTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //获取Unsafe实例
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);//设置为true，通过反射获取私有变量的时候，会忽略访问修饰符的检查
        Unsafe unsafe = (Unsafe) f.get(null);
        //直接操作堆外内存
        unsafe.allocateMemory(100);//分配一块内存空间
        unsafe.reallocateMemory(100, 200);//重新分配一块内存
        unsafe.freeMemory(100);// 释放内存
    }
}
