package cn.com.sdd.study.thread.sync.block;

/**
 * @ClassName MyWaitNotify2
 * @Author suidd
 * @Description 线程通信时要注意的问题-丢失的信号（Missed Signals）
 * notify()和notifyAll()方法不会保存调用它们的方法，因为当这两个方法被调用时，有可能没有线程处于等待状态。
 * <p>
 * 通知信号过后便丢 弃了。因此，如果一个线程先于被通知线程调用wait()前调用了notify()，等待的线程将错过这个信号。这可能是也可能不是个问题。
 * <p>
 * 不过，在某些 情况下，这可能使等待线程永远在等待，不再醒来，因为线程错过了唤醒信号。
 * <p>
 * 为了避免丢失信号，必须把它们保存在信号类里。在MyWaitNotify的例子中，通知信号应被存储在MyWaitNotify实例的一个成员变量里
 * @Date 16:00 2020/5/4
 * @Version 1.0
 **/
public class MyWaitNotify2 {
    MonitorObject myMonitorObject = new MonitorObject();
    boolean wasSignalled = false;

    public void doWait() {
        synchronized (myMonitorObject) {
            if (!wasSignalled) {
                try {
                    myMonitorObject.wait();
                } catch (InterruptedException e) {
                }
            }
            //clear signal and continue running.
            wasSignalled = false;
        }
    }

    public void doNotify() {
        synchronized (myMonitorObject) {
            wasSignalled = true;
            myMonitorObject.notify();
        }
    }
}
