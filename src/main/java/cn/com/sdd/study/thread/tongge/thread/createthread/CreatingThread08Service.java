package cn.com.sdd.study.thread.tongge.thread.createthread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName CreatingThread08Service
 * @Author suidd
 * @Description @Async注解方法
 * @Date 21:53 2020/5/28
 * @Version 1.0
 **/
@Service
public class CreatingThread08Service {
    @Async
    public void call() {
        System.out.println(Thread.currentThread().getName() + " is running");
    }
}