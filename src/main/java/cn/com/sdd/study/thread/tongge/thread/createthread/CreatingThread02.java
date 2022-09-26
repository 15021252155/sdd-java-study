package cn.com.sdd.study.thread.tongge.thread.createthread;

/**
 * @ClassName CreatingThread02
 * @Author suidd
 * @Description 实现Runnable接口
 * @Date 21:31 2020/5/28
 * @Version 1.0
 **/
public class CreatingThread02 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running");
    }

    public static void main(String[] args) {
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
        new Thread(new CreatingThread02()).start();
    }
}
