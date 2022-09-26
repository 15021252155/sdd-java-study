package cn.com.sdd.study.thread.concurrent.sync.component;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName LockSupportDemo
 * @Author suidd
 * @Description LockSupport
 * LockSupport是JDK中比较底层的类，用来创建锁和其他同步工具类的基本线程阻塞原语。
 * <p>
 * java锁和同步器框架的核心 AQS: AbstractQueuedSynchronizer，就是通过调用 LockSupport .park()和 LockSupport .unpark()实现线程的阻塞和唤醒 的。
 * <p>
 * LockSupport 很类似于二元信号量(只有1个许可证可供使用)，如果这个许可还没有被占用，当前线程获取许可并继 续 执行；如果许可已经被占用，当前线 程阻塞，等待获取许可。
 * @Date 21:04 2020/5/4
 * @Version 1.0
 **/
public class LockSupportDemo {
    public static void main(String[] args) {
        //运行该代码，可以发现主线程一直处于阻塞状态。因为 许可默认是被占用的 ，调用park()时获取不到许可，所以进入阻塞状态。
        LockSupport.park();
        System.out.println("block.");
    }
}
