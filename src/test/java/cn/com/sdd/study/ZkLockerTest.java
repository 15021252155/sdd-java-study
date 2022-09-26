package cn.com.sdd.study;

import cn.com.sdd.study.concurrent.zookeeper.Locker;
import cn.com.sdd.study.concurrent.zookeeper.ZkLocker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author suidd
 * @name ZkLockerTest
 * @description zookeeper分布式锁测试类
 * @date 2020/5/27 13:41
 * Version 1.0
 **/
@SpringBootTest
public class ZkLockerTest {

    @Autowired
    private ZkLocker zkLocker;

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //zookeeper分布式锁测试类
     * 原生API实现更易于理解zookeeper实现分布式锁的逻辑，但是难免保证没有什么问题，比如不是重入锁，不支持读写锁等
     * @date 2020/5/27 16:20
     **/
    @Test
    public void testZkLocker() throws IOException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                zkLocker.lock("user_1", () -> {
                    try {
                        System.out.println(String.format("user_1 time: %d, threadName: %s", System.currentTimeMillis(), Thread.currentThread().getName()));
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }, "Thread-" + i).start();
        }
        for (int i = 10; i < 20; i++) {
            new Thread(() -> {
                zkLocker.lock("user_2", () -> {
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
