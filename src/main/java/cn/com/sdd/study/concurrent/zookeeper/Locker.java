package cn.com.sdd.study.concurrent.zookeeper;

/**
 * @author suidd
 * @name Locker
 * @description Lock接口
 * @date 2020/5/27 13:12
 * Version 1.0
 **/
public interface Locker {
    void lock(String key, Runnable command);
}