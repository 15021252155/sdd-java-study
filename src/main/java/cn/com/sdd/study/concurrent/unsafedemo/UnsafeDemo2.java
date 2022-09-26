package cn.com.sdd.study.concurrent.unsafedemo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @ClassName UnsafeDemo2
 * @Author suidd
 * @Description 突破限制创建实例
 * 通过allocateInstance()方法，你可以创建一个类的实例，但是却不需要调用它的构造函数、初使化代码、各种JVM安全检查以及其它的一些底层的东西。
 * 即使构造函数是私有，我们也可以通过这个方法创建它的实例。
 * （这个对单例模式情有独钟的程序员来说将会是一个噩梦，它们没有办法阻止这种方式调用）
 * 看下面一个实例（注：为了配合这个主题，译者将原实例中的public构造函数修改为了私有的）
 * @Date 20:50 2020/5/4
 * @Version 1.0
 **/
public class UnsafeDemo2 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        //获取Unsafe实例
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        // 这将在不进行任何初始化的情况下创建player类的实例
        Player player = (Player) unsafe.allocateInstance(Player.class);
        //打印年龄
        System.out.println(player.getAge());

        //给Player未实例化对象 设置年龄
        player.setAge(45);
        //打印年龄
        System.out.println(player.getAge());

        /*
        总结:
        sun.misc.Unsafe提供了可以随意查看及修改JVM中运行时的数据结构，尽管这些功能在JAVA开发本身是不适用的，

        Unsafe是一个用于研究学习HotSpot虚拟机非常棒的工具，因为它不需要调用C++代码，或者需要创建即时分析的工具。
         */

    }
}

class Player {
    private int age;

    private Player() {
        this.age = 50;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
