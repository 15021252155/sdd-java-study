package cn.com.sdd.study.concurrent.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author suidd
 * @name ZkCuratorLocker
 * @description 基于curator zookeeper分布式锁实现
 * @date 2020/5/27 14:11
 * Version 1.0
 **/
@Component
@Slf4j
public class ZkCuratorLocker implements Locker {
    public static final String connAddr = "127.0.0.1:2181";
    public static final int timeout = 6000;
    public static final String LOCKER_ROOT = "/locker";

    private CuratorFramework cf;

    @PostConstruct
    public void init() {
        this.cf = CuratorFrameworkFactory.builder()
                .connectString(connAddr)
                .sessionTimeoutMs(timeout)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        cf.start();
    }

    @Override
    public void lock(String key, Runnable command) {
        String path = LOCKER_ROOT.concat("/").concat(key);
        InterProcessLock lock = new InterProcessMutex(cf, path);
        try {
            lock.acquire();
            command.run();
        } catch (Exception e) {
            log.error("get lock error", e);
            throw new RuntimeException("get lock error", e);
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                log.error("release lock error", e);
                throw new RuntimeException("release lock error", e);
            }
        }
    }
}