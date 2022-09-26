package cn.com.sdd.study.thread.tongge.thread.createthread;

/**
 * @ClassName CreatingThread01
 * @Author suidd
 * @Description 继承Thread类并重写run()方法
 * @Date 21:28 2020/5/28
 * @Version 1.0
 **/
public class CreatingThread01 extends Thread {
    @Override
    public void run() {
        System.out.println(getName() + " is running");
    }

    public static void main(String[] args) {
        new CreatingThread01().start();
        new CreatingThread01().start();
        new CreatingThread01().start();
        new CreatingThread01().start();
    }
}
