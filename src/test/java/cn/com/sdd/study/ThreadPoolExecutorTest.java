package cn.com.sdd.study;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @ClassName ThreadPoolExecutorTest
 * @Author suidd
 * @Description 演示ThreadPoolExecutor的各个特性
 * 排队有三种通用策略：
 * <p>
 * 1、直接提交。工作队列的默认选项是 SynchronousQueue，它将任务直接提交给线程而不保持它们。
 * 在此，如果不存在可用于立即运行任务的线程，则试图把任务加入队列将失败，因此会构造一个新的线程。
 * 此策略可以避免在处理可能具有内部依赖性的请求集时出现锁。直接提交通常要求无界 maximumPoolSizes 以避免拒绝新提交的任务。
 * 当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。
 * <p>
 * 2、无界队列。使用无界队列（例如，不具有预定义容量的 LinkedBlockingQueue）将导致在所有 corePoolSize 线程都忙时新任务在队列中等待。
 * 这样，创建的线程就不会超过 corePoolSize。（因此，maximumPoolSize 的值也就无效了。）当每个任务完全独立于其他任务，即任务执行互不影响时，
 * 适合于使用无界队列；例如，在 Web 页服务器中。这种排队可用于处理瞬态突发请求，当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。
 * <p>
 * 3、有界队列。当使用有限的 maximumPoolSizes 时，有界队列（如 ArrayBlockingQueue）有助于防止资源耗尽，但是可能较难调整和控制。
 * 队列大小和最大池大小可能需要相互折衷：使用大型队列和小型池可以最大限度地降低 CPU 使用率、操作系统资源和上下文切换开销，但是可能导致人工降低吞吐量。
 * 如果任务频繁阻塞（例如，如果它们是 I/O 边界），则系统可能为超过您许可的更多线程安排时间。使用小型队列通常要求较大的池大小，CPU 使用率较高，
 * 但是可能遇到不可接受的调度开销，这样也会降低吞吐量。
 * @Date 15:25 2020/5/5
 * @Version 1.0
 **/
public class ThreadPoolExecutorTest {//测试corePoolSize和MaximumPoolSize随着任务提交量的变化,以及keepAliveTime与TimeUnit

