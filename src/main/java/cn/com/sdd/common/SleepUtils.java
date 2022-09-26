package cn.com.sdd.common;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName SleepUtils
 * @Author suidd
 * @Description 线程休眠工具类
 * @Date 21:01 2020/5/3
 * @Version 1.0
 **/
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}