package cn.com.sdd.study.thread.concurrent.sync.component;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName BoundedBuffer
 * @Author suidd
 * @Description Condition使用案例
 * <p>
 * Condition 实例实质上被绑定到一个锁上。要为特定 Lock 实例获得 Condition 实例，可以使用其 newCondition() 方法。
 * <p>
 * 作为一个示例，假定有一个有界的缓冲区，它支持 put 和 take 方法。如果试图在空的缓冲区上执行 take 操作，
 * <p>
 * 则在某一个项变得可用之前，线程将一直阻塞；如果试图在满的缓冲区上执行 put 操作，则在有空间变得可用之前，线程将一直阻塞。
 * <p>
 * 我们喜欢在单独的等待 set 中保存 put 线程和 take 线程，这样就可以在缓冲区中的项或空间变得可用时利用最佳规划，一次只通知一个线程。
 * <p>
 * 可以使用两个 Condition 实例来做到这一点。
 * @Date 10:12 2020/5/5
 * @Version 1.0
 **/
public class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putprt, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putprt] = x;
            if (++putprt == items.length)
                putprt = 0;
            ++count;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object task() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length)
                takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            lock.unlock();
        }
        return null;
    }
}
