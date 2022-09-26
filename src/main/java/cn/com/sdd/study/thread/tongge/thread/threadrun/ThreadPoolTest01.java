package cn.com.sdd.study.thread.tongge.thread.threadrun;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author suidd
 * @name ThreadPoolTest01
 * @description 线程池中普通任务执行的流程
 * @date 2020/6/1 13:42
 * Version 1.0
 **/
public class ThreadPoolTest01 {
    // 新建一个线程池
    // 核心数量为5，最大数量为10，空闲时间为1秒，队列长度为5，拒绝策略打印一句话
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(), (Runnable r, ThreadPoolExecutor executor) -> System.out.println(currentThreadName() + ", discard task"));

        /**
         * 观察i值的打印信息，先是打印了0~4，再打印了10~14，最后打印了5~9，竟然不是按顺序打印的，为什么呢？
         *
         * 线程池的参数：核心数量5个，最大数量10个，任务队列5个。
         *
         * 答：执行前5个任务执行时，正好还不到核心数量，所以新建核心线程并执行了他们；
         *
         * 执行中间的5个任务时，已达到核心数量，所以他们先入队列；
         *
         * 执行后面5个任务时，已达核心数量且队列已满，所以新建非核心线程并执行了他们；
         *
         * 再执行最后5个任务时，线程池已达到满负荷状态，所以执行了拒绝策略。
         */
        IntStream.range(0, 20)
                .forEach(i ->
                        threadPool.execute(() -> {
                            System.out.println(currentThreadName() + ", " + i + " running, " + System.currentTimeMillis());
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        })
                );

    }

    private static String currentThreadName() {
        return Thread.currentThread().getName();
    }
}