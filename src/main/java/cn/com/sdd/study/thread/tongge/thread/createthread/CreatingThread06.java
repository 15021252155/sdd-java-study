package cn.com.sdd.study.thread.tongge.thread.createthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @ClassName CreatingThread06
 * @Author suidd
 * @Description 线程池
 * @Date 21:45 2020/5/28
 * @Version 1.0
 **/
public class CreatingThread06 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        IntStream.range(0, 100)
                .forEach(i -> {
                    executorService.execute(() -> {
                        System.out.println(Thread.currentThread().getName() + " is running");
                    });
                });
    }
}
