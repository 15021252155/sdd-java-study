package cn.com.sdd.study.thread.api;

import cn.com.sdd.common.SleepUtils;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadState
 * @Author suidd
 * @Description 线程的状态
 * @Date 20:37 2020/5/3
 * @Version 1.0
 **/
public class ThreadState {
    /*
        一个Thread对象可以有多个状态，在java.lang.Thread.State中，总共定义六种状态：

        1、NEW

        线程刚刚被创建，也就是已经new过了，但是还没有调用start()方法

        2、RUNNABLE

        RUNNABLE这个名字很具有欺骗性，很容易让人误以为处于这个状态的线程正在运行。事实上，这个状态只是表示，线程是可运行的。

        我们已经无数次提到过，一个单核CPU在同一时刻，只能运行一个线程。

        3、BLOCKED

        线程处于阻塞状态，正在等待一个monitor lock。通常情况下，是因为本线程与其他线程公用了一个锁。其他在线程正在使用这个锁进入某个synchronized同步方法块或者方法，而本线程进入这个同步代码块也需要这个锁，最终导致本线程处于阻塞状态。

        4、WAITING

        等待状态，调用以下方法可能会导致一个线程处于等待状态：

        Object.wait with no timeout

        Thread.join with no timeout

        LockSupport.park

        例如：对于wait()方法，一个线程处于等待状态，通常是在等待其他线程完成某个操作。本线程调用某个对象的wait()方法，其他线程处于完成之后，调用同一个对象的notify或者notifyAll()方法。

        Object.wait()方法只能够在同步代码块中调用。调用了wait()方法后，会释放锁。

        5、TIMED_WAITING

        线程等待指定的时间，对于以下方法的调用，可能会导致线程处于这个状态：

        Thread.sleep

        Object.wait with timeout

        Thread.join with timeout

        LockSupport.parkNanos

        LockSupport.parkUntil

        6、TERMINATED

        线程终止。

        这些状态中NEW状态是开始，TERMINATED时销毁，在整个线程对象的运行过程中，这个两个状态只能出现一次。其他任何状态都可以出现多次，彼此之间可以相互转换。

        Tips：

         经常有人把线程的状态又称为线程的生命周期。个人认为这种叫法并不好，因为生命周期通常指的是一个对象从创建到销毁所必须要经历的过程。

         例如J2EE的Servlet的生命周期方法是init->service->destroy，这三个阶段是必须要经历的。

         而对于线程而言，完全可以NEW了之后就直接到TERMINATED了，或者只经历其中某个状态而不是所有。因此这可能也是官方只称之为线程状态(用java.lang.Thread.State类表示)的原因。

         不过，因为生命周期这个叫法在某种程度上又有一些说明力，笔者也不反对你也这样来称呼线程的不同状态。

        总之，需要知道的一点是，别人和你提到线程状态或者是线程生命周期，其实指的是同一个东西。
         */
    public static void main(String[] args) {
        new Thread(new Running(), "RunningThread").start();
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread-1").start();
        new Thread(new Waiting(), "WaitingThread-2").start();
        // 使用两个Blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }

    /**
     * 表示线程正在运行
     *
     * @author TIANSHOUZHI336
     */
    static class Running implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < Long.MAX_VALUE; i++) {
                i++;
            }
        }
    }

    /**
     * 该线程不断的进行睡眠
     */
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }

    /**
     * 该线程在Waiting.class实例上等待
     */
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 该线程在Blocked.class实例上加锁后，不会释放该锁
     */
    static class Blocked implements Runnable {
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }
}
