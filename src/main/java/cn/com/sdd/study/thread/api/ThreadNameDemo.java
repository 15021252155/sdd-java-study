package cn.com.sdd.study.thread.api;

/**
 * 线程的名字
 * 当创建一个线程的时候，如果我们不给线程明确的起一个名字的话，JVM默认会给其指定一个默认的名字。
 * 当然我们明确的可以给线程起一个名字。它有助于我们区分不同的线程，这在我们查看线程运行时的状况会很有用。
 */
public class ThreadNameDemo {
    public static void main(String[] arg) {
        //打印主线程的名称
        System.out.println("主线程名称:" + Thread.currentThread().getName());

        //创建一个线程，不指定名字，JVM会自动给其分配一个名字
        new Thread() {
            @Override
            public void run() {
                System.out.println("JVM自动分配的线程名:" + this.getName());
            }
        }.start();

        //创建一个名字为new Thread的线程并启动
        new Thread(new MyRunnableTest(), "new Thread").start();
    }
    static class MyRunnableTest implements Runnable {
        @Override
        public void run() {
            //如果我们编写的只是运行时代码，那么要获取将会执行这段运行时代码的线程的信息，通过Thread.currentThread()的方式
            System.out.println("执行这段运行时代码的线程名:" + Thread.currentThread().getName());
        }
    }
}
