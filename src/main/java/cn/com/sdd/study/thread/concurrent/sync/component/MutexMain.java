package cn.com.sdd.study.thread.concurrent.sync.component;

import cn.com.sdd.common.DateUtil;

/**
 * @ClassName MutexMain
 * @Author suidd
 * @Description 测试类
 * @Date 23:21 2020/5/4
 * @Version 1.0
 **/
public class MutexMain {
    /*

    可以看到，我们的独占锁的确是起作用了，任意一时刻只有一个线程在运行。

    请注意输出结果线程启动的顺序：1，0，2，3,4。线程1先获取到了锁并执行，而0、2、3、4被加入到了等待队列。

    而后面获取到锁的顺序也是0,2,3,4。这是因为AQS内部是使用一个FIFO队列，所以先进入等待队列的先获取到锁。（每次启动顺序都会不一样）
     */
    public static void main(String[] args) {
        Mutex mutex = new MutexImpl();
        for (int i = 0; i < 5; i++) {
            new MutexThread("线程" + i, mutex).start();

        }
    }

    //匿名内部类-测试线程
    static class MutexThread extends Thread {
        private Mutex mutex;

        public MutexThread(String name, Mutex mutex) {
            this.setName(name);
            this.mutex = mutex;

        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "启动..");
            mutex.lock();
            System.out.println(Thread.currentThread().getName() + "获取锁成功..");
            try {
                System.out.println(Thread.currentThread().getName() + "开始执行,当前时间:" + DateUtil.getNowTime());
                Thread.currentThread().sleep(1000);//假设线程执行需要1秒钟
                System.out.println(Thread.currentThread().getName() + "结束执行,当前时间:" + DateUtil.getNowTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + "释放锁..");
                mutex.release();
            }
        }
    }
}
