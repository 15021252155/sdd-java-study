package cn.com.sdd.study.thread.tongge.thread.myexecutor;

/**
 * @author suidd
 * @name RejectPolicy
 * @description RejectPolicy拒绝策略接口
 * @date 2020/5/29 13:37
 * Version 1.0
 **/
public interface RejectPolicy {
    void reject(Runnable task, MyThreadPoolExecutor myThreadPoolExecutor);
}