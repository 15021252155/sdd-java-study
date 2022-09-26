package cn.com.sdd.study.thread.basic;

/**
 * 多线程处理模拟文件处理
 */
public class ResourceThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 业务场景
         * 5秒读取文件A
         * 2秒处理文件A
         * 5秒读取文件B
         * 2秒处理文件B
         * ---------------------
         * 总共需要12秒
         */
        final long start = System.currentTimeMillis();
        System.out.println("----------程序开始运行---------");
        System.out.println("读取A文件开始...");
        Thread.currentThread().sleep(5000);
        System.out.println("读取A文件结束，耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒...开始处理A文件，同时开始读取B文件..");
        Thread t1 = new Thread(
                () -> {
                    try {
                        System.out.println("读取B文件开始...");
                        Thread.currentThread().sleep(5000);
                        System.out.println("读取B文件结束，耗时：" + (System.currentTimeMillis() - start) / 1000 + "秒...开始处理B文件");
                        Thread.currentThread().sleep(2000);
                        System.out.println("B文件处理完成...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        t1.start();
        Thread.currentThread().sleep(2000);
        System.out.println("A文件处理完成...");
        t1.join();
        System.out.println("总耗时:" + (System.currentTimeMillis() - start) / 1000 + "秒");
    }
}
