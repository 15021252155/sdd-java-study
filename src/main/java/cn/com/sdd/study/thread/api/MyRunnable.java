package cn.com.sdd.study.thread.api;

/**
 * 创建线程运行时代码
 * 实现Runnable接口
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("MyRunnable running");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        thread.start();

        Runnable runnable = () -> System.out.println("Runnable  running");
        //创建一个实现了Runnable接口的匿名类
        Thread thread2 = new Thread(runnable, "New Thread runnable ");
        thread2.start();
        System.out.println(thread2.getName());
        System.out.println(Thread.currentThread().getName());
    }
}
