package cn.com.sdd.study.thread.concurrent.sync.component;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CyclicBarrierDeamo
 * @Author suidd
 * @Description CyclicBarrier
 * CyclicBarrier字面意思回环栅栏，通过它可以实现让一组线程等待至某个状态之后再全部同时执行。
 * <p>
 * 叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用。
 * <p>
 * 我们暂且把这个状态就叫做barrier，当调用await()方法之后，线程就处于barrier了
 * @Date 13:56 2020/5/5
 * @Version 1.0
 **/
public class CyclicBarrierDeamo {
    public static void main(String[] args) {
        int N = 4;
        //如果说想在所有线程写入操作完之后，进行额外的其他操作可以为CyclicBarrier提供Runnable参数：
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, () -> System.out.println("当前线程" + Thread.currentThread().getName()));
        for (int i = 0; i < N; i++) {
            new Writer(cyclicBarrier).start();
        }
        /*
        从结果可以看出，当四个线程都到达barrier状态后，会从四个线程中选择最后一个执行完的线程去执行Runnable。

        CyclicBarrier实际用途：假设我们要导入一批数据，因为数据量较大有4亿条，所以我们希望启动四个线程，

        每个线程负责导入1亿条数据，我们希望所有的数据导入完成之后，还在数据库中插入一条记录，表示任务执行完成，此时就可以使用CyclicBarrier。
         */
    }

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
            try {
                TimeUnit.SECONDS.sleep(5);//以睡眠来模拟写入数据操作
                System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
