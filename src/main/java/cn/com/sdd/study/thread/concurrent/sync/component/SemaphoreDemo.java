package cn.com.sdd.study.thread.concurrent.sync.component;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SemaphoreDemo
 * @Author suidd
 * @Description Semaphore
 * Semaphore翻译成字面意思为 信号量，Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 * @Date 13:34 2020/5/5
 * @Version 1.0
 **/
public class SemaphoreDemo {
    public static void main(String[] args) {
        int N = 10;//工人数
        Semaphore semaphore = new Semaphore(5);//机器数目
        for (int i = 1; i < N + 1; i++) {
            new Worker(i, semaphore).start();
        }

    }

    static class Worker extends Thread {
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();//获得一个许可
                System.out.println("工人" + this.num + "占用一个机器在生产...");
                TimeUnit.SECONDS.sleep(10);
                System.out.println("工人" + this.num + "释放出机器");
                semaphore.release();//释放一个许可
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
