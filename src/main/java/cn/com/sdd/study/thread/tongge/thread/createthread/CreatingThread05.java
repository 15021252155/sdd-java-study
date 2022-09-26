package cn.com.sdd.study.thread.tongge.thread.createthread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName CreatingThread05
 * @Author suidd
 * @Description 定时器（java.util.Timer）
 * @Date 21:42 2020/5/28
 * @Version 1.0
 **/
public class CreatingThread05 {
    //使用定时器java.util.Timer可以快速地实现定时任务，TimerTask实际上实现了Runnable接口。
    public static void main(String[] args) {
        Timer timer = new Timer();
        // 每隔1秒执行一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }, 0, 1000);
    }
}
