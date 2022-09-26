package cn.com.sdd.study.thread.sync.block;

import cn.com.sdd.study.thread.api.MyRunnable;

import java.util.Arrays;

/**
 * @ClassName NotThreadSafe
 * @Author suidd
 * @Description 线程不安全-对象成员
 * @Date 10:31 2020/5/4
 * @Version 1.0
 **/
public class NotThreadSafe {
    StringBuilder builder = new StringBuilder();

    public void add(String text) {
        this.builder.append(text);
    }

    public static void main(String[] args) {
        try {
            //对象成员存储在堆上。如果两个线程同时更新同一个对象的同一个成员，那这个代码就不是线程安全的。
            //如果两个线程同时调用同一个NotThreadSafe实例上的add()方法，就会有竞态条件问题
            NotThreadSafe sharedInstance = new NotThreadSafe();
            new Thread(new MyRunnableThreadSafe(sharedInstance)).start();
            new Thread(new MyRunnableThreadSafe(sharedInstance)).start();
            System.out.println(sharedInstance.builder);

            //如果这两个线程在不同的NotThreadSafe实例上调用add()方法，就不会导致竞态条件
            NotThreadSafe sharedInstance2 = new NotThreadSafe();
            NotThreadSafe sharedInstance3 = new NotThreadSafe();
            new Thread(new MyRunnableThreadSafe(sharedInstance2)).start();
            new Thread(new MyRunnableThreadSafe(sharedInstance3)).start();
            System.out.println(sharedInstance2.builder);
            System.out.println(sharedInstance3.builder);
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }
}

class MyRunnableThreadSafe implements Runnable {
    NotThreadSafe instance = null;

    public MyRunnableThreadSafe(NotThreadSafe instance) {
        this.instance = instance;
    }

    @Override
    public void run() {
        this.instance.add("some text");
    }
}
