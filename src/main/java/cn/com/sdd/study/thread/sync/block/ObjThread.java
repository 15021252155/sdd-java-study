package cn.com.sdd.study.thread.sync.block;

/**
 * @ClassName ObjThread
 * @Author suidd
 * @Description 线程类ObjThread 用于启动同步方法（注意它的run方法可能会调整以进行不同的测试）
 * @Date 11:30 2020/5/4
 * @Version 1.0
 **/
public class ObjThread extends Thread {
    LockTestClass lock;
    int i = 0;

    public ObjThread(LockTestClass lock, int i) {
        this.lock = lock;
        this.i = i;
    }

    public void run() {
        //无锁方法
//      lock.noSynMethod(this.getId(),this);
        //对象锁方法1，采用synchronized synInMethod的方式
        lock.synInMethod();
        //对象锁方法2，采用synchronized(this)的方式
        lock.synOnMethod();
        //私有锁方法，采用synchronized(object)的方式
//      lock.synMethodWithObj();
        //类锁方法，采用static synchronized increment的方式
//        LockTestClass.increment();
    }
}
