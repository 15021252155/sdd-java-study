package cn.com.sdd.study.concurrent.reentrantreadwritelock;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * @ClassName SafeTreeMap
 * @Author suidd
 * @Description 使用ReentrantReadWriteLock实现一个高效安全的TreeMap
 * @Date 14:42 2020/5/24
 * @Version 1.0
 **/
public class SafeTreeMap {
    private final Map<String, Object> m = new TreeMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public Object get(String key) {
        readLock.lock();
        try {
            return m.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public Object put(String key, Object value) {
        writeLock.lock();
        try {
            return m.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("test start ...");
        long start = System.currentTimeMillis();
        final CountDownLatch latch = new CountDownLatch(2);
        SafeTreeMap treeMap = new SafeTreeMap();
        new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> {
                treeMap.put(String.valueOf(i), i);
                System.out.println(Thread.currentThread().getName() + " ...");
            });
            latch.countDown();
        }, "write thread").start();

        new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> {
                treeMap.get(String.valueOf(i));
                System.out.println(Thread.currentThread().getName() + " ...");
            });
            latch.countDown();
        }, "read thread").start();

        latch.await();
        System.out.println("test end  times=" + (System.currentTimeMillis() - start));
    }
}
