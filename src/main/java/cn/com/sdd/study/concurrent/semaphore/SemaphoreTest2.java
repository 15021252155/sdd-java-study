package cn.com.sdd.study.concurrent.semaphore;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * @ClassName SemaphoreTest
 * @Author suidd
 * @Description Semaphore使用示例
 * 假若一个工厂有5台机器，但是有8个工人，一台机器同时只能被一个工人使用，只有使用完了，其他工人才能继续使用。那么我们就可以通过Semaphore来实现
 * @Date 15:38 2020/5/24
 * @Version 1.0
 **/
public class SemaphoreTest2 {
    public static void main(String[] args) {
        int workerNum = 8;// 工人数
        int machineCount = 5;// 机器数
        Semaphore semaphore = new Semaphore(machineCount);
        IntStream.range(1, workerNum + 1).
                forEach(i -> new Work(i, semaphore).start());
    }

    static class Work extends Thread {
        private int workerNo;
        private Semaphore semaphore;

        public Work(int workerNo, Semaphore semaphore) {
            this.workerNo = workerNo;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                // 获取一个许可，默认使用的是可中断方式
                // 如果尝试获取许可失败，会进入AQS的队列中排队
                semaphore.acquire();
                System.out.println("工人" + this.workerNo + "占用一台机器，正在生产中...");
                Thread.sleep(2000);
                System.out.println("工人" + this.workerNo + "生产完成，释放出机器...");
                // 释放许可
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
