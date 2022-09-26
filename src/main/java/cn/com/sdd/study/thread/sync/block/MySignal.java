package cn.com.sdd.study.thread.sync.block;

/**
 * @ClassName MySignal
 * @Author suidd
 * @Description 通过共享对象通信
 * @Date 15:52 2020/5/4
 * @Version 1.0
 **/
public class MySignal {
    protected boolean hasDataToProcess = false;

    public synchronized boolean hasDataToProcess() {
        return this.hasDataToProcess;
    }

    public synchronized void setHasDataToProcess(boolean hasDataToProcess) {
        this.hasDataToProcess = hasDataToProcess;
    }
}
