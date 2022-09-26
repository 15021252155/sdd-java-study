package cn.com.sdd.study.thread.sync.block;

/**
 * @ClassName MyWaitNotify4
 * @Author suidd
 * @Description 线程通信时要注意的问题-不要在字符串常量或全局对象中调用wait()
 * 如果你有多个线程在等待，被notifyAll()唤醒，但只有一个被允许继续执行，使用while循环也是个好方法。每次只有一个线程可以获得监 视器对象锁，
 * <p>
 * 意味着只有一个线程可以退出wait()调用并清除wasSignalled标志（设为false）。一旦这个线程退出doWait()的同 步块，其他线程退出wait()调用，
 * <p>
 * 并在while循环里检查wasSignalled变量值。但是，这个标志已经被第一个唤醒的线程清除了，所以其余 醒来的线程将回到等待状态，直到下次信号到来。
 * <p>
 * 不要在字符串常量或全局对象中调用wait()
 *
 * 在空字符串作为锁的同步块(或者其他常量字符串)里调用wait()和notify()产生的问题是，JVM/编译器内部会把常量字符串转换成同一个对象。
 *
 * 这意味着，即使你有2个不同的MyWaitNotify实例，它们都引用了相同的空字符串实例。同时也意味着存在这样的风险：
 *
 * 在第一个 MyWaitNotify实例上调用doWait()的线程会被在第二个MyWaitNotify实例上调用doNotify()的线程唤醒
 *
 *
 * 起初这可能不像个大问题。毕竟，如果doNotify()在第二个MyWaitNotify实例上被调用，真正发生的事不外乎线程A和B被错误的唤 醒了 。
 *
 * 这个被唤醒的线程（A或者B）将在while循环里检查信号值，然后回到等待状态，因为doNotify()并没有在第一个MyWaitNotify实 例上调用，而这个正是它要等待的实例。
 *
 * 这种情况相当于引发了一次假唤醒。线程A或者B在信号值没有更新的情况下唤醒。但是代码处理了这种情况，所以线程回 到了等待状态。
 *
 * 记住，即使4个线程在相同的共享字符串实例上调用wait()和notify()，doWait()和doNotify()里的信号还会被 2个MyWaitNotify实例分别保存。
 *
 * 在MyWaitNotify1上的一次doNotify()调用可能唤醒MyWaitNotify2的线程， 但是信号值只会保存在MyWaitNotify1里。
 *
 * 问题在于，由于doNotify()仅调用了notify()而不是notifyAll()，即使有4个线程在相同的字符串（空字符串）实例上等 待，只能有一个线程被唤醒。
 *
 * 所以，如果线程A或B被发给C或D的信号唤醒，它会检查自己的信号值，看看有没有信号被接收到，然后回到等待状态。而C和D都 没被唤醒来检查它们实际上接收到的信号值，这样信号便丢失了。
 *
 * 这种情况相当于前面所说的丢失信号的问题。C和D被发送过信号，只是都不能对信号作出回应。
 *
 * 如果doNotify()方法调用notifyAll()，而非notify()，所有等待线程都会被唤醒并依次检查信号值。线程A和B将回到等待 状态，但是C或D只有一个线程注意到信号，并退出doWait()方法调用。
 *
 * C或D中的另一个将回到等待状态，因为获得信号的线程在退出doWait() 的过程中清除了信号值(置为false)。
 *
 * 看过上面这段后，你可能会设法使用notifyAll()来代替notify()，但是这在性能上是个坏主意。在只有一个线程能对信号进行响应的情况下，没有理由每次都去唤醒所有线程。
 *
 * 所以：在wait()/notify()机制中，不要使用全局对象，字符串常量等。应该使用对应唯一的对象。
 *
 * 例如，每一个MyWaitNotify3的实例（前一节的例子）拥有一个属于自己的监视器对象，而不是在空字符串上调用wait()/notify()。
 *
 * @Date 16:14 2020/5/4
 * @Version 1.0
 **/
public class MyWaitNotify4 {
    String myMonitorObject = "";
    boolean wasSignalled = false;

    public void doWait() {
        synchronized (myMonitorObject) {
            while (!wasSignalled) {
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
