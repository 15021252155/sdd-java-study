package cn.com.sdd.study.thread.basic;

/**
 * 模拟文件处理
 */
public class ResourceDemo {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 业务场景
         * 5秒读取文件A
         * 2秒处理文件A
         * 5秒读取文件B
         * 2秒处理文件B
         * ---------------------
         * 总共需要14秒
         */
        final  long start=System.currentTimeMillis();
        System.out.println("----------程序开始运行---------");
        System.out.println("读取A文件开始...");
        Thread.currentThread().sleep(5000);
        System.out.println("读取A文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理A文件");
        Thread.currentThread().sleep(2000);
        System.out.println("A文件处理完成...，耗时："+(System.currentTimeMillis()-start)/1000+"秒");
        System.out.println("读取B文件开始...");
        Thread.currentThread().sleep(5000);
        System.out.println("读取B文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理B文件");
        Thread.currentThread().sleep(2000);
        System.out.println("B文件处理完成...，耗时："+(System.currentTimeMillis()-start)/1000+"秒");
    }
}
