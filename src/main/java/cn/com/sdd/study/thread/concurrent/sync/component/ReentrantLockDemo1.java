package cn.com.sdd.study.thread.concurrent.sync.component;

import cn.com.sdd.common.DateUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockDemo1
 * @Author suidd
 * @Description Lock接口的独占性演示
 * @Date 9:03 2020/5/5
 * @Version 1.0
 **/
public class ReentrantLockDemo1 {
    //两个线程竞争一个锁
    //从输出结果中可以看到，A获取到锁，先执行，而必须等到A执行完成之后，B才能执行。因此Lock对象的实际上是一个独占锁。
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        new Thread("Thread A") {
            @Override
            public void run() {
                lock.lock();//加锁
                try {
                    work();//work
                } finally {
                    lock.unlock();//释放锁
                }
            }
        }.start();

        new Thread("Thread B") {
            @Override
            public void run() {
                lock.lock();//加锁
                try {
                    work();//work
                } finally {
                    lock.unlock();//释放锁
                }
            }
        }.start();

    }

    public static void work() {
        try {
            System.out.println(Thread.currentThread().getName() + " started to work,date:" + DateUtil.getNowTime());
            Thread.currentThread().sleep(1000);
            System.out.println(Thread.currentThread().getName() + " end work,date:" + DateUtil.getNowTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
