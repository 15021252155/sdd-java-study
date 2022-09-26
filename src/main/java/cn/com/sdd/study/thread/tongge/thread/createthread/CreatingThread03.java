package cn.com.sdd.study.thread.tongge.thread.createthread;

/**
 * @ClassName CreatingThread03
 * @Author suidd
 * @Description 匿名内部类
 * @Date 21:34 2020/5/28
 * @Version 1.0
 **/
public class CreatingThread03 {
    public static void main(String[] args) {
        // Thread匿名类，重写Thread的run()方法
        new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " is running");
            }
        }.start();

        // Runnable匿名类，实现其run()方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }).start();

        // 同上，使用lambda表达式函数式编程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " is running");
        }).start();
    }
}
