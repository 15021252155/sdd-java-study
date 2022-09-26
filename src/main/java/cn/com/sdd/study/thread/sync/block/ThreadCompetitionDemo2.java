package cn.com.sdd.study.thread.sync.block;

/**
 * @ClassName ThreadCompetitionDemo2
 * @Author suidd
 * @Description 线程竞争-修改后的代码
 * @Date 22:32 2020/5/3
 * @Version 1.0
 **/
public class ThreadCompetitionDemo2 {
    //通过synchronized关键字和锁来解决ThreadCompetitionDemo类代码中出现的问题
    static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 5000000; i++) {
                    synchronized (ThreadCompetitionDemo2.class) {
                        count++;
                    }
                }
                System.out.println("自定义线程:计算完成...，耗时" + (System.currentTimeMillis() - start));
            }
        };
        thread.start();
        for (int i = 0; i < 5000000; i++) {
            synchronized (ThreadCompetitionDemo2.class) {
                count++;
            }
        }
        System.out.println("主线程:计算完成....，耗时" + (System.currentTimeMillis() - start));
        thread.join();
        Thread.sleep(1000);
        System.out.println("count = " + count);
    }
}
