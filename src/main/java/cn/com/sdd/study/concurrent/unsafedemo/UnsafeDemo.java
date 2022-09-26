package cn.com.sdd.study.concurrent.unsafedemo;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @ClassName UnsafeDemo
 * @Author suidd
 * @Description Unsafe类介绍
 * 很多低级语言中可用的技巧在Java中都是不被允许的。Java是一个安全的开发工具，它阻止开发人员犯很多低级的错误，而大部份的错误都是基于内存管理方面的。
 * 我们知道JAVA作为高级语言的重要创新一点就是在于JVM的内存管理功能，这完全区别于C语言开发过程中需要对变量的内存分配小心控制，JVM很大程度解放了码农对于内存的调整。
 * 一直以来，JAVA在大多数人心目中没有办法对内存进行操作的，其实不然，Unsafe类就是一把操作JAVA内存的钥匙。如果你想搞破坏，可以使用Unsafe这个类。这个类是属于sun.* API中的类。
 * Unsafe做操作的是直接内存区，所以该类没有办法通过HotSpot的GC进行回收，需要进行手动回收，因此在使用此类时需要注意内存泄漏（Memory Leak）和内存溢出（Out Of Memory）。
 * 因为这是一个平台相关的类，因此在实际开发中，建议不要使用。
 * Unsafe类提供了一个静态方法getUnsafe()来获取Unsafe的实例，但是如果你尝试调用Unsafe.getUnsafe()，会得到一个SecutiryException。这个类只有被JDK信任的类实例化。
 * 但是这总会是有变通的解决办法的，一个简单的方式就是使用反射进行实例化
 * @Date 20:42 2020/5/4
 * @Version 1.0
 **/
@Slf4j
public class UnsafeDemo {
    private int i = 0;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //获取Unsafe实例
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        //获取字段i在内存中偏移量
        long offset = unsafe.objectFieldOffset(UnsafeDemo.class.getDeclaredField("i"));
        log.info("i offset is {}", offset);

        UnsafeDemo demo = new UnsafeDemo();
        //根据偏移量，获取变量i的值
        int val = unsafe.getInt(demo, offset);
        log.info("get i value by offset,i={}", val);
        //根据偏移量，设置变量i的值
        unsafe.putInt(demo, offset, 520);
        log.info("set i value by offset,i={}", demo.i);

        /*
        JVM的实现可以自由选择如何实现Java对象的“布局”，也就是在内存里Java对象的各个部分放在哪里，包括对象的实例字段和一些元数据之类。
        sun.misc.Unsafe里关于对象字段访问的方法把对象布局抽象出来，它提供了objectFieldOffset()方法用于获取某个字段相对Java对象的“起始地址”的偏移量，
        也提供了getInt、getLong、getObject之类的方法可以使用前面获取的偏移量来访问某个Java对象的某个字段。

        在上例中，我们通过putInt方法给一个int变量i赋值，类似的，Unsafe也提供了putLong、putFloat、putDouble、putChar、putByte、putShort、putBoolean、
        以及putObject等方法给对应类型的变量赋值。并提供了相应的get方法。
         */
    }
}
