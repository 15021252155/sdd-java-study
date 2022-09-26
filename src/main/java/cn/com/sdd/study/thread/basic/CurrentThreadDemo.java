package cn.com.sdd.study.thread.basic;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 当前线程demo
 */
public class CurrentThreadDemo {
    public static void main(String[] args) {
        new Thread("custom thread"){
            @Override
            public void run() {
                System.out.println("当前线程："+Thread.currentThread().getName());
            }
        }.start();

        System.out.println("当前线程："+Thread.currentThread().getName());


    }
}
