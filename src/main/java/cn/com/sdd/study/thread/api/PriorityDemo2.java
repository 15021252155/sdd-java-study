package cn.com.sdd.study.thread.api;

/**
 * @ClassName PriorityDemo2
 * @Author suidd
 * @Description 线程的优先级
 * @Date 20:26 2020/5/3
 * @Version 1.0
 **/
public class PriorityDemo2 {
    public static void main(String[] args) {
        /*
        演示优先级高的线程可能比一个优先级低线程获取到更多的运行机会

        从结果中我们好像看到优先级高的线程P10好像并没有比优先级低的线程P1在执行的时候有什么优势。

        这是因为线程的执行是抢占式的，优先级高的线程只是理论上会更优先的获取执行的机会，但并不意味着一定优先执行。
         */
        Thread thread1 = new Thread("thread P10") {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++)
                    System.out.println(Thread.currentThread().getName() + ":执行" + i + "次");
            }
        };
        thread1.setPriority(Thread.MAX_PRIORITY);

        Thread thread2 = new Thread("thread P1") {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++)
                    System.out.println(Thread.currentThread().getName() + ":执行" + i + "次");

            }
        };
        thread2.setPriority(Thread.MIN_PRIORITY);
        thread1.start();
        thread2.start();

    }
}
