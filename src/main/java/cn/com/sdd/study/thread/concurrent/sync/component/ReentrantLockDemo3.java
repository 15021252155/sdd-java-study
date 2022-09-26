package cn.com.sdd.study.thread.concurrent.sync.component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockDemo3
 * @Author suidd
 * @Description Lock接口的独占性演示
 * @Date 9:37 2020/5/5
 * @Version 1.0
 **/
public class ReentrantLockDemo3 { //两个线程竞争一个锁
    //从输出结果中可以看到，A获取到锁，先执行，而必须等到A执行完成之后，B才能执行。因此Lock对象的实际上是一个独占锁。
    //ReentrantLock 默认实现非公平锁
    //构造函数参数为为空或false--表示实现非公平锁 ：随机的获取，谁运气好，cpu时间片轮到哪个线程，哪个线程就能获取锁
    //构造函数参数为true--表示实现公平锁 :在锁上等待时间最长的线程将获得锁的使用权
    private static final Lock lock = new ReentrantLock(false);

    public static void main(String[] args) {
        new Thread(() -> work(), "Thread A").start();
        new Thread(() -> work(), "Thread B").start();
        new Thread(() -> work(), "Thread C").start();
        new Thread(() -> work(), "Thread D").start();
        new Thread(() -> work(), "Thread E").start();
        new Thread(() -> work(), "Thread F").start();
        new Thread(() -> work(), "Thread G").start();
    }

    public static void work() {
        for (int i = 0; i < 2; i++) {
            lock.lock();//加锁
            try {
                System.out.println(Thread.currentThread().getName() + " 获取了锁");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
