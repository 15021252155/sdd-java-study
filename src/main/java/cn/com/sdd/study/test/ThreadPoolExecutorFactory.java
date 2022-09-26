package cn.com.sdd.study.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yinbin
 * 公用线程工厂
 */
public final class ThreadPoolExecutorFactory {
    private final static Logger log = LoggerFactory.getLogger(ThreadPoolExecutorFactory.class);

    private ThreadPoolExecutorFactory() {
    }

    private volatile static ThreadPoolExecutor threadPoolExecutor;
    /**
     * 核心线程数
     */
    private static final int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
    /***
     * 最大线程数
     */
    private static final int maximumPoolSize = Runtime.getRuntime().availableProcessors() << 1 + 1;
    /**
     * 读取数据阻塞队列数
     */
    private static final int size = cn.com.huak.databasic.receive.common.EnvTools.getPropertyForInt("receive.public.threadpool.queue");

    /**
     * 获取线程池实例
     *
     * @return ThreadPoolExecutor
     */
    public static ThreadPoolExecutor getInstance() {
        if (threadPoolExecutor == null) {
            synchronized (ThreadPoolExecutor.class) {
                if (threadPoolExecutor == null) {
                    //构造线程工厂
                    final ThreadFactory nameFactory = new ThreadFactoryBuilder()
                            .setNameFormat("public-task-%d")
                            .setDaemon(false)
                            .build();
                    //todo 线程池内核通过配置文件获取
                    threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L,
                            TimeUnit.SECONDS, new LinkedBlockingQueue<>(size), nameFactory);
                    log.info("公共线程池已启动");
                }
            }
        }
        return threadPoolExecutor;
    }
}