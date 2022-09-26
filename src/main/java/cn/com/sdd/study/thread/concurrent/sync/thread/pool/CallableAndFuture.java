package cn.com.sdd.study.thread.concurrent.sync.thread.pool;

import cn.com.sdd.common.DateUtil;

import java.util.concurrent.*;

/**
 * @ClassName CallableAndFuture
 * @Author suidd
 * @Description 使用Callable与Future
 * 在之前的代码中，我们使用的是“自旋锁+共享变量”的方式来完成案例的需求，下面我们看一下使用Callable与Future怎样实现。
 * <p>
 * 一般情况下是配合ExecutorService来使用的，在ExecutorService接口中声明了若干个submit方法的重载版本
 * @Date 22:29 2020/5/5
 * @Version 1.0
 **/
public class CallableAndFuture {
    /*
    这段代码的运行结果与自旋锁的案例是一致的。需要说明的是Callable<T>和Future<T>都有一个泛型参数，这指的是线程运行返回值结果类型。

    用future.get()方法，会进行阻塞，直到线程运行完并返回结果。

　   Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果。必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。
     */
    public static void main(String[] args) {
        System.out.println("开始时间:" + DateUtil.getNowTime());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //Future与Callable中的泛型，就是返回值的类型
        Future<String> future = executorService.submit(() -> {
            Thread.sleep(2000);
            return "Hello World!";
        });

        try {
            String result = future.get();// 该方法会进行阻塞，等待执行完成
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("结束时间:" + DateUtil.getNowTime());
        executorService.shutdown();
    }
}
