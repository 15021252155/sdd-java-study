package cn.com.sdd.study.thread.concurrent.sync.component;

/**
 * @ClassName Mutex
 * @Author suidd
 * @Description 不可重入的独占锁接口
 * @Date 23:17 2020/5/4
 * @Version 1.0
 **/
public interface Mutex {
    //获取锁
    public void lock();
    //释放锁
    public void release();
}
