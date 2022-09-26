package cn.com.sdd.study.list;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName CopyOnWriteArrayListDemo
 * @Author suidd
 * @Description ArrayList的线程安全版本
 * 总结
 * （1）CopyOnWriteArrayList使用ReentrantLock重入锁加锁，保证线程安全；
 * <p>
 * （2）CopyOnWriteArrayList的写操作都要先拷贝一份新数组，在新数组中做修改，修改完了再用新数组替换老数组，所以空间复杂度是O(n)，性能比较低下；
 * <p>
 * （3）CopyOnWriteArrayList的读操作支持随机访问，时间复杂度为O(1)；
 * <p>
 * （4）CopyOnWriteArrayList采用读写分离的思想，读操作不加锁，写操作加锁，且写操作占用较大内存空间，所以适用于读多写少的场合；
 * <p>
 * （5）CopyOnWriteArrayList只保证最终一致性，不保证实时一致性；
 * @Date 10:20 2020/5/16
 * @Version 1.0
 **/
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        list.add("sdd");
        list.size();
    }
}
