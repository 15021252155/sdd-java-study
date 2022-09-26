package cn.com.sdd.study.thread.concurrent.sync.thread.pool;

import cn.com.sdd.common.DateUtil;

import java.util.concurrent.*;

/**
 * @ClassName SmartFutureTest
 * @Author suidd
 * @Description 测试代码：SmartFutureTest
 * @Date 22:57 2020/5/5
 * @Version 1.0
 **/
public class SmartFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int corePoolSize = 5;//核心线程池大小
        int maximumPoolSize = 10;//最大线程池大小
        int keepAliveTime = 10;//线程空闲时间
        TimeUnit unit = TimeUnit.SECONDS;//线程空闲时间单位
        //队列排队有三种通用策略 1.直接提交 2.无界队列 3.有界队列
        BlockingQueue workQueue = new LinkedBlockingDeque<>();///队列的排队策略为：无界队列
        //实例化自定义的线程池
        SmartThreadExecutorPool smartThreadExecutorPool =
                new SmartThreadExecutorPool(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        //提交一个任务
        SmartFuture<String> smartFuture = smartThreadExecutorPool.submit(() -> "当前时间：" + DateUtil.getNowTime());

        smartFuture.addListener(new SmartFutureListener<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("异步回调成功：" + result);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("异步回调失败：" + throwable);
            }
        });

        String syncResult = smartFuture.get();
        System.out.println("同步回调成功：" + syncResult);
    }
}
