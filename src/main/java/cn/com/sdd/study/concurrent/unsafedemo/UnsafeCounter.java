package cn.com.sdd.study.concurrent.unsafedemo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author suidd
 * @name UnsafeCounter
 * @description 基于Unsafe的compareAndSwapInt()方法构建线程安全的计数器Demo
 * @date 2020/5/20 17:42
 * Version 1.0
 **/
public class UnsafeCounter {
    private volatile int count = 0;

    private static long offset;
    private static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
            offset = unsafe.objectFieldOffset(UnsafeCounter.class.getDeclaredField("count"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void increment() {
        int current = count;
        // 失败了就重试直到成功为止
        while (!unsafe.compareAndSwapInt(this, offset, current, current + 1)) {
            current = count;
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        UnsafeCounter counter = new UnsafeCounter();
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        // 起100个线程，每个线程自增10000次
        IntStream.range(0, 100)
                .forEach(i -> threadPool.submit(() -> IntStream.range(0, 10000)
                        .forEach(j -> counter.increment())));

        threadPool.shutdown();

        Thread.sleep(2000);

        // 打印1000000
        System.out.println(counter.getCount());
    }
}
