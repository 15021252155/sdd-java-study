package cn.com.sdd.study.thread.concurrent.sync.thread.pool;

import cn.com.sdd.common.DateUtil;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author suidd
 * @name ScheduledThreadPoolExecutorDemo
 * @description ScheduledThreadPoolExecutor是ThreadPoolExecutor的子类，同时实现了ScheduledExecutorService接口，
 * 它可另行安排在给定的延迟后运行任务(Callable，Runnable)，或者定期执行命令。此类要优于 Timer。
 * 虽然此类继承自 ThreadPoolExecutor，但是几个继承的调整方法对此类并无作用。特别是，因为它作为一个使用 corePoolSize 线程和一个无界队列的固定大小的池
 * ，所以调整 maximumPoolSize 没有什么效果。
 * @date 2020/5/6 15:23
 * Version 1.0
 **/
public class ScheduledThreadPoolExecutorDemo {
    private static final int corePoolSize = 5;
    private static final TimeUnit unit = TimeUnit.SECONDS;
    private static ScheduledThreadPoolExecutor scheduledThreadPool = new ScheduledThreadPoolExecutor(corePoolSize);

    public static void main(String[] args) {
        System.out.println("主程序开始...时间：" + DateUtil.getNowTime());
        //指定延迟时间执行
        testSchedule();
        //考虑任务执行时间-延迟10秒执行，之后每2秒执行一次
        //自动校正任务的执行时间，也就是说，如果任务执行了1秒，那么再过1秒就会执行，如果任务执行了1. 5秒，那么再过0. 5秒就会执行
        testScheduleAtFixedRate();
        //不考虑任务执行时间 ，总是在上一次任务执行完成之后，再延迟指定时间进行执行-延迟10秒执行，之后每2秒执行一次
        testScheduleWithFixedDelay();
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description 指定延迟时间执行-任务提交时间，延迟10秒执行
     * @date 2020/5/6 15:30
     **/
    public static void testSchedule() {
        System.out.println("testSchedule    提交时间： " + DateUtil.getNowTime());
        scheduledThreadPool.schedule(() -> System.out.println("testSchedule run ...时间：" + DateUtil.getNowTime())
                , 10, unit);
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //考虑任务执行时间-延迟10秒执行，之后每2秒执行一次
     * @date 2020/5/6 15:32
     **/
    public static void testScheduleAtFixedRate() {
        System.out.println("testScheduleAtFixedRate     提交时间：" + DateUtil.getNowTime());
        scheduledThreadPool.scheduleAtFixedRate(() -> {
            try {
                //自动校正任务的执行时间，也就是说，如果任务执行了1秒，那么再过1秒就会执行，如果任务执行了1. 5秒，那么再过0. 5秒就会执行
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("testScheduleAtFixedRate run ...时间：" + DateUtil.getNowTime());
        }, 10, 2, unit);
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //不考虑任务执行时间 ，总是在上一次任务执行完成之后，再延迟指定时间进行执行-延迟10秒执行，之后每2秒执行一次
     * @date 2020/5/6 15:56
     **/
    public static void testScheduleWithFixedDelay() {
        System.out.println("testScheduleWithFixedDelay    提交时间： " + DateUtil.getNowTime());
        scheduledThreadPool.scheduleWithFixedDelay(() -> {
            try {
                //不考虑任务执行时间，任务执行间隔=delay+任务执行时间
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("testScheduleWithFixedDelay.run...时间：" + DateUtil.getNowTime());
        }, 10, 2, unit);
    }
}