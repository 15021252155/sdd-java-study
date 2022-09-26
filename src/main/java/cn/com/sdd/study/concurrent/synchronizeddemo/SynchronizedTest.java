package cn.com.sdd.study.concurrent.synchronizeddemo;

/**
 * @ClassName SynchronizedTest
 * @Author suidd
 * @Description Synchronized是非公平锁示例
 * @Date 19:11 2020/5/22
 * @Version 1.0
 **/
public class SynchronizedTest {
    public static void sync(String tips) {
        synchronized (SynchronizedTest.class) {
            System.out.println(tips);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> sync("线程1")).start();
        Thread.sleep(100);
        new Thread(() -> sync("线程2")).start();
        Thread.sleep(100);
        new Thread(() -> sync("线程3")).start();
        Thread.sleep(100);
        new Thread(() -> sync("线程4")).start();
    }
}
