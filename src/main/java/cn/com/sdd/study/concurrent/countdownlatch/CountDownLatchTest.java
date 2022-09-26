package cn.com.sdd.study.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @ClassName CountDownLatchTest
 * @Author suidd
 * @Description CountDownLatch使用示例
 * 模拟一个使用场景，我们有一个主线程和5个辅助线程，等待主线程准备就绪了，5个辅助线程开始运行
 * 等待5个辅助线程运行完毕了，主线程继续往下运行
 * @Date 17:15 2020/5/24
 * @Version 1.0
 **/
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);

        IntStream.range(1, 6)
                .forEach(i -> new Thread(() -> {
                    try {
                        System.out.println("Aid thread " + i + " is waiting for starting.");
                        startSignal.await();

                        // aid thread do something
                        System.out.println("Aid thread " + i + " is doing something.");
                        doneSignal.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start());

        // main thread do sth
        Thread.sleep(2000);
        System.out.println("Main thread is doing something.");
        startSignal.countDown();

        System.out.println("Main thread is waiting for aid threads finishing.");
        doneSignal.await();

        System.out.println("All threads have finished.");
        // main thread do sth else
        System.out.println("Main thread do sth else! ");
    }
}