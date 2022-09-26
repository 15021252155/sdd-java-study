package cn.com.sdd.study.concurrent.longadder;

/**
 * @author suidd
 * @name FalseSharingTest
 * @description 伪共享测试
 * @date 2020/5/19 15:49
 * Version 1.0
 **/
public class FalseSharingTest {
    public static void main(String[] args) throws InterruptedException {
        //伪共享，导致性能低下
        testPointer(new Pointer());
        //解决伪共享1：在两个 long 类型的变量之间再加 7 个 long 类型
        testPointer1(new Pointer1());
        //解决伪共享2：重新创建自己的 long 类型，而不是 java 自带的 long
        testPointer2(new Pointer2());
        //解决伪共享3：@sun.misc.Contended注解来避免伪共享
        //默认使用这个注解是无效的，需要在JVM启动参数加上-XX:-RestrictContended才会生效
        testPointer3(new Pointer3());
    }

    private static void testPointer(Pointer pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.x++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.y++;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(System.currentTimeMillis() - start);
        System.out.println("testPointer:" + pointer);
    }

    private static void testPointer1(Pointer1 pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.x++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.y++;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(System.currentTimeMillis() - start);
        System.out.println("testPointer1:" + pointer);
    }

    private static void testPointer2(Pointer2 pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.x.value++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.y.value++;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(System.currentTimeMillis() - start);
        System.out.println("testPointer2:" + pointer);
    }

    private static void testPointer3(Pointer3 pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.x.value++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000000; i++) {
                pointer.y.value++;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(System.currentTimeMillis() - start);
        System.out.println("testPointer3:" + pointer);
    }
}

class Pointer {
    volatile long x;
    volatile long y;
}

//在两个 long 类型的变量之间再加 7 个 long 类型
class Pointer1 {
    volatile long x;
    long p1, p2, p3, p4, p5, p6, p7;
    volatile long y;
}

//
class Pointer2 {
    MyLong x = new MyLong();
    MyLong y = new MyLong();
}

//重新创建自己的 long 类型，而不是 java 自带的 long
class MyLong {
    volatile long value;
    long p1, p2, p3, p4, p5, p6, p7;
}

class Pointer3 {
    MyContendedLong x = new MyContendedLong();
    MyContendedLong y = new MyContendedLong();
}

@sun.misc.Contended
class MyContendedLong {
    volatile long value;
}