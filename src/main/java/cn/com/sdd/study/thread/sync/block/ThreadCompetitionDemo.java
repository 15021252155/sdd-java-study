package cn.com.sdd.study.thread.sync.block;

import java.util.Arrays;

/**
 * @ClassName ThreadCompetitionDemo
 * @Author suidd
 * @Description 线程竞争
 * @Date 22:20 2020/5/3
 * @Version 1.0
 **/
public class ThreadCompetitionDemo {
    /*
    Java同步代码块(synchronized block)和锁是用来避免多个线程对共享资源产生竞争，导致运行结果与期望不符合的一种机制。

    同步代码块和锁是Java并发编程中最最核心的概念，也是最容易让读者迷惑的地方，一些有了一些开发经验的用户，可能对此也没有彻底的理解。

    在本章中，我们将会详细的讲解每一个知识点，以最简化的方式，帮助读者理解每一个概念，并介绍相关术语。
     */

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 5000000; i++) {
                    count++;
                }
                System.out.println("自定义线程:计算完成...，耗时" + (System.currentTimeMillis() - start));
            }
        };
        thread.start();
        for (int i = 0; i < 5000000; i++) {
            count++;
        }
        System.out.println("主线程:计算完成....，耗时" + (System.currentTimeMillis() - start));
        Thread.sleep(1000);
        System.out.println("count = " + count);
    }
}
