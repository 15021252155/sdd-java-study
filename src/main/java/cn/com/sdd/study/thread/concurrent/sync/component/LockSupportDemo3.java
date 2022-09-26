package cn.com.sdd.study.thread.concurrent.sync.component;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName LockSupportDemo3
 * @Author suidd
 * @Description LockSupport
 * @Date 21:22 2020/5/4
 * @Version 1.0
 **/
public class LockSupportDemo3 {

    public static void main(String[] args) {
        //LockSupport是不可重入 的，如果一个线程连续2次调用 LockSupport .park()，那么该线程一定会一直阻塞下去。
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);//释放许可

        //这段代码打印出a和b，不会打印c，因为第二次调用park的时候，线程无法获取许可出现死锁。
        System.out.println("a");
        LockSupport.park();//获取许可
        System.out.println("b");
        LockSupport.park();//获取许可
        System.out.println("c");
    }
}
