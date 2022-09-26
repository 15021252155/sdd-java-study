package cn.com.sdd.study.concurrent.atomicinteger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author suidd
 * @name AtomicIntegerTest2
 * @description AtomicInteger是java并发包下面提供的原子类，主要操作的是int类型的整型，通过调用底层Unsafe的CAS等方法实现原子操作。
 * @date 2020/5/21 9:24
 * Version 1.0
 **/
public class AtomicIntegerTest2 {
    private static AtomicInteger count = new AtomicInteger(0);

    public static void increment() {
        count.incrementAndGet();//以原子方式将当前值加 1，方法是返回新值（即加1后值）
        //count.getAndIncrement();//以原子方式将当前值加 1，方法是返回旧值（即加1前的原始值）
    }


    //AtomicInteger，它的自增调用的是Unsafe的CAS并使用自旋保证一定会成功，它可以保证两步操作的原子性
    public static void main(String[] args) {
        IntStream.range(0, 100)
                .forEach(i ->
                        new Thread(() -> IntStream.range(0, 1000).
                                forEach(j -> increment())).start());

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