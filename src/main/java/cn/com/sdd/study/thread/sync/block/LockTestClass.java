package cn.com.sdd.study.thread.sync.block;

import cn.com.sdd.common.DateUtil;

/**
 * @ClassName LockTestClass
 * @Author suidd
 * @Description 锁的测试类
 * @Date 11:30 2020/5/4
 * @Version 1.0
 **/
public class LockTestClass {
    //用于类锁计数
    private static int i = 0;
    //私有锁
    private Object object = new Object();

    /**
     * &lt;p&gt;
     * 无锁方法
     *
     * @param threadID
     * @param thread
     */
    public void noSynMethod(long threadID, ObjThread thread) {
        System.out.println("nosyn: class obj is " + thread + ", threadId is"
                + threadID);
    }

    /**
     * 对象锁方法1
     */
    public synchronized void synOnMethod() {
        System.out.println("synOnMethod begins" + ", time = "
                + DateUtil.getNowTime());
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("synOnMethod ends"+ ", time = "
                + DateUtil.getNowTime());
    }

    /**
     * 对象锁方法2,采用synchronized (this)来加锁
     */
    public void synInMethod() {
        synchronized (this) {
            System.out.println("synInMethod begins" + ", time = "
                    + DateUtil.getNowTime());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("synInMethod ends"+ ", time = "
                    + DateUtil.getNowTime());
        }

    }

    /**
     * 对象锁方法3
     */
    public void synMethodWithObj() {
        synchronized (object) {
            System.out.println("synMethodWithObj begins" + ", time = "
                    + DateUtil.getNowTime());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("synMethodWithObj ends"+ ", time = "
                    + DateUtil.getNowTime());
        }
    }

    /**
     * 类锁
     */
    public static synchronized void increment() {
        System.out.println("class synchronized. i = " + i + ", time = "
                + DateUtil.getNowTime());
        i++;
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("class synchronized ends."+ ", time = "
                + DateUtil.getNowTime());
    }
}
