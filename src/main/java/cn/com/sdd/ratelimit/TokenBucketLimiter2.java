package cn.com.sdd.ratelimit;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TokenBucketLimiter
 * @Author suidd
 * @Description 令牌桶算法实现限流优化代码
 * @Date 10:40 2021/11/13
 * @Version 1.0
 **/
public class TokenBucketLimiter2 {
    /**
     * 令牌桶
     */
    private ArrayBlockingQueue<String> tokenBucketQueue;
    /**
     * 令牌桶大小
     */
    private int limit;
    /**
     * 生成令牌时间单位
     */
    private TimeUnit timeUnit;
    /**
     * 生成令牌时间周期
     */
    private Long cycle;
    /**
     * 默认常量字符串
     */
    private final String CONSTANT_VAL = "1";
    /**
     * 初始化延迟生成令牌时间
     */
    private final int initialDelay = 1;

    public TokenBucketLimiter2(int limit, TimeUnit timeUnit, Long cycle) {
        this.tokenBucketQueue = new ArrayBlockingQueue(limit);
        this.limit = limit;
        this.timeUnit = timeUnit;
        this.cycle = cycle;

        initTokenBucket();
        scheduleBuildToken();
    }

    /**
     * @return
     * @Author suidd
     * @Description 初始化令牌桶
     * @Date 10:43 2021/11/13
     * @Param
     * @Version 1.0
     **/
    private void initTokenBucket() {
        for (int i = 0; i < limit; i++) {
            tokenBucketQueue.add(CONSTANT_VAL);
        }
    }

    /**
     * @return
     * @Author suidd
     * @Description 获取令牌
     * @Date 10:47 2021/11/13
     * @Param
     * @Version 1.0
     **/
    public boolean tryAcquire() {
        return Objects.nonNull(tokenBucketQueue.poll());
    }

    /**
     * @return
     * @Author suidd
     * @Description 往桶里放入一个令牌
     * @Date 10:49 2021/11/13
     * @Param
     * @Version 1.0
     **/
    public void offerToken() {
        tokenBucketQueue.offer(CONSTANT_VAL);
    }

    /**
     * @return
     * @Author suidd
     * @Description 定时生成令牌
     * @Date 10:51 2021/11/13
     * @Param
     * @Version 1.0
     **/
    private void scheduleBuildToken() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            offerToken();
        }, initialDelay, cycle, timeUnit);
    }

    public static void main(String[] args) {
        // 测试，初始化生成一个令牌数量为10的令牌桶，桶容量大小为10
        // 初始化1S之后，每100毫秒生成一个新令牌放入桶中的限流器（因为桶大小固定，所以相当于1S中可以生成10个令牌）
        TokenBucketLimiter2 tokenBucketLimiter = new TokenBucketLimiter2(10, TimeUnit.MILLISECONDS, 1000l);
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 为方便展示效果，自旋
        for (; ; ) {
            executorService.execute(() -> {
                if (tokenBucketLimiter.tryAcquire()) {
                    System.out.println("成功抢到令牌，处理业务逻辑...");
                } else {
                    //System.out.println("未抢到令牌，退出...");
                }
            });
        }
    }
}