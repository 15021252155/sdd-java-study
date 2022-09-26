package cn.com.sdd.study.thread.tongge.thread.threadrun;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * @author suidd
 * @name ThreadPoolTest02
 * @description 线程池中未来任务执行的流程
 * 我们定义一个线程池，并使用它提交5个任务，这5个任务分别返回0、1、2、3、4，在未来的某一时刻，我们再取用它们的返回值，做一个累加操作。
 * @date 2020/6/1 14:21
 * Version 1.0
 **/
public class ThreadPoolTest02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 新建一个固定5个线程的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futureList = new ArrayList<>(5);

        // 提交5个任务，分别返回0、1、2、3、4
        IntStream.range(0, 5)
                .forEach(i -> {
                    // 任务执行的结果用Future包装
                    Future<Integer> future = threadPool.submit(() -> {
                        Thread.sleep(1000);
                        System.out.println("return: " + i);
                        // 返回值
                        return i;
                    });
                    futureList.add(future);
                });

        int sum = 0;
        for (Future<Integer> f : futureList) {
            sum += f.get();
        }
        System.out.println("sum=" + sum);
    }
}