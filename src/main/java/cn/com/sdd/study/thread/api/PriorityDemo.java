package cn.com.sdd.study.thread.api;

/**
 * @ClassName PriorityDemo
 * @Author suidd
 * @Description 线程的优先级
 * @Date 20:23 2020/5/3
 * @Version 1.0
 **/
public class PriorityDemo {
    /*
    java中Thread对象有一个优先级的概念，优先级被划分10个级别，创建线程的时候，如果没有指定优先级，默认是5。主线程的优先级也是5。优先级高的线程会比优先级低的线程获得更多的运行机会。

    Thread类定义了3个整形常量MAX_PRIORITY、NORM_PRIORITY、MIN_PRIORITY分别用于表示支持的最高优先级，正常优先级和最低优先级。

    同时提供了一个getPriority()方法来获取当前线程优先级。
     */
    public static void main(String[] args) {
        System.out.println("最大优先级:" + Thread.MAX_PRIORITY);
        System.out.println("正常优先级:" + Thread.NORM_PRIORITY);
        System.out.println("最小优先级:" + Thread.MIN_PRIORITY);
        System.out.println("主线程优先级:" + Thread.currentThread().getPriority());
        Thread t = new Thread();
        System.out.println("创建一个线程默认的优先级:" + t.getPriority());
    }
}
