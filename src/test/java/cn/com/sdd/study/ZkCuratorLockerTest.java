package cn.com.sdd.study;

import cn.com.sdd.study.concurrent.zookeeper.ZkCuratorLocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author suidd
 * @name ZkCuratorLockerTest
 * @description 基于curator zookeeper分布式锁实现测试类
 * @date 2020/5/27 14:13
 * Version 1.0
 **/
@SpringBootTest
public class ZkCuratorLockerTest {
    @Autowired
    private ZkCuratorLocker zkCuratorLocker;

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //基于curator zookeeper分布式锁实现测试类
     * @date 2020/5/27 16:21
     **/
    @Test
    public void testZkCuratorLocker() throws IOException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                zkCuratorLocker.lock("user_1", () -> {
                    try {
                        System.out.println(String.format("user_1 time: %d, threadName: %s", System.currentTimeMillis(), Thread.currentThread().getName()));
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }, "Thread-" + i).start();
        }
        for (int i = 10; i < 2000; i++) {
            new Thread(() -> {
                zkCuratorLocker.lock("user_2", () -> {
                    try {
                        System.out.println(String.format("user_2 time: %d, threadName: %s", System.currentTimeMillis(), Thread.currentThread().getName()));
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }, "Thread-" + i).start();
        }

        System.in.read();
    }
}