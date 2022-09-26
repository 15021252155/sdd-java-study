package cn.com.sdd.study.thread.api;

/**
 * @ClassName ThreadGroupDemo
 * @Author suidd
 * @Description 线程组
 * @Date 21:21 2020/5/3
 * @Version 1.0
 **/
public class ThreadGroupDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        ThreadGroup threadGroup = t1.getThreadGroup();
        System.out.println("threadGroup="+threadGroup);
        ThreadGroup systemThreadGroup = threadGroup.getParent();
        System.out.println("systemThreadGroup="+systemThreadGroup);
        systemThreadGroup.list();//列出线程组树形结构，只会打印出已经start的线程信息
    }
}
