package cn.com.sdd.study.thread.concurrent.sync.component;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName LockSupportDemo2
 * @Author suidd
 * @Description LockSupport
 * @Date 21:21 2020/5/4
 * @Version 1.0
 **/
public class LockSupportDemo2 {
    public static void main(String[] args) {
        //先释放许可，再获取许可，主线程能够正常终止。LockSupport许可的获取和释放，一般来说是对应的，如果多次unpark，只有一次park也不会出现什么问题，结果是许可处于可用状态。
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);//释放许可
        LockSupport.park();//获取许可
        System.out.println("do............");
    }
}
