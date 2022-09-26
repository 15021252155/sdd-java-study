package cn.com.sdd.study.list;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author suidd
 * @name UseCountdownLatch
 * @description
 * 我们举一个多线程中传递对象的例子。还是举生产者消费者的例子，在生产者中我们创建一个对象，在消费者中我们取出这个对象。
 * 先看一下用CountDownLatch该怎么做
 * 对比可以看 类 SynchronousQueueDemo 的实现方式，两者实现的功能一样
 * @date 2020/5/20 10:00
 * Version 1.0
 **/
@Slf4j
public class UseCountdownLatch {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicReference<Object> atomicReference= new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable producer = () -> {
            Object object=new Object();
            atomicReference.set(object);
            log.info("produced {}",object);
            countDownLatch.countDown();
        };

        Runnable consumer = () -> {
            try {
                countDownLatch.await();
                Object object = atomicReference.get();
                log.info("consumed {}",object);
            } catch (InterruptedException ex) {
                log.error(ex.getMessage(),ex);
            }
        };

        executor.submit(producer);
        executor.submit(consumer);

        try {
            executor.awaitTermination(50000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}