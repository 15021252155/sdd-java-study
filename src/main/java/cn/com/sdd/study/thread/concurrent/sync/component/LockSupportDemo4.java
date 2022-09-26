package cn.com.sdd.study.thread.concurrent.sync.component;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName LockSupportDemo4
 * @Author suidd
 * @Description LockSupport对应中断的响应性
 * @Date 21:25 2020/5/4
 * @Version 1.0
 **/
public class LockSupportDemo4 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {

            private int count = 0;

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long end = 0;
                while ((start - end) < 1000) {
                    count++;
                    end = System.currentTimeMillis();
                }
                System.out.println("after 1 second.count=" + count);

                //等待获取许可
                LockSupport.park();
                System.out.println("thread over." + Thread.currentThread().isInterrupted());
            }
        });

        //启动线程
        thread.start();
        Thread.sleep(20000);
        //中断线程
        thread.interrupt();
        System.out.println("main over");

        //最终线程会打印出thread over.true。这说明 线程如果因为调用park而阻塞的话，能够响应中断请求(中断状态被设置成true)，但是不会抛出InterruptedException 。
    }
}
