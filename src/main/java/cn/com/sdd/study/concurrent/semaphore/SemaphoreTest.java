package cn.com.sdd.study.concurrent.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @ClassName SemaphoreTest
 * @Author suidd
 * @Description 使用Semaphore实现秒杀限流功能
 * @Date 15:38 2020/5/24
 * @Version 1.0
 **/
public class SemaphoreTest {
    public static final Semaphore SEMAPHORE = new Semaphore(100);
    public static final AtomicInteger failCount = new AtomicInteger(0);
    public static final AtomicInteger successCount = new AtomicInteger(0);

    public static void main(String[] args) {
        IntStream.range(0, 1000).
                forEach(i -> new Thread(() -> seckill()).start());
    }

     /**
      * @Author suidd
      * @Description 秒杀
      * @Date 15:46 2020/5/24
      * @Param
      * @return
      * @Version 1.0
      **/
    public static boolean seckill() {
        // 尝试获取一个许可，使用Sync的非公平模式尝试获取许可方法，不论是否获取到许可都返回
        // 只尝试一次，不会进入队列排队
        if (!SEMAPHORE.tryAcquire()) {
            System.out.println("no permits, count=" + failCount.incrementAndGet());
            return false;
        }

        try {
            // 处理业务逻辑
            Thread.sleep(2000);
            System.out.println("seckill success, count=" + successCount.incrementAndGet());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SEMAPHORE.release();
        }
        return true;
    }
}
