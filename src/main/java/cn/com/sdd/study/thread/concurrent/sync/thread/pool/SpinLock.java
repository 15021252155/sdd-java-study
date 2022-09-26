package cn.com.sdd.study.thread.concurrent.sync.thread.pool;

import cn.com.sdd.common.DateUtil;
import org.springframework.util.StringUtils;

/**
 * @ClassName SpinLock
 * @Author suidd
 * @Description Callable与Future
 * Callable与Future的作用是，我们可以启动一个线程去执行某个任务，而另外一个线程等待获取这个结果后执行响应的操作。
 * <p>
 * 假设我们有这样一个案例，线程A中进行某种运算，而主线程需要等待其运算结果，以便进行接下来的操作。
 * @Date 22:12 2020/5/5
 * @Version 1.0
 **/
public class SpinLock {
    public volatile static String sharedVariable;//共享变量

    //在没有使用Callable与Future之前，我们要实现这样的效果，通常需要通过共享变量和自旋锁实现(或者使用wait、notify)。使用自旋锁的代码案例如下
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                Thread.sleep(2000);//进行运算操作，以休眠代替
                sharedVariable = "hello world!";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("开始时间：" + DateUtil.getNowTime());
        while (StringUtils.isEmpty(sharedVariable)) {
            //小睡一下就行,不然主线程飞速空转一直在访问shareVar耗住了cpu，自定义线程可能没有机会对shareVal进行读-写-赋值操作
            //或者将共享变量设置为volatile
            Thread.sleep(1);
        }

        System.out.println("sharedVariable=" + sharedVariable);
        System.out.println("结束时间：" + DateUtil.getNowTime());
    }
}
