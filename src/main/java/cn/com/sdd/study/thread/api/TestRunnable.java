package cn.com.sdd.study.thread.api;

import cn.com.sdd.common.SleepUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName TestRunnable
 * @Author suidd
 * @Description 守护线程
 * @Date 20:56 2020/5/3
 * @Version 1.0
 **/
public class TestRunnable implements Runnable {
    /*
    在Java中有两类线程：User Thread(用户线程)、Daemon Thread(守护线程)

    Daemon的作用是为其他线程的运行提供服务，比如说GC线程。

    其实User Thread线程和Daemon Thread守护线程本质上来说去没啥区别的，唯一的区别之处就在虚拟机的离开：如果User Thread全部撤离，那么Daemon Thread也就没啥线程好服务的了，所以虚拟机也就退出了。

    守护线程并非虚拟机内部可以提供，用户也可以自行的设定守护线程，方法：

    public final void setDaemon(boolean on);

    但是有几点需要注意：

    1）、thread.setDaemon(true)必须在thread.start()之前设置，你不能把正在运行的常规线程设置为守护线程，否则会抛出一个IllegalThreadStateException异常。

    2）、 在Daemon线程中产生的新线程也是Daemon的。

    3）、不是所有的应用都适合分配给Daemon线程来进行服务，比如读写操作或者计算逻辑。因为在Daemon Thread还没来的及进行操作时，虚拟机可能已经退出了。

    The Java Virtual Machine exits when the only threads running are all daemon threads.JVM会在所有执行的线程都是守护线程的时候退出。
     */
    public static void main(String[] args) {
        //完成文件输出的守护线程任务
        Runnable runnable = new TestRunnable();
        Thread thread = new Thread(runnable);
        //thread.setDaemon(true);//设置守护线程
        thread.start(); //开始执行分进程
    }

    @Override
    public void run() {
        try {
            System.out.println("run start....");
             SleepUtils.second(1);//守护线程阻塞1秒后运行
            //File f = new File("D:\\log\\file\\daemon.txt");
            //FileOutputStream os = new FileOutputStream(f, true);
           // os.write("daemon-sdd".getBytes());
            System.out.println("do something...");
            System.out.println("write end....");
        } catch (Exception e2) {
            e2.printStackTrace();
            System.out.println(e2.getMessage());
        }
    }
}
