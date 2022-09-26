package cn.com.sdd.study.concurrent.volatiledemo;

/**
 * @author suidd
 * @name VolatileTest4
 * @description 理解内存屏障示例
 * @date 2020/5/22 16:08
 * Version 1.0
 **/
public class VolatileTest4 {
    // a不使用volatile修饰
    public static long a = 0;
    // 消除缓存行的影响
    public static long p1, p2, p3, p4, p5, p6, p7;
    // b使用volatile修饰
    public static volatile long b = 0;
    // 消除缓存行的影响
    public static long q1, q2, q3, q4, q5, q6, q7;
    // c不使用volatile修饰
    public static long c = 0;

    /*
    在a和c的两个线程的while循环中我们获取一下b，你猜怎样？如果把long x = b;这行去掉呢？运行试试吧。

    这里直接说结论了：volatile变量的影响范围不仅仅只包含它自己，它会对其上下的变量值的读写都有影响。
     */
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (a == 0) {
                long x = b;
            }
            System.out.println("a=" + a);
        }).start();

        new Thread(() -> {
            while (c == 0) {
               long x = b;
            }
            System.out.println("c=" + c);
        }).start();

        Thread.sleep(100);

        a = 1;
        b = 1;
        c = 1;
        System.out.println("end......");
    }
}