package cn.com.sdd.study.concurrent.volatiledemo;

/**
 * @author suidd
 * @name VolatileTest
 * @description volatile的可见性示例
 * @date 2020/5/22 14:42
 * Version 1.0
 **/
public class VolatileTest {
    public static volatile int finished = 0;

    private static void checkFinished() {
        while (finished == 0) {
            // do nothing
        }
        System.out.println("finished...");
    }

    private static void finish() {
        finished = 1;
    }

    public static void main(String[] args) throws InterruptedException {
        //启动一个线程1 检查是否完成
        new Thread(() -> checkFinished()).start();

        Thread.sleep(100);

        //主线程将finished标志置为1
        finish();

        System.out.println("main finished");
        /*
        在上面的代码中，针对finished变量，使用volatile修饰时这个程序可以正常结束，不使用volatile修饰时这个程序永远不会结束。

        因为不使用volatile修饰时，checkFinished()所在的线程每次都是读取的它自己工作内存中的变量的值，这个值一直为0，所以一直都不会跳出while循环。

        使用volatile修饰时，checkFinished()所在的线程每次都是从主内存中加载最新的值，当finished被主线程修改为1的时候，它会立即感知到，进而会跳出while循环。
         */
    }
}