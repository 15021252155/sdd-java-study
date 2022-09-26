package cn.com.sdd.study.thread.tongge.thread.createthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName CreatingThread04
 * @Author suidd
 * @Description 实现Callabe接口
 * @Date 21:38 2020/5/28
 * @Version 1.0
 **/
public class CreatingThread04 implements Callable {
    @Override
    public Object call() throws Exception {
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + " is running");
        return Thread.currentThread().getId();
    }
    
    //实现Callabe接口，可以获取线程执行的结果，FutureTask实际上实现了Runnable接口。
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Long> task = new FutureTask<>(new CreatingThread04());
        new Thread(task).start();
        System.out.println("等待完成任务");
        Long result = task.get();
        System.out.println("任务结果：" + result);

    }
}
