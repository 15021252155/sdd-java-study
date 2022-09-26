package cn.com.sdd.study.concurrent.longadder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author suidd
 * @name LongAdderTest
 * @description LongAdder是java8中新增的原子类，在多线程环境中，它比AtomicLong性能要高出不少，特别是写多的场景
 * @date 2020/5/21 11:41
 * Version 1.0
 **/
public class LongAdderTest {
    public static void main(String[] args) {
        testAtomicLongVSLongAdder(1, 10000000);
        testAtomicLongVSLongAdder(10, 10000000);
        testAtomicLongVSLongAdder(20, 10000000);
        testAtomicLongVSLongAdder(40, 10000000);
        testAtomicLongVSLongAdder(80, 10000000);
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //AtomicLong和LongAdder 性能比较
     * @date 2020/5/21 15:33
     **/
    static void testAtomicLongVSLongAdder(final int threadCount, final int times) {
        System.out.println(String.format("threadCount:%s,times:%s", threadCount, times));
        try {
            long start = System.currentTimeMillis();
            testLongAdder(threadCount, times);//LongAdder
            System.out.println(String.format("LongAdder elapse：%s", (System.currentTimeMillis() - start)));

            long start2 = System.currentTimeMillis();
            testAtomicLong(threadCount, times);//AtomicLong
            System.out.println(String.format("AtomicLong elapse：%s", (System.currentTimeMillis() - start2)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //使用AtomicLong实现自增
     * @date 2020/5/21 15:24
     **/
    static void testAtomicLong(final int threadCount, final int times) throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong();
        List<Thread> list = new ArrayList<>(threadCount);
        //封装threadcount个线程
        for (int i = 0; i < threadCount; i++) {
            list.add(new Thread(() -> {
                for (int j = 0; j < times; j++) {
                    //根据times，递增AtomicLong
                    atomicLong.incrementAndGet();
                }
            }));
        }

        //启动线程
        for (int i = 0; i < threadCount; i++) {
            list.get(i).start();
        }

        //等待所有线程执行完毕
        for (int i = 0; i < threadCount; i++) {
            list.get(i).join();
        }
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //使用LongAdder实现自增
     * @date 2020/5/21 15:24
     **/
    static void testLongAdder(final int threadCount, final int times) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        List<Thread> list = new ArrayList<>(threadCount);
        //封装threadcount个线程
        for (int i = 0; i < threadCount; i++) {
            list.add(new Thread(() -> {
                for (int j = 0; j < times; j++) {
                    //根据times，递增AtomicLong
                    longAdder.add(1);
                }
            }));
        }

        //启动线程
        for (int i = 0; i < threadCount; i++) {
            list.get(i).start();
        }

        //等待所有线程执行完毕
        for (int i = 0; i < threadCount; i++) {
            list.get(i).join();
        }
    }
}