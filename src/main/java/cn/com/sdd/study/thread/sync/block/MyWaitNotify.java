package cn.com.sdd.study.thread.sync.block;

/**
 * @ClassName MyWaitNotify
 * @Author suidd
 * @Description wait()、notify()、notifyAll()与线程通信方式总结
 * 线程通信的目标是使线程间能够互相发送信号。另一方面，线程通信使线程能够等待其他线程的信号。例如，线程B可以等待线程A的一个信号，这个信号会通知线程B数据已经准备好了。
 *
 * Java有一个内建的等待机制来允许线程在等待信号的时候变为非运行状态。java.lang.Object 类定义了三个方法，wait()、notify()和notifyAll()来实现这个等待机制。
 *
 * 一个线程一旦调用了任意对象的wait()方法，就会变为非运行状态，直到另一个线程调用了同一个对象的notify()方法。为了调用 wait()或者notify()，线程必须先获得那个对象的锁。
 *
 * 也就是说，线程必须在同步块里调用wait()或者notify()。
 *
 * 等待线程将调用doWait()，而唤醒线程将调用doNotify()。当一个线程调用一个对象的notify()方法，正在等待该对象的所有线 程中将有一个线程被唤醒并允许执行（校注：这个将被唤醒的线程是随机的，
 *
 * 不可以指定唤醒哪个线程）。同时也提供了一个notifyAll()方法来唤醒正 在等待一个给定对象的所有线程。
 *
 * 如你所见，不管是等待线程还是唤醒线程都在同步块里调用wait()和notify()。这是强制性的！一个线程如果没有持有对象锁，将不能调用 wait()，notify()或者notifyAll()。
 *
 * 否则，会抛出IllegalMonitorStateException异常。
 *
 * （校注：JVM是这么实现的，当你调用wait时候它首先要检查下当前线程是否是锁的拥有者，不是则抛出IllegalMonitorStateExcept，参考JVM源码的 1422行。）
 *
 * 但是，这怎么可能？等待线程在同步块里面执行的时候，不是一直持有监视器对象（myMonitor对象）的锁吗？等待线程不能阻塞唤醒线程进入 doNotify()的同步块吗？
 *
 * 答案是：的确不能。一旦线程调用了wait()方法，它就释放了所持有的监视器对象上的锁。这将允许其他线程也可以调用 wait()或者notify()。
 *
 * 一旦一个线程被唤醒，不能立刻就退出wait()的方法调用，直到调用notify()的线程退出了它自己的同步块。换句话说：被唤醒的线程必须重新获得监视器对象的锁，
 *
 * 才可以退出wait()的方法调用，因为wait方法调用运行在同步块里面。如果多个线程被notifyAll()唤醒，那么在同一时刻 将只有一个线程可以退出wait()方法，
 *
 * 因为每个线程在退出wait()前必须获得监视器对象的锁。
 *
 * @Date 15:30 2020/5/4
 * @Version 1.0
 **/
public class MyWaitNotify {
    MonitorObject myMonitorObject = new MonitorObject();

    public void doWait() {
        synchronized (myMonitorObject) {
            try {
                myMonitorObject.wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public void doNotify() {
        synchronized (myMonitorObject) {
            myMonitorObject.notify();
        }
    }
}
