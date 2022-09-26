package cn.com.sdd.study.concurrent.atomicinteger;

import java.util.stream.IntStream;

/**
 * @author suidd
 * @name AtomicIntegerTest
 * @description AtomicInteger是java并发包下面提供的原子类，主要操作的是int类型的整型，通过调用底层Unsafe的CAS等方法实现原子操作。
 * @date 2020/5/21 9:11
 * Version 1.0
 **/
public class AtomicIntegerTest {
    private static int count = 0;

    public static void increment() {
        count++;
    }

    //这里起了100个线程，每个线程对count自增1000次，你会发现每次运行的结果都不一样，但它们有个共同点就是都不到100000次，所以直接使用int是有问题的。
    public static void main(String[] args) {
        IntStream.range(0, 100)
                .forEach(i ->
                        new Thread(() -> IntStream.range(0, 1000)
                                .forEach(j -> increment())).start());

        // 这里使用2或者1看自己的机器
        // 我这里是用run跑大于2才会退出循环
        // 但是用debug跑大于1就会退出循环了
        while (Thread.activeCount() > 2) {
            // 让出CPU
            Thread.yield();
        }

        System.out.println(count);
    }
}