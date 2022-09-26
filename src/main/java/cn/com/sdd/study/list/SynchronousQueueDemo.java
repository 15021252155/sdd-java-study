package cn.com.sdd.study.list;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SynchronousQueueDemo
 * @Author suidd
 * @Description SynchronousQueue
 * SynchronousQueue是java并发包下无缓冲阻塞队列，它用来在两个线程之间移交元素
 * 使用SynchronousQueue是java并发包下无缓冲阻塞队列 进行多线程中传递对象的例子
 * @Date 18:31 2020/5/17
 * @Version 1.0
 **/
@Slf4j
public class SynchronousQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>();

        Runnable producer = () -> {
            Object object = new Object();
            try {
                synchronousQueue.put(object);
            } catch (InterruptedException ex) {
                log.error(ex.getMessage(), ex);
            }
            log.info("produced {}", object);
        };

        Runnable consumer = () -> {
            try {
                Object object = synchronousQueue.take();
                log.info("consumed {}", object);
            } catch (InterruptedException ex) {
                log.error(ex.getMessage(), ex);
            }
        };

        executor.submit(producer);
        executor.submit(consumer);

        executor.awaitTermination(50000, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
