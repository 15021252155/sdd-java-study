package cn.com.sdd.study.thread.concurrent.sync.thread.pool;

/**
 * @ClassName SmartFutureListener
 * @Author suidd
 * @Description SmartFuture监听器
 * @Date 22:56 2020/5/5
 * @Version 1.0
 **/
public interface SmartFutureListener<V> {
    public void onSuccess(V result);

    public void onError(Throwable throwable);
}