    /**
     * @return
     * @Author suidd
     * @Description 队列的排队策略为：直接提交
     * taskCount=maxPoolSize的情况
     * @Date 21:14 2020/5/5
     * @Param
     * @Version 1.0
     **/
    @Test
    public void threadPoolExecutorTest() throws InterruptedException {
        int corePoolSize = 2;//核心线程池大小
        int maximumPoolSize = 5;//最大线程池大小
        int keepAliveTime = 5;//线程空闲时间
        TimeUnit seconds = TimeUnit.SECONDS;//线程空闲时间单位
        //队列排队有三种通用策略 1.直接提交 2.无界队列 3.有界队列
        BlockingQueue workQueue = new SynchronousQueue(); //队列的排队策略为：直接提交
        int taskCount = 5;//任务总数
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, seconds, workQueue);
        //如果在构建ThreadPoolExecutor的时候，没有指定ThreadFactory，默认就会使用 Executors.defaultThreadFactory()获取ThreadFactory实例
        //自定义ThreadFactory
        //threadPoolExecutor.setThreadFactory(new NamedThreadFactory());
        doTest(keepAliveTime, taskCount, threadPoolExecutor);
    }

    /**
     * @return
     * @Author suidd
     * @Description 队列的排队策略为：直接提交
     * taskCount>maxPoolSize的情况
     * @Date 21:14 2020/5/5
     * @Param
     * @Version 1.0
     **/
    @Test
    public void threadPoolExecutorTest2() throws InterruptedException {
        int corePoolSize = 2;//核心线程池大小
        int maximumPoolSize = 5;//最大线程池大小
        int keepAliveTime = 5;//线程空闲时间
        TimeUnit seconds = TimeUnit.SECONDS;//线程空闲时间单位
        //队列排队有三种通用策略 1.直接提交 2.无界队列 3.有界队列
        BlockingQueue workQueue = new SynchronousQueue(); //队列的排队策略为：直接提交
        int taskCount = 10;//任务总数
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, seconds, workQueue);
        //如果在构建ThreadPoolExecutor的时候，没有指定ThreadFactory，默认就会使用 Executors.defaultThreadFactory()获取ThreadFactory实例
        //自定义ThreadFactory
        //threadPoolExecutor.setThreadFactory(new NamedThreadFactory());
        doTest(keepAliveTime, taskCount, threadPoolExecutor);
    }

    /**
     * @return
     * @Author suidd
     * @Description 有界队列的情况下，taskCount>maxPoolSize+taskQueueSize的情况
     * @Date 21:15 2020/5/5
     * @Param
     * @Version 1.0
     **/
    @Test
    public void threadPoolExecutorTest3() throws InterruptedException {
        int corePoolSize = 2;//核心线程池大小
        int maximumPoolSize = 5;//最大线程池大小
        int keepAliveTime = 5;//线程空闲时间
        TimeUnit seconds = TimeUnit.SECONDS;//线程空闲时间单位
        //队列排队有三种通用策略 1.直接提交 2.无界队列 3.有界队列
        BlockingQueue workQueue = new LinkedBlockingDeque<>(4);///队列的排队策略为：有界队列
        int taskCount = 15;//将taskCount改为10，超出maxPoolSize+taskQueueSize=9
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, seconds, workQueue);
        //如果在构建ThreadPoolExecutor的时候，没有指定ThreadFactory，默认就会使用 Executors.defaultThreadFactory()获取ThreadFactory实例
        //自定义ThreadFactory
        //threadPoolExecutor.setThreadFactory(new NamedThreadFactory());
        doTest(keepAliveTime, taskCount, threadPoolExecutor);
    }

    /**
     * @return
     * @Author suidd
     * @Description 无界队列的情况下
     * @Date 21:20 2020/5/5
     * @Param
     * @Version 1.0
     **/
    @Test
    public void threadPoolExecutorTest4() throws InterruptedException {
        int corePoolSize = 2;//核心线程池大小
        int maximumPoolSize = 5;//最大线程池大小
        int keepAliveTime = 5;//线程空闲时间
        TimeUnit seconds = TimeUnit.SECONDS;//线程空闲时间单位
        //队列排队有三种通用策略 1.直接提交 2.无界队列 3.有界队列
        BlockingQueue workQueue = new LinkedBlockingDeque<>();///队列的排队策略为：无界队列
        int taskCount = 10;//将taskCount改为10，超出maxPoolSize+taskQueueSize
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, seconds, workQueue);
        //如果在构建ThreadPoolExecutor的时候，没有指定ThreadFactory，默认就会使用 Executors.defaultThreadFactory()获取ThreadFactory实例
        //自定义ThreadFactory
        //threadPoolExecutor.setThreadFactory(new NamedThreadFactory());
        doTest(keepAliveTime, taskCount, threadPoolExecutor);
    }

    private void doTest(int keepAliveTime, int taskCount, ThreadPoolExecutor threadPoolExecutor)
            throws InterruptedException {
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        System.out.println("-------threadPoolExecutor刚刚创建----------");
        printPoolSize(threadPoolExecutor);
        for (int i = 1; i <= taskCount; i++) {
            threadPoolExecutor.execute(new Task(threadPoolExecutor, i));
            System.out.print("--------已提交任务" + i + "个任务--------");
            printPoolSize(threadPoolExecutor);
        }

        //等到所有的任务都执行完
        TimeUnit.SECONDS.sleep(11);//休眠11秒，保证子线程都执行完
        System.out.println("---------所有的任务都执行完--------");
        printPoolSize(threadPoolExecutor);

        //此时maximumPoolSize>corePoolSize，当前时间再休眠keepAliveTime时间，测试多出corePoolSize的线程是否能自动销毁
        System.out.println("---------休眠keepAliveTime，测试maximumPoolSize>corePoolSize的部分能否自动回收--------");
        TimeUnit.SECONDS.sleep(keepAliveTime);//再休眠keepAliveTime时间，确保当前空闲时间已经大于设置的keepAliveTime值
        printPoolSize(threadPoolExecutor);
    }

    private void printPoolSize(ThreadPoolExecutor threadPoolExecutor) {
        int corePoolSize = threadPoolExecutor.getCorePoolSize();
        int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        int poolSize = threadPoolExecutor.getPoolSize();
        System.out.println("核心线程池大小：" + corePoolSize + ",最大线程池大小:" + maximumPoolSize + ",当前线程池大小:" + poolSize);
    }

    class Task implements Runnable {
        private ThreadPoolExecutor threadPoolExecutor;
        private int taskId;

        public Task(ThreadPoolExecutor threadPoolExecutor, final int taskId) {
            this.threadPoolExecutor = threadPoolExecutor;
            this.taskId = taskId;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);//休眠10秒
                System.out.println("第" + taskId + "个任务执行完:");
                printPoolSize(threadPoolExecutor);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
