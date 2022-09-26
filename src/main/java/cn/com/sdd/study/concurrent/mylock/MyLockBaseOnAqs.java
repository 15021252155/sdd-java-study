package cn.com.sdd.study.concurrent.mylock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.stream.IntStream;

/**
 * @ClassName MyLockBaseOnAqs
 * @Author suidd
 * @Description 基于AQS自己动手写一个锁
 * @Date 20:04 2020/5/23
 * @Version 1.0
 **/
public class MyLockBaseOnAqs {
    // 定义一个同步器，继承AQS类
    private static final class Sync extends AbstractQueuedSynchronizer {
        // 实现tryAcquire(acquires)方法
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        // 实现tryRelease(releases)方法
        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    // 声明同步器
    private final Sync sync = new Sync();

    // 加锁
    public void lock() {
        sync.acquire(1);
    }

    // 解锁
    public void unlock() {
        sync.release(1);
    }

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        MyLockBaseOnAqs lock = new MyLockBaseOnAqs();
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        IntStream.range(0, 1000).forEach(i -> new Thread(() -> {
            lock.lock();

            try {
                IntStream.range(0, 10000).forEach(j -> {
                    count++;
                });
            } finally {
                lock.unlock();
            }

            countDownLatch.countDown();
        }, "thread" + i).start());

        countDownLatch.await();

        System.out.println(count);
    }
}