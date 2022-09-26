package cn.com.sdd.study.thread.concurrent;

/**
 * @ClassName MyRunnable
 * @Author suidd
 * @Description Java内存模型
 * @Date 16:56 2020/5/4
 * @Version 1.0
 **/
public class MyRunnable implements Runnable {
    public void run() {
        methodOne();
    }

    public void methodOne() {
        int localVariable1 = 45;

        MySharedObject localVariable2 = MySharedObject.sharedInstance;

        //... do more with local variables.

        methodTwo();
    }

    public void methodTwo() {
        Integer localVariable1 = new Integer(99);

        //... do more with local variable.
    }
}