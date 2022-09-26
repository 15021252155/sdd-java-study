package cn.com.sdd.study.thread.concurrent.sync.thread.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName NamedThreadFactory
 * @Author suidd
 * @Description ThreadFactory演示
 * 线程池中的线程默认都是根据ThreadFactory创建，如果在构建ThreadPoolExecutor的时候，没有指定ThreadFactory，
 * <p>
 * 默认就会使用 Executors.defaultThreadFactory()获取ThreadFactory实例。ThreadFactory只定义了一个抽象方法，用于返回新的线程
 * <p>
 * 我们可以完全可以创建一个自己的ThreadFactory，以下实现参考了Executors.defaultThreadFactory()。
 * @Date 20:49 2020/5/5
 * @Version 1.0
 **/
public class NamedThreadFactory implements ThreadFactory {
    private static AtomicInteger poolId;
    private static ThreadGroup threadGroup;
    private AtomicInteger threadId;
    private static String threadNamePrefix = "NamedThreadPool";

    public NamedThreadFactory() {
        poolId = new AtomicInteger();
        threadGroup = new ThreadGroup("NamedThreadFactory");
        threadId = new AtomicInteger();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = threadNamePrefix + "-pool-" + poolId.getAndIncrement() + "-thread-" + threadId;
        Thread t = new Thread(threadGroup, name);
        return t;
    }
}
