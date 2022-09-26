package cn.com.sdd.study.concurrent.reentrantreadwritelock;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName DeathLockTest
 * @Author suidd
 * @Description 死锁测试
 * 死锁的定义是线程A占有着线程B需要的资源，线程B占有着线程A需要的资源，两个线程相互等待对方释放资源，经典的死锁例子如下
 * @Date 14:40 2020/5/24
 * @Version 1.0
 **/
public class DeathLockTest {
    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        new Thread(() -> {
            synchronized (a) {
                LockSupport.parkNanos(1000000);
                synchronized (b) {

                }
            }
        }).start();

        new Thread(() -> {
            synchronized (b) {
                synchronized (a) {

                }
            }
        }).start();
    }
}
