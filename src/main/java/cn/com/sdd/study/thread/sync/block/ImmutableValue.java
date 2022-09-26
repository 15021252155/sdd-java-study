package cn.com.sdd.study.thread.sync.block;

/**
 * @ClassName ImmutableValue
 * @Author suidd
 * @Description 线程安全及不可变性
 * 当多个线程同时访问同一个资源，并且其中的一个或者多个线程对这个资源进行了写操作，才会产生竞态条件。
 *
 * 多个线程同时读同一个资源不会产生竞态条件。
 *
 * 我们可以通过创建不可变的共享对象来保证对象在线程间共享时不会被修改，从而实现线程安全
 *
 * 请注意ImmutableValue类的成员变量value是通过构造函数赋值的，并且在类中没有set方法。
 *
 * 这意味着一旦ImmutableValue实例被创建，value变量就不能再被修改，这就是不可变性。但你可以通过getValue()方法读取这个变量的值。
 *
 * （译者注：注意，“不变”（Immutable）和“只读”（Read Only）是不同的。当一个变量是“只读”时，变量的值不能直接改变，但是可以在其它变量发生改变的时候发生改变。
 *
 * 比如，一个人的出生年月日是“不变”属 性，而一个人的年龄便是“只读”属性，但是不是“不变”属性。
 *
 * 随着时间的变化，一个人的年龄会随之发生变化，而一个人的出生年月日则不会变化。这就是“不 变”和“只读”的区别。（摘自《Java与模式》第34章））
 *
 * @Date 11:04 2020/5/4
 * @Version 1.0
 **/
public class ImmutableValue {
    private int value = 0;

    public ImmutableValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public ImmutableValue add(int valueToAdd) {
        return new ImmutableValue(this.value + valueToAdd);
    }
}
