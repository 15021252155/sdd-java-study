package cn.com.sdd.study.thread.api;

import java.util.Date;

/**
 * @ClassName InterruptDemo
 * @Author suidd
 * @Description 线程的中断
 * 中断(interrupt)表示一个线程应该停止当前所做的事而去另外一件事。通常中断是一个线程给另外一个线程发送中断信号，
 * 程序员自行决定如如何进行响应，也就是说收到中断信号后，接下来该做什么。通常情况下，线程收到中断信号后，采取的操作都是停止运行。
 * @Date 17:17 2020/5/3
 * @Version 1.0
 **/
public class InterruptDemo {
    /**
     * 1、在运行时代码中调用了可以抛出InterruptedException的方法
     * 线程如何支持中断？这依赖于线程的运行时代码是如何编写的。如果在运行时代码中调用了可以抛出InterruptedException的方法，那么线程在接受到的中断信号就体现在这个异常上。
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(4000);
                    System.out.println("自定义线程:当前时间：" + new Date().toLocaleString());
                } catch (InterruptedException e) {
                    //这个异常由sleep方法抛出
                    e.printStackTrace();
                    System.out.println("自定义线程:收到中断信号，总共循环了" + i + "次...");
                    //接受到中断信号时，停止运行
                    return;
                }
            }
        });
        thread.start();
        //主线程休眠9秒
        Thread.sleep(9000);
        System.out.println("主线程：等待9秒后发送中断信号...");
        thread.interrupt();
    }
}
