package cn.com.sdd.study.thread.concurrent.sync.component;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ReentrantLockDemo2
 * @Author suidd
 * @Description Lock接口的可重入性演示
 * @Date 9:10 2020/5/5
 * @Version 1.0
 **/
public class ReentrantLockDemo2 {
    public static void main(String[] args) {
        //注意这里直接定义了ReentrantLock，通过直接使用这个类而不是Lock接口，我们可以使用其独有的方法getHoldCount()，这个方法表示的是当前线程持有锁的次数
        ReentrantLock lock = new ReentrantLock();
        System.out.println(lock.getHoldCount());//没有调用lock之前，hold count为0
        lock.lock();//holdCount+1
        System.out.println(lock.getHoldCount());
        lock.lock();//holdCount+1
        System.out.println(lock.getHoldCount());
        lock.unlock();//holdCount-1
        System.out.println(lock.getHoldCount());
        lock.unlock();//holdCount-1
        System.out.println(lock.getHoldCount());
    }
}
