package cn.com.sdd.study.thread.tongge.thread.createthread;

/**
 * @ClassName CreatingThread09
 * @Author suidd
 * @Description 同时继承Thread并实现Runnable接口
 * @Date 22:12 2020/5/28
 * @Version 1.0
 **/
public class CreatingThread09 {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Runnable: " + Thread.currentThread().getName());
        }) {
            @Override
            public void run() {
                System.out.println("Thread: " + getName());
            }
        }.start();
    }

}
