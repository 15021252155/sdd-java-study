package cn.com.sdd.study;

import cn.com.sdd.study.concurrent.redis.RedisLocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author suidd
 * @name RedisLockerTest
 * @description RedisLockerTest
 * @date 2020/5/27 16:06
 * Version 1.0
 **/
@SpringBootTest
public class RedisLockerTest {
    @Autowired
    private RedisLocker redisLocker;

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //测试Redis获取锁
     * @date 2020/5/27 17:25
     **/
    @Test
    public void testRedisLocker() throws IOException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                redisLocker.lock("lock", () -> {
                    // 可重入锁测试
                    redisLocker.lock("lock", () -> {
                        System.out.println(String.format("time: %d, threadName: %s", System.currentTimeMillis(), Thread.currentThread().getName()));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });
            }, "Thread-" + i).start();
        }
        System.in.read();
    }
}