package cn.com.sdd.study.thread.sync.block;

/**
 * @ClassName Counter
 * @Author suidd
 * @Description Java同步实例--对象锁
 * 计数器
 * @Date 10:12 2020/5/4
 * @Version 1.0
 **/
public class Counter {
    long count = 0;

    public synchronized void add(long value) {
        this.count += value;
        System.out.println(Thread.currentThread().getName() + ":" + count);
    }
}

//计数线程
class CounterThread extends Thread {
    protected Counter counter = null;

    public CounterThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            counter.add(1);
        }
    }
}

/*
启动了两个线程，都调用Counter类同一个实例的add方法。因为同步在该方法所属的实例上，所以同时只能有一个线程访问该方法。

创建了两个线程。他们的构造器引用同一个Counter实例。Counter.add方法是同步在实例上，是因为add 方法是实例方法并且被标记上synchronized关键字。

因此每次只允许一个线程调用该方法。另外一个线程必须要等到第一个线程退出add()方法 时，才能继续执行方法。
 */
class Example {
    public static void main(String[] args) {
        Counter counter = new Counter();
        CounterThread thread1 = new CounterThread(counter);
        CounterThread thread2 = new CounterThread(counter);
        thread1.start();
        thread2.start();
    }
}

class Example2 {
    public static void main(String[] args) {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        CounterThread thread1 = new CounterThread(counter1);
        CounterThread thread2 = new CounterThread(counter2);
        thread1.start();
        thread2.start();
    }
}