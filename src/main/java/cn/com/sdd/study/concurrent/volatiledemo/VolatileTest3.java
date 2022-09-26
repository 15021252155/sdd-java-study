package cn.com.sdd.study.concurrent.volatiledemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author suidd
 * @name VolatileTest3
 * @description 禁止重排序示例
 * @date 2020/5/22 15:52
 * Version 1.0
 **/
public class VolatileTest3 {
    private static Config config = null;
    private static volatile boolean initialized = false;

    public static void main(String[] args) {
        System.out.println("1...........");
        // 线程1负责初始化配置信息
        new Thread(() -> {
            config = new Config();
            config.name = "config";
            initialized = true;
        }).start();
        System.out.println("2...............");
        // 线程2检测到配置初始化完成后使用配置信息
        new Thread(() -> {
            while (!initialized) {
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(100));
            }

            // do sth with config
            String name = config.name;

            System.out.println("name is "+name);
        }).start();
        System.out.println("3...............");
    }
}

class Config {
    String name;
}