package cn.com.sdd.study.thread.sync.block;

import cn.com.sdd.common.DateUtil;

/**
 * @ClassName ObjectLock
 * @Author suidd
 * @Description Java类锁、对象锁、私有锁、隐式锁
 * 为了明确后文的描述，先对本文涉及到的锁的相关定义作如下约定：
 * <p>
 * 1. 类锁：在代码中的方法上加了static和synchronized的锁，或者synchronized(xxx.class）的代码段，如下文中的increament()；
 * <p>
 * 2.对象锁：在代码中的方法上加了synchronized的锁，或者synchronized(this）的代码段，如下文中的synOnMethod()和synInMethod()；
 * <p>
 * 3.私有锁：在类内部声明一个私有属性如private Object lock，在需要加锁的代码段synchronized(lock），如下文中的synMethodWithObj()。
 * @Date 11:28 2020/5/4
 * @Version 1.0
 **/
public class ObjectLock {
    /*
       结论：

       类锁和对象锁不会产生竞争，二者的加锁方法不会相互影响。

       私有锁和对象锁也不会产生竞争，二者的加锁方法不会相互影响。

       synchronized直接加在方法上和synchronized(this)都是对当前对象加锁，二者的加锁方法够成了竞争关系，同一时刻只能有一个方法能执行。

        */
    public static void main(String[] args) {
        System.out.println("start time = " + DateUtil.getNowTime());
        LockTestClass test = new LockTestClass();
        for (int i = 0; i < 3; i++) {
            Thread thread = new ObjThread(test, i);
            thread.start();
        }
    }
}
