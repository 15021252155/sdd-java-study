package cn.com.sdd.study.thread.basic;

/**
 * 创建并运行线程
 */
public class ThreadDemo2 {
    public static void main(String[] args) {
        /**
         * 创建一个线程对象，覆盖其run方法，传入参数为线程的名字
         */
        Thread t1 = new Thread(() -> {
            for (int i = 1; i < 100; i++)
                System.out.println("自定义线程循环：" + i + "次");
        });
        //启动自定义线程
        t1.start();

        //执行主线程逻辑
        for (int i = 1; i < 100; i++)
            System.out.println("主线程循环：" + i + "次");
    }
}
