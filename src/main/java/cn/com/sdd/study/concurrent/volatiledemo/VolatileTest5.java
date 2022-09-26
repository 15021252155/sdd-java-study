package cn.com.sdd.study.concurrent.volatiledemo;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @author suidd
 * @name VolatileTest5
 * @description volatile不能保证原子性示例
 * @date 2020/5/22 16:15
 * Version 1.0
 **/
public class VolatileTest5 {
    public static volatile int counter = 0;

    public static void increment() {
        counter++;
    }

    /*
    这段代码中，我们起了100个线程分别对counter自增1000次，一共应该是增加了100000，但是实际运行结果却永远不会达到100000
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        IntStream.range(0, 100).forEach(i ->
                new Thread(() -> {
                    IntStream.range(0, 1000).forEach(j -> increment());
                    countDownLatch.countDown();
                }).start());

        countDownLatch.await();

        System.out.println(counter);
    }
}