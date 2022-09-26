package cn.com.sdd.study.thread.tongge.thread.myexecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * @author suidd
 * @name FutureExecutor
 * @description FutureExecutor接口，支持执行任务后有返回值
 * @date 2020/5/29 16:44
 * Version 1.0
 **/
public interface FutureExecutor extends Executor {
    <T> Future<T> submit(Callable<T> command);
}