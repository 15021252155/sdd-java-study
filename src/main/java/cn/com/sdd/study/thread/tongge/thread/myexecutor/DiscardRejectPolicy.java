package cn.com.sdd.study.thread.tongge.thread.myexecutor;

/**
 * @author suidd
 * @name DiscardRejectPolicy
 * @description DiscardRejectPolicy丢弃策略实现类
 * @date 2020/5/29 13:37
 * Version 1.0
 **/
public class DiscardRejectPolicy implements RejectPolicy {
    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //丢弃当前任务
     * @date 2020/5/29 13:38
     **/
    @Override
    public void reject(Runnable task, MyThreadPoolExecutor myThreadPoolExecutor) {
        // do nothing
        System.out.println("discard one task");
    }
}