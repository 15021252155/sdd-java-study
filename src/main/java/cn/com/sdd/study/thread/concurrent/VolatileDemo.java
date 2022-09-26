package cn.com.sdd.study.thread.concurrent;

/**
 * @ClassName VolatileDemo
 * @Author suidd
 * @Description volatile关键字可见性的实际使用场景--状态变量标记
 * 在前面我们提到volatile关键字可以保证多个线程运行时的可见性问题。在单核CPU的情况下，是不存在可见性问题的，如果是多核CPU，可见性问题就会暴露出来。
 * <p>
 * 我们知道线程中运行的代码最终都是交给CPU执行的，而代码执行时所需使用到的数据来自于内存(或者称之为主存)。但是CPU是不会直接操作内存的，每个CPU都会有自己的缓存，操作缓存的速度比操作主存更快。
 * <p>
 * 因此当某个线程需要修改一个数据时，事实上步骤是如下的：
 * <p>
 * 1、将主存中的数据加载到缓存中
 * <p>
 * 2、CPU对缓存中的数据进行修改
 * <p>
 * 3、将修改后的值刷新到内存中
 * <p>
 * <p>
 * <p>
 * 多个线程操作同一个变量的情况
 * 第一步：线程1、线程2、线程3操作的是主存中的同一个变量，并且分别交由CPU1、CPU2、CPU3处理。
 * <p>
 * 第二步：3个CPU分别将主存中变量加载到缓存中
 * <p>
 * 第三步：各自将修改后的值刷新到主存总
 * <p>
 * 问题就出现在第二步，因为每个CPU操作的是各自的缓存，所以不同的CPU之间是无法感知其他CPU对这个变量的修改的，最终就可能导致结果与我们的预期不符。
 * <p>
 * 而使用了volatile关键字之后，情况就有所不同，volatile关键字有两层语义：
 * <p>
 * 1、立即将缓存中数据写会到内存中
 * <p>
 * 2、其他处理器通过嗅探总线上传播过来了数据监测自己缓存的值是不是过期了，如果过期了，就会对应的缓存中的数据置为无效。而当处理器对这个数据进行修改时，会重新从内存中把数据读取到缓存中进行处理。
 * <p>
 * 在这种情况下，不同的CPU之间就可以感知其他CPU对变量的修改，并重新从内存中加载更新后的值，因此可以解决可见性问题。
 * @Date 20:19 2020/5/4
 * @Version 1.0
 **/
public class VolatileDemo {
    volatile static Boolean flag = true;

    /**
     * 在实际开发中，我们在程序中通常会有一些标记标记变量。程序运行时根据根据这个标记变量值的不同决定是否执行某段业务逻辑处理代码
     *
     * @param args
     */
    public static void main(String[] args) {
        //该线程每隔1秒，修改一次flag的值
        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        this.sleep(1000);
                        flag = !flag;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //主线程通过死循环不断根据flag进行判断是否要执行某段代码
        while (true) {
            if (flag) {
                System.out.println("do some thing...");
            } else {
                System.out.println("...");
            }
        }

        /*
        在这段代码中，由于我们使用了volatile关键字，因此自定义线程每次修改时状态变量的值时，主线程都可以实时的感知到。

        特别的，对于多个线程都依赖于同一个状态变量的值来判断是否要执行某段代码时，使用volatile关键字更为有用，其可以保证多个线程在任一时刻的行为都是一致的
         */

    }
}
