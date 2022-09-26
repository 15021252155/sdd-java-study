package cn.com.sdd.study.concurrent.phaser;

import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

/**
 * @ClassName PhaserTest
 * @Author suidd
 * @Description Phaser，翻译为阶段，它适用于这样一种场景，一个大任务可以分为多个阶段完成，且每个阶段的任务可以多个线程并发执行，但是必须上一个阶段的任务都完成了才可以执行下一个阶段的任务。
 * 这种场景虽然使用CyclicBarrier或者CountryDownLatch也可以实现，但是要复杂的多。
 * 首先，具体需要多少个阶段是可能会变的，其次，每个阶段的任务数也可能会变的。相比于CyclicBsarrier和CountDownLatch，Phaser更加灵活更加方便。
 * @Date 21:33 2020/5/26
 * @Version 1.0
 **/
public class PhaserTest {
    public static final int PARTIES = 3;
    public static final int PHASES = 4;

    public static void main(String[] args) {
        Phaser phaser = new Phaser(PARTIES) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("=======phase: " + phase + " finished=============");
                return super.onAdvance(phase, registeredParties);
            }
        };

        IntStream.range(0, PARTIES).forEach(i -> {
            new Thread(() -> {
                IntStream.range(0, PHASES).forEach(j -> {
                    System.out.println(String.format("%s: phase: %d", Thread.currentThread().getName(), j));
                    phaser.arriveAndAwaitAdvance();
                });
            }, "Thread" + i).start();
        });

    }

}
