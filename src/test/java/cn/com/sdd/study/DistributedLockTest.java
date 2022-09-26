package cn.com.sdd.study;


import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author suidd
 * @name DistributedLockDemo
 * @description Curator实现分布式锁的示例
 * @date 2020/5/27 14:26
 * Version 1.0
 **/
@Slf4j
@SpringBootTest
public class DistributedLockTest {
    // ZooKeeper 锁节点路径, 分布式锁的相关操作都是在这个节点上进行
    private final String lockPath = "/distributed-lock";

    // ZooKeeper 服务地址, 单机格式为:(127.0.0.1:2181),
    // 集群格式为:(127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183)
    private String connectString;
    // Curator 客户端重试策略
    private RetryPolicy retry;
    // Curator 客户端对象
    private CuratorFramework client;
    // client2 用户模拟其他客户端
    private CuratorFramework client2;

    // 初始化资源
    @BeforeEach
    public void init() {
        // 设置 ZooKeeper 服务地址为本机的 2181 端口
        connectString = "127.0.0.1:2181";
        // 重试策略
        // 初始休眠时间为 1000ms, 最大重试次数为 3
        retry = new ExponentialBackoffRetry(1000, 3);
        // 创建一个客户端, 60000(ms)为 session 超时时间, 15000(ms)为链接超时时间
        client = CuratorFrameworkFactory.newClient(connectString, 60000, 15000, retry);
        client2 = CuratorFrameworkFactory.newClient(connectString, 60000, 15000, retry);
        // 创建会话
        client.start();
        client2.start();
        System.out.println("初始化资源...");
    }

