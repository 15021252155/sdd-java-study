package cn.com.sdd.study;

import cn.com.sdd.study.thread.tongge.thread.myexecutor.DiscardRejectPolicy;
import cn.com.sdd.study.thread.tongge.thread.myexecutor.MyThreadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author suidd
 * @name MyThreadPoolExecutorTest
 * @description 自己实现一个线程池测试
 * @date 2020/5/29 13:52
 * Version 1.0
 **/
public class MyThreadPoolExecutorTest {
    /**
     * 我们分析下这段程序：
     * <p>
     * （1）先连续创建了5个核心线程，并执行了新任务；
     * <p>
     * （2）后面的15个任务进了队列；
     * <p>
     * （3）队列满了，又连续创建了5个线程，并执行了新任务；
     * <p>
     * （4）后面的任务就没得执行了，全部走了丢弃策略；
     * <p>
     * （5）所以真正执行成功的任务应该是 5 + 15 + 5 = 25 条任务；
     *
     * @param args
     */
    public static void main(String[] args) {
        Executor threadPool = new MyThreadPoolExecutor("test", 5, 10, new ArrayBlockingQueue<>(15), new DiscardRejectPolicy());
        AtomicInteger num = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("running: " + System.currentTimeMillis() + ": " + num.incrementAndGet());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}