package cn.com.sdd.study.concurrent.unsafedemo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author suidd
 * @name OffHeapArray
 * @description 堆外创建一个巨大的int数组，进行数据存储、读取demo
 * @date 2020/5/20 17:34
 * Version 1.0
 **/
public class OffHeapArray {
    // 一个int等于4个字节
    private static final int INT = 4;
    private long size;
    private long address;

    private static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 构造方法，分配内存
    public OffHeapArray(long size) {
        this.size = size;
        // 参数字节数
        address = unsafe.allocateMemory(size * INT);
    }

    // 获取指定索引处的元素
    public int get(long i) {
        return unsafe.getInt(address + i * INT);
    }

    // 设置指定索引处的元素
    public void set(long i, int value) {
        unsafe.putInt(address + i * INT, value);
    }

    // 元素个数
    public long size() {
        return size;
    }

    // 释放堆外内存
    public void freeMemory() {
        unsafe.freeMemory(address);
    }

    public static void main(String[] args) {
        OffHeapArray offHeapArray = new OffHeapArray(4);
        offHeapArray.set(0, 1);
        offHeapArray.set(1, 2);
        offHeapArray.set(2, 3);
        offHeapArray.set(3, 4);
        offHeapArray.set(2, 5); // 在索引2的位置重复放入元素

        int sum = 0;
        for (int i = 0; i < offHeapArray.size(); i++) {
            sum += offHeapArray.get(i);
        }

        // 打印12
        System.out.println(sum);
        //将内存释放回操作系统
        offHeapArray.freeMemory();
    }
}