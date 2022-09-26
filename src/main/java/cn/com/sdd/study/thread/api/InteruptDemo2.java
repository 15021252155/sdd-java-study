package cn.com.sdd.study.thread.api;

/**
 * @ClassName InteruptDemo2
 * @Author suidd
 * @Description 线程的中断2
 * @Date 17:26 2020/5/3
 * @Version 1.0
 **/
public class InteruptDemo2 {
    /**
     * 2、在运行时代码中没有调用了可以抛出InterruptedException的方法
     * 如果在运行时代码中没有调用可以抛出中断异常的方法，那么我们必须频繁的调用Thread类的静态方法interrupted()来判断是否收到一个中断信号。
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (true) {
                //每次打印前都判断是否被中断
                if (Thread.interrupted()) {
                    //如果被中断，停止运行
                    System.out.println("自定义线程：被中断...");
                    return;
                } else {
                    i++;
                    System.out.println("自定义线程，打印...." + i + "次");
                }
            }
        });
        thread.start();
        //主线程休眠1毫秒，以便自定义线程执行
        Thread.sleep(1);
        System.out.println("主线程:休眠1毫秒后发送中断信号...");
        thread.interrupt();
    }
}
