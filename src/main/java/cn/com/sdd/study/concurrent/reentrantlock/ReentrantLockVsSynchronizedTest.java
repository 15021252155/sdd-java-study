package cn.com.sdd.study.concurrent.reentrantlock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @ClassName ReentrantLockVsSynchronizedTest
 * @Author suidd
 * @Description ReentrantLock与synchronized对比分析
 * @Date 10:35 2020/5/24
 * @Version 1.0
 **/
public class ReentrantLockVsSynchronizedTest {
    public static AtomicInteger a = new AtomicInteger(0);
    public static LongAdder b = new LongAdder();
    public static int c;
    public static int d;
    public static int e;

    public static final ReentrantLock fairLock = new ReentrantLock(true);
    public static final ReentrantLock unfairLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("-------------------------------------");
        testAll(1, 100000);
        System.out.println("-------------------------------------");
        testAll(2, 100000);
        System.out.println("-------------------------------------");
        testAll(4, 100000);
        System.out.println("-------------------------------------");
        testAll(6, 100000);
        System.out.println("-------------------------------------");
        testAll(8, 100000);
        System.out.println("-------------------------------------");
        testAll(10, 100000);
        System.out.println("-------------------------------------");
        testAll(50, 100000);
        System.out.println("-------------------------------------");
        testAll(100, 100000);
        System.out.println("-------------------------------------");
        testAll(200, 100000);
        System.out.println("-------------------------------------");
        testAll(500, 100000);
        System.out.println("-------------------------------------");
        testAll(1000, 1000000);
        System.out.println("-------------------------------------");
        testAll(500, 10000);
        System.out.println("-------------------------------------");
        testAll(500, 1000);
        System.out.println("-------------------------------------");
        testAll(500, 100);
        System.out.println("-------------------------------------");
        testAll(500, 10);
        System.out.println("-------------------------------------");
        testAll(500, 1);
        System.out.println("-------------------------------------");
    }

    /**
     * @return
     * @Author suidd
     * @Description 测试
     * @Date 10:58 2020/5/24
     * @Param
     * @Version 1.0
     **/
    public static void testAll(int threadCount, int loopCount) throws InterruptedException {
        testAtomicInteger(threadCount, loopCount);
        testLongAdder(threadCount, loopCount);
        testSynchronized(threadCount, loopCount);
        testReentrantLockUnfair(threadCount, loopCount);
        //testReentrantLockFair(threadCount, loopCount);
    }

    /**
     * @return
     * @Author suidd
     * @Description 测试AtomicInteger
     * @Date 10:50 2020/5/24
     * @Param
     * @Version 1.0
     **/
    public static void testAtomicInteger(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        IntStream.range(0, threadCount)
                .forEach(i -> new Thread(() -> {
                    IntStream.range(0, loopCount)
                            .forEach(j -> a.incrementAndGet());
                    countDownLatch.countDown();
                }).start());

        countDownLatch.await();

        System.out.println("testAtomicInteger: result=" + a.get() + ", threadCount=" + threadCount + ", loopCount=" + loopCount + ", elapse=" + (System.currentTimeMillis() - start));
    }

    /**
     * @return
     * @Author suidd
     * @Description 测试LongAdder
     * @Date 10:51 2020/5/24
     * @Param
     * @Version 1.0
     **/
    public static void testLongAdder(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        IntStream.range(0, threadCount)
                .forEach(i -> new Thread(() -> {
                    IntStream.range(0, loopCount)
                            .forEach(j -> b.increment());
                    countDownLatch.countDown();
                }).start());

        countDownLatch.await();

        System.out.println("testLongAdder: result=" + b.sum() + ", threadCount=" + threadCount + ", loopCount=" + loopCount + ", elapse=" + (System.currentTimeMillis() - start));
    }

    /**
     * @return
     * @Author suidd
     * @Description 测试ReentrantLock公平锁
     * @Date 10:52 2020/5/24
     * @Param
     * @Version 1.0
     **/
    public static void testReentrantLockFair(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        IntStream.range(0, threadCount)
                .forEach(i -> new Thread(() -> {
                    IntStream.range(0, loopCount)
                            .forEach(j -> {
                                fairLock.lock();
                                c++;
                                fairLock.unlock();
                            });
                    countDownLatch.countDown();
                }).start());

        countDownLatch.await();

        System.out.println("testReentrantLockFair: result=" + c + ", threadCount=" + threadCount + ", loopCount=" + loopCount + ", elapse=" + (System.currentTimeMillis() - start));
    }

    /**
     * @return
     * @Author suidd
     * @Description 测试ReentrantLock非公平锁
     * @Date 10:55 2020/5/24
     * @Param
     * @Version 1.0
     **/
    public static void testReentrantLockUnfair(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        IntStream.range(0, threadCount)
                .forEach(i -> new Thread(() -> {
                    IntStream.range(0, loopCount)
                            .forEach(j -> {
                                unfairLock.lock();
                                d++;
                                unfairLock.unlock();
                            });
                    countDownLatch.countDown();
                }).start());

        countDownLatch.await();

        System.out.println("testReentrantLockUnfair: result=" + d + ", threadCount=" + threadCount + ", loopCount=" + loopCount + ", elapse=" + (System.currentTimeMillis() - start));
    }

    /**
     * @return
     * @Author suidd
     * @Description 测试Synchronized
     * @Date 10:57 2020/5/24
     * @Param
     * @Version 1.0
     **/
    public static void testSynchronized(int threadCount, int loopCount) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        IntStream.range(0, threadCount)
                .forEach(i -> new Thread(() -> {
                    IntStream.range(0, loopCount)
                            .forEach(j -> {
                                synchronized (ReentrantLockVsSynchronizedTest.class) {
                                    e++;
                                }
                            });

                    countDownLatch.countDown();
                }).start());

        countDownLatch.await();

        System.out.println("testSynchronized: result=" + e + ", threadCount=" + threadCount + ", loopCount=" + loopCount + ", elapse=" + (System.currentTimeMillis() - start));
    }
}
