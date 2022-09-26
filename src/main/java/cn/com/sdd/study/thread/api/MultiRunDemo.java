package cn.com.sdd.study.thread.api;

/**
 * @ClassName MultiRunDemo
 * @Author suidd
 * @Description 覆盖了Thread对象的run方法，同时传入一个Runnable的实现类(实现了其run方法)，会执行哪个？
 * @Date 16:45 2020/5/3
 * @Version 1.0
 **/
public class MultiRunDemo {
    /**
     * 看一下Thread类Runnable方法的实现
     * private Runnable target;
     * ....
     *
     * @Override public void run() {
     * if (target != null) {
     * target.run();
     * }
     * }
     * 其中target就是Runable对象，默认的run方法的逻辑是，如果传递了Runnable的实现类，就运行Runnable的run方法。
     * 在我们的案例中，由于我们覆写了这段代码，所以这段逻辑根本就不存在了，运行run方法时，实际上运行的是我们覆写后的方法中的内容，所以才输出了以上的打印结果。
     * 不过二者并非不能共存，这要看我们对Thread类run方法是如何覆写的，最简单的但，我们覆写的代码和run方法和Thread类中的方法完全一样，那么最终Runable接口还是会执行。
     * 当然，我们还可以在if语句前后各添加一些其他的处理，这样我们覆写的run方法中的内容和Runnable接口方法中的内容就都可以执行了，读者可以自行进行验证。
     * 最后提一嘴，Thread对象实现了Runnable接口(实现了run方法)，而构造方法中又可以接受一个Runnable接口的实现类(也实现了run方法)，称之为target，
     * 在执行的时候，如果target存在，就执行target的run方法。
     * 这实际上是装饰者设计模式(又称包装设计模式)，感兴趣的读者可以自行参考相关内容。
     */
    public static void main(String[] args) {
        //传入了Runnable实现类
        new Thread(new MyRunnable()) {
            @Override
            public void run() {
                System.out.println("我是直接覆写Thread类run方法的代码...");
            }
        }.start();
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("我是实现Runnable接口的对象中的run方法的代码...");
        }
    }
}
