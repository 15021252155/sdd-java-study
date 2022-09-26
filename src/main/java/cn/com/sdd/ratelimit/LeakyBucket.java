package cn.com.sdd.ratelimit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName LeakyBucket
 * @Author suidd
 * @Description TODO
 * @Date 19:21 2021/11/13
 * @Version 1.0
 **/
public class LeakyBucket {

    // 流出速率
    private double rate;

    // 桶大小
    private double burst;

    // 最后更新时间
    private long refreshTime;

    // 现有量
    private int water;


    public LeakyBucket(double rate, double burst) {
        this.rate = rate;
        this.burst = burst;
    }

    /**
     * 用来刷新水量
     */
    private void refreshWater() {
        long now = System.currentTimeMillis();
        water = (int) Math.max(0, water - (now - refreshTime) * rate);
        refreshTime = now;
    }

    public synchronized boolean tryAcquire() {
        refreshWater();
        if (water < burst) {
            water++;
            return true;
        } else {
            return false;
        }
    }


    private static LeakyBucket leakyBucket = new LeakyBucket(1, 100);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (; ; ) {
            executorService.execute(() -> {
                if (leakyBucket.tryAcquire()) {
                    System.out.println("成功抢到令牌，处理业务逻辑...");
                } else {
                    System.out.println("未抢到令牌，退出...");
                }

            });
        }
    }
}
