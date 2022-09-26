package cn.com.sdd.study.concurrent.countdownlatch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author suidd
 * @name CountDownLatchTest2
 * @description CountDownLatch示例2
 * 比如几个人约好去饭店一起去吃饭，这几个人都是比较绅士，要等到所有人都到齐以后才让服务员上菜
 * @date 2020/5/25 8:30
 * Version 1.0
 **/
public class CountDownLatchTest2 {
    public static void main(String[] args) throws InterruptedException {
        int cusNum=5;//定义员工数量
        CountDownLatch countDownLatch = new CountDownLatch(cusNum);
        IntStream.range(0, cusNum).forEach(i -> new Customer("员工" + (i+1), countDownLatch).start());
        Thread.sleep(100);
        new Thread(new Waiter("♥小芳♥", countDownLatch)).start();
    }

    /**
     * @param
     * @author suidd
     * @description //员工类
     * @date 2020/5/25 8:50
     * @return change notes
     **/
    private static class Customer extends Thread {
        private String name;
        private CountDownLatch latch;

        public Customer(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
                Random random = new Random();

                System.out.println(sdf.format(new Date()) + "-" + this.name + "出发去往饭店");
                Thread.sleep((long) (Math.random() * 3000) + 1000);
                latch.countDown();
                System.out.println(sdf.format(new Date()) + "-" + this.name + "到达饭店");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param
     * @author suidd
     * @description // 服务员类
     * @date 2020/5/25 8:50
     * @return change notes
     **/
    private static class Waiter implements Runnable {
        private String name;
        private CountDownLatch latch;

        public Waiter(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
            try {
                System.out.println(sdf.format(new Date()) + "-" + this.name + "等待上菜");
                // 一直等待所有顾客到来才会上菜
                //latch.await();
                // 如果有一个顾客迟迟没到，可以设置服务员等待超时时间，超时后，服务员会开始上菜，不再等待
                latch.await(3, TimeUnit.SECONDS);
                System.out.println(sdf.format(new Date()) + "-" + this.name + "开始上菜");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
