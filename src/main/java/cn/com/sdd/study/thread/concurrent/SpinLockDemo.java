package cn.com.sdd.study.thread.concurrent;

/**
 * @ClassName SpinLockDemo
 * @Author suidd
 * @Description 自旋锁解决StackOverflowError案例
 * @Date 18:10 2020/5/4
 * @Version 1.0
 **/
public class SpinLockDemo {
    int count = 0;

    public void incr() {
        count++;
        System.out.println("执行了:" + count + "次");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        //这段代码其实就是一个自旋锁
        while (spinLockDemo.count != 1000000)
            spinLockDemo.incr();
        System.out.println("执行其他代码...");
    }
}
