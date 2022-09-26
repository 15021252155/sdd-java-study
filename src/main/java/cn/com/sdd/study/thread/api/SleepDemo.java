package cn.com.sdd.study.thread.api;

import java.util.Date;

/**
 * @ClassName SleepDemo
 * @Author suidd
 * @Description 线程的休眠
 * @Date 16:57 2020/5/3
 * @Version 1.0
 **/
public class SleepDemo {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                //我们希望不断的去检查服务器的状态，所以将检查的代码放入一个死循环中
                while (true) {
                    //模拟表示检查服务器状态
                    System.out.println("检查服务器状态....当前时间:" + new Date().toString());
                    try {
                        //休眠3秒，以免检查频繁
                        this.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
