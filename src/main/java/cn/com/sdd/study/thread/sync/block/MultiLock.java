package cn.com.sdd.study.thread.sync.block;

/**
 * @ClassName MultiLock
 * @Author suidd
 * @Description 同步代码块与隐式锁
 * synchronized关键字的作用就是用于声明这是一段同步代码块。JVM在遇到synchronized关键字时，会把花括号"{...}"中间的代码当成一个原子操作，也就是说，
 *
 * 只有等到同步代码块中的代码在执行完成的时候，CPU才会进行线程的上下文切换，而不会再同步代码块中的内容只执行了一部分的时候，就切换到其他线程运行。
 *
 * 每个synchronized关键字都必须要配合锁进行使用，在java中，任何对象实例都可以当做一个锁来使用，而上面代码中，ThreadCompetitionDemo.class表示的一个Class对象的实例，
 *
 * 因此其也是一个锁。我们经常会看到一些概念，例如隐式锁(intrinsic lock)、监视器锁(monitor lock)，其实指的都是把一个普通java对象当成一个锁来使用。
 *
 * 在java官方的并发编程教程中，就提到每一个java对象都会关联一个隐式锁，因此当我们在使用synchronized关键字编写同步代码块时，实际上利用的就是小括号中的java对象的关联的隐式锁。
 *
 * 需要注意的是，在使用同步代码块解决多线程竞争共享资源的问题时，我们使用的必须是同一把锁。所谓同一把锁，其实指得就是同一个对象实例。
 *
 * 多个线程竞争同一把锁，先得到锁的先执行，在同步代码块执行完成之后，就把自动把锁释放掉。然后其他线程再来抢夺这把锁，进行代码的执行。
 *
 * 读者从这里应该看到，如果多个线程竞争的是同一把锁，JVM除了保证同步代码块中的内容可以以原子方式执行，其实还隐含了另外一个功能，就是先后执行的顺序，
 *
 * 先得到锁的先执行，这实际上是happens-before原则，我们在后面会详细详解。
 *
 * @Date 11:14 2020/5/4
 * @Version 1.0
 **/
public class MultiLock {
    static int count = 0;
    //定义两个锁，前面我们已经提到，任何的Java对象实例都可以当成锁，这里用两个不同的字符串实例表示两个不同的锁
    static String lock1 = "lock1";
    static String lock2 = "lock2";

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 5000000; i++) {
                    synchronized (lock1) {//使用lock1
                        count++;
                    }
                }
                System.out.println("自定义线程:计算完成...，耗时" + (System.currentTimeMillis() - start));
            }
        }.start();
        for (int i = 0; i < 5000000; i++) {
            synchronized (lock2) {//使用lock2
                count++;
            }
        }
        System.out.println("主线程:计算完成....，耗时" + (System.currentTimeMillis() - start));
        Thread.sleep(10000);
        System.out.println("count:" + count);
    }
}
