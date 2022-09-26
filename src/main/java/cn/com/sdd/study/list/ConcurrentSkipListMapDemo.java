package cn.com.sdd.study.list;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @ClassName ConcurrentSkipListMapDemo
 * @Author suidd
 * @Description ConcurrentSkipListMap
 * ConcurrentSkipListMap是线程安全的有序的哈希表，适用于高并发的场景。
 * <p>
 * ConcurrentSkipListMap和TreeMap，它们虽然都是有序的哈希表。它们的区别如下：
 * <p>
 * 第一，它们的线程安全机制不同，TreeMap是非线程安全的，而ConcurrentSkipListMap是线程安全的。
 * <p>
 * 第二，ConcurrentSkipListMap是通过跳表实现的，而TreeMap是通过红黑树实现的。
 * @Date 21:40 2020/5/16
 * @Version 1.0
 **/
public class ConcurrentSkipListMapDemo {
    public static void main(String[] args) {
        ConcurrentSkipListMap map = new ConcurrentSkipListMap();
        map.put(1, 1);
        map.put(3, 3);
        map.put(5, 5);
        map.put(8, 8);
        map.put(12, 12);
        map.put(9, 9);

        map.remove(9);
    }
}
