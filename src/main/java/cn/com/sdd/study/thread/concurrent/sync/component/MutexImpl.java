package cn.com.sdd.study.thread.concurrent.sync.component;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @ClassName MutexImpl
 * @Author suidd
 * @Description 不可重入的独占锁接口实现类
 * @Date 23:17 2020/5/4
 * @Version 1.0
 **/
public class MutexImpl implements Mutex {
    // 仅需要将操作代理到Sync上即可
    private Syncduzhan sync = new Syncduzhan();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void release() {
        sync.release(1);
    }

    //静态内部类：因为某个同步组件通常是为实现特定的目的而实现，可能只适用于特定的场合。
    //如果某个同步组件不具备通用性，我们应该将其定义为一个私有的静态内部类。结合第一点，我们编写的同步组件Sync应该是MutexImpl的一个私有的静态内部类。
    //独占式同步组件实现
    private static class Syncduzhan extends AbstractQueuedSynchronizer {
        /**
         * 独占方式。尝试获取资源，成功则返回true，失败则返回false。
         *
         * @param arg arg表示获取许可的数量
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0, 1);
        }

        /**
         * 独占方式。尝试释放资源，成功则返回true，失败则返回false。
         *
         * @param arg arg表示获取许可的数量
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            return compareAndSetState(1, 0);
        }
    }
}
