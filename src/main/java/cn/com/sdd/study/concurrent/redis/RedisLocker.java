package cn.com.sdd.study.concurrent.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author suidd
 * @name RedisLocker
 * @description RedisLocker实现类
 * @date 2020/5/27 16:01
 * Version 1.0
 **/
@Component
public class RedisLocker implements Locker {
    @Autowired
    private RedissonClient redissonClient;

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //获取锁
     * @date 2020/5/27 16:02
     **/
    @Override
    public void lock(String key, Runnable command) {
        RLock lock = redissonClient.getLock(key);
        try {
            lock.lock();
            command.run();
        } finally {
            lock.unlock();
        }
    }
}