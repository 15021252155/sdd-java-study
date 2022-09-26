package cn.com.sdd.study.thread.api;

/**
 * 线程代码Demo
 */
public class ThreadExample {
    /**
     * 需要注意的是，尽管启动线程的顺序是有序的，但是执行的顺序并非是有序的。
     * 也就是说，1号线程并不一定是第一个将自己名字输出到控制台的线程。
     * 这是因为线程是并行执行而非顺序的。
     * Jvm和操作系统一起决定了线程的执行顺序，他和线程的启动顺序并非一定是一致的。
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        for (int i = 0; i < 10; i++) {
            new Thread(String.valueOf(i)) {
                @Override
                public void run() {
                    System.out.println("Thread:" + getName() + "running");
                }
            }.start();
        }
    }
}
