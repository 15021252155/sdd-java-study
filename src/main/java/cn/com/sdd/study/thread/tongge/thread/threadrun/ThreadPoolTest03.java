package cn.com.sdd.study.thread.tongge.thread.threadrun;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author suidd
 * @name ThreadPoolTest03
 * @description 线程池执行定时任务demo
 * 实现定时任务有两个问题要解决，分别是指定未来某个时刻执行任务、重复执行。
 * <p>
 * （1）指定某个时刻执行任务，是通过延时队列的特性来解决的；
 * <p>
 * （2）重复执行，是通过在任务执行后再次把任务加入到队列中来解决的。
 * @date 2020/6/1 15:29
 * Version 1.0
 **/
public class ThreadPoolTest03 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建一个定时线程池
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("start: " + sdf.format(new Date()));

        // 执行一个无返回值任务，5秒后执行，只执行一次
        scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("spring: " + sdf.format(new Date()));
        }, 5, TimeUnit.SECONDS);

        // 执行一个有返回值任务，5秒后执行，只执行一次
        ScheduledFuture<String> future = scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("inner summer: " + sdf.format(new Date()));
            return "outer summer: ";
        }, 5, TimeUnit.SECONDS);
        // 获取返回值
        System.out.println(future.get() + sdf.format(new Date()));

        // 按固定频率执行一个任务，每2秒执行一次，1秒后执行
        // 任务开始时的2秒后
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            System.out.println("autumn: " + sdf.format(new Date()));
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }, 1, 2, TimeUnit.SECONDS);

        // 按固定延时执行一个任务，每延时2秒执行一次，1秒执行
        // 任务结束时的2秒后
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            System.out.println("winter: " + sdf.format(new Date()));
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }, 1, 2, TimeUnit.SECONDS);

    }
}