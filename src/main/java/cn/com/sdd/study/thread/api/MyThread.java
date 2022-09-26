package cn.com.sdd.study.thread.api;

/**
 * 创建线程运行时代码
 * 创建Thread的子类，覆写run方法
 */
public class MyThread extends Thread {
    public void run() {
        System.out.println("MyThread running");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();

        //Thread的匿名子类
        Thread thread = new Thread() {
            public void run() {
                System.out.println("Thread Running");
            }
        };
        thread.start();


        Thread thread2=new Thread(()->{
            System.out.println("Thread2 Running");
        });
        thread2.start();

        new Thread(()->{
            System.out.println("Thread3 Running");
        }).start();

        new Thread("Thread4"){
            @Override
            public void run() {
                System.out.println("Thread4 Running");
            }
        }.start();
    }
}
