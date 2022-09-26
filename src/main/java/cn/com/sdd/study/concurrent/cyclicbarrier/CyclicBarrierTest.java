package cn.com.sdd.study.concurrent.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

/**
 * @ClassName CyclicBarrierTest
 * @Author suidd
 * @Description CyclicBarrier，回环栅栏，它会阻塞一组线程直到这些线程同时达到某个条件才继续执行。
 * 它与CountDownLatch很类似，但又不同，CountDownLatch需要调用countDown()方法触发事件，而CyclicBarrier不需要，它就像一个栅栏一样，当一组线程都到达了栅栏处才继续往下走。
 * @Date 21:27 2020/5/26
 * @Version 1.0
 **/
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        IntStream.range(0, 3).forEach(i -> {
            new Thread(() -> {
                System.out.println("线程" + i + "thread before");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + i + "thread after");
            }).start();
        });
    }
}
