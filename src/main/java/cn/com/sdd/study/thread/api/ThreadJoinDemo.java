package cn.com.sdd.study.thread.api;

/**
 * @ClassName ThreadJoinDemo
 * @Author suidd
 * @Description Thread类的join方法
 * Thread类有一个join方法，其作用是：在A线程中调用了另外一个线程对象B的join方法时，那么A线程必须等待B线程执行完才能继续往下执行。
 * @Date 17:33 2020/5/3
 * @Version 1.0
 **/
public class ThreadJoinDemo {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("程序开始运行...");
        Thread thread = new Thread(() -> {
            //模拟自定义线程干某个事花了5秒
            System.out.println("自定义线程执行开始...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("自定义线程执行完毕...");
        });
        thread.start();

        //模拟主线程干其他事情花了2秒
        Thread.sleep(2000);
        System.out.println("主线程执行完毕，等待t线程执行完成...");
        //主线程2秒后就可以继续执行，但是其必须执行的条件是t线程必须执行完成，此时调用t的join方法
        long joinStart = System.currentTimeMillis();
        thread.join();
        System.out.println("主线程：t执行已经执行完毕...，等待了" + (System.currentTimeMillis() - joinStart) / 1000 + "秒");
        System.out.println("程序运行总时间..." + (System.currentTimeMillis() - start) / 1000 + "秒");


    }
}