    // 释放资源
    @AfterEach
    public void close() {
        CloseableUtils.closeQuietly(client);
        System.out.println("释放资源...");
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //共享锁，不可重入-InterProcessSemaphoreMutex
     * InterProcessSemaphoreMutex是一种不可重入的互斥锁，也就意味着即使是同一个线程也无法在持有锁的情况下再次获得锁，所以需要注意，不可重入的锁很容易在一些情况导致死锁。
     * @date 2020/5/27 14:48
     **/
    @Test
    public void sharedLock() throws Exception {
        // 创建共享锁
        final InterProcessLock lock = new InterProcessSemaphoreMutex(client, lockPath);
        // lock2 用于模拟其他客户端
        final InterProcessLock lock2 = new InterProcessSemaphoreMutex(client2, lockPath);

        new Thread(() -> {
            // 获取锁对象
            try {
                lock.acquire();
                System.out.println("1获取锁===============");
                // 测试锁重入
                Thread.sleep(5 * 1000);
                lock.release();
                System.out.println("1释放锁===============");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            // 获取锁对象
            try {
                lock2.acquire();
                System.out.println("2获取锁===============");
                Thread.sleep(5 * 1000);
                lock2.release();
                System.out.println("2释放锁===============");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(20 * 1000);
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //共享可重入锁-InterProcessMutex
     * 此锁可以重入，但是重入几次需要释放几次
     * @date 2020/5/27 14:55
     **/
    @Test
    public void sharedReentrantLock() throws Exception {
        // 创建共享锁
        final InterProcessLock lock = new InterProcessMutex(client, lockPath);
        // lock2 用于模拟其他客户端
        final InterProcessLock lock2 = new InterProcessMutex(client2, lockPath);

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            // 获取锁对象
            try {
                lock.acquire();
                System.out.println("1获取锁===============");
                // 测试锁重入
                lock.acquire();
                System.out.println("1再次获取锁===============");
                Thread.sleep(5 * 1000);
                lock.release();
                System.out.println("1释放锁===============");
                lock.release();
                System.out.println("1再次释放锁===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            // 获取锁对象
            try {
                lock2.acquire();
                System.out.println("2获取锁===============");
                // 测试锁重入
                lock2.acquire();
                System.out.println("2再次获取锁===============");
                Thread.sleep(5 * 1000);
                lock2.release();
                System.out.println("2释放锁===============");
                lock2.release();
                System.out.println("2再次释放锁===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //共享可重入读写锁-InterProcessReadWriteLock
     * 读锁和读锁不互斥，只要有写锁就互斥
     * @date 2020/5/27 14:57
     **/
    @Test
    public void sharedReentrantReadWriteLock() throws Exception {
        // 创建共享可重入读写锁
        final InterProcessReadWriteLock locl1 = new InterProcessReadWriteLock(client, lockPath);
        // lock2 用于模拟其他客户端
        final InterProcessReadWriteLock lock2 = new InterProcessReadWriteLock(client2, lockPath);

        // 获取读写锁(使用 InterProcessMutex 实现, 所以是可以重入的)
        final InterProcessLock readLock = locl1.readLock();
        final InterProcessLock readLockw = lock2.readLock();

        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            // 获取锁对象
            try {
                readLock.acquire();
                System.out.println("1获取读锁===============");
                // 测试锁重入
                readLock.acquire();
                System.out.println("1再次获取读锁===============");
                Thread.sleep(5 * 1000);
                readLock.release();
                System.out.println("1释放读锁===============");
                readLock.release();
                System.out.println("1再次释放读锁===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            // 获取锁对象
            try {
                Thread.sleep(500);
                readLockw.acquire();
                System.out.println("2获取读锁===============");
                // 测试锁重入
                readLockw.acquire();
                System.out.println("2再次获取读锁==============");
                Thread.sleep(5 * 1000);
                readLockw.release();
                System.out.println("2释放读锁===============");
                readLockw.release();
                System.out.println("2再次释放读锁===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //共享信号量-InterProcessSemaphoreV2
     * @date 2020/5/27 14:59
     **/
    @Test
    public void semaphore() throws Exception {
        // 创建一个信号量, Curator 以公平锁的方式进行实现
        final InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, lockPath, 1);
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            // 获取锁对象
            try {
                // 获取一个许可
                Lease lease = semaphore.acquire();
                log.info("1获取读信号量===============");
                Thread.sleep(5 * 1000);
                semaphore.returnLease(lease);
                log.info("1释放读信号量===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            // 获取锁对象
            try {
                // 获取一个许可
                Lease lease = semaphore.acquire();
                log.info("2获取读信号量===============");
                Thread.sleep(5 * 1000);
                semaphore.returnLease(lease);
                log.info("2释放读信号量===============");

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //一次获取多个共享信号量
     * @date 2020/5/27 15:01
     **/
    @Test
    public void semaphore2() throws Exception {
        // 创建一个信号量, Curator 以公平锁的方式进行实现
        final InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, lockPath, 3);
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            // 获取锁对象
            try {
                // 获取2个许可
                Collection<Lease> acquire = semaphore.acquire(2);
                log.info("1获取读信号量===============");
                Thread.sleep(5 * 1000);
                semaphore.returnAll(acquire);
                log.info("1释放读信号量===============");
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            // 获取锁对象
            try {
                // 获取1个许可
                Collection<Lease> acquire = semaphore.acquire(1);
                log.info("2获取读信号量===============");
                Thread.sleep(5 * 1000);
                semaphore.returnAll(acquire);
                log.info("2释放读信号量===============");
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //多重共享锁-InterProcessMultiLock
     * @date 2020/5/27 15:02
     **/
    @Test
    public void multiLock() throws Exception {
        // 可重入锁
        final InterProcessLock interProcessLock1 = new InterProcessMutex(client, lockPath);
        // 不可重入锁
        final InterProcessLock interProcessLock2 = new InterProcessSemaphoreMutex(client2, lockPath);
        // 创建多重锁对象
        final InterProcessLock lock = new InterProcessMultiLock(Arrays.asList(interProcessLock1, interProcessLock2));

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(() -> {
            // 获取锁对象
            try {
                // 获取参数集合中的所有锁
                lock.acquire();
                // 因为存在一个不可重入锁, 所以整个 InterProcessMultiLock 不可重入
                System.out.println(lock.acquire(2, TimeUnit.SECONDS));
                // interProcessLock1 是可重入锁, 所以可以继续获取锁
                System.out.println(interProcessLock1.acquire(2, TimeUnit.SECONDS));
                // interProcessLock2 是不可重入锁, 所以获取锁失败
                System.out.println(interProcessLock2.acquire(2, TimeUnit.SECONDS));

                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
    }
}