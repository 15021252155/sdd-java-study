package cn.com.sdd.study.thread.concurrent.sync.component;

import cn.com.sdd.common.DateUtil;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName CountDownLatchDemo
 * @Author suidd
 * @Description CountDownLatch示例
 * CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。
 * <p>
 * 比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了。
 * @Date 11:12 2020/5/5
 * @Version 1.0
 **/
public class CountDownLatchDemo {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(2);
        new Thread("Thread A") {
            @Override
            public void run() {
                try {
                    System.out.println("子线程" + Thread.currentThread().getName() + "正在执行，时间" + DateUtil.getNowTime());
                    Thread.sleep(3000);
                    System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕，时间" + DateUtil.getNowTime());
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("Thread B") {
            @Override
            public void run() {
                try {
                    System.out.println("子线程" + Thread.currentThread().getName() + "正在执行，时间" + DateUtil.getNowTime());
                    Thread.sleep(3000);
                    System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕，时间" + DateUtil.getNowTime());
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println("等待2个子线程执行完毕...");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2个子线程执行完毕.");
        System.out.println("继续执行主线程");
        /*
        在上面的例子中，主线程就是调用线程，主线中设置了CountDownLatch的值为2，并启动两个线程，每个线程执行完成之后将CountDownLatch减1，

        最后主线程中调用了latch.await()。此时主线程就会等到CountDownLatch值为0时才能继续往下执行。也是说，必须等到两个线程执行完成之后，才能执行。

        需要注意的是，如果CountDownLatch设置的值大于2的话，那么主线程就会一直等待下去，因为CountDownLatch的值即使减去2次，还是大于0，主线程只能一直等待。

        如果不想一直等待下去，可以调用其

        public boolean await(long timeout, TimeUnit unit)

        返回true表示指定时间内所有线程执行完，返回false表示指定时间内，所有线程没有执行完。

         */
    }
}
