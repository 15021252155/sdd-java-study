package cn.com.sdd.study.list;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ConcurrentHashMapDemo
 * @Author suidd
 * @Description ConcurrentHashMap是HashMap的线程安全版本
 * 总结
 * （1）ConcurrentHashMap是HashMap的线程安全版本；
 * <p>
 * （2）ConcurrentHashMap采用（数组 + 链表 + 红黑树）的结构存储元素；
 * <p>
 * （3）ConcurrentHashMap相比于同样线程安全的HashTable，效率要高很多；
 * <p>
 * （4）ConcurrentHashMap采用的锁有 synchronized，CAS，自旋锁，分段锁，volatile等；
 * <p>
 * （5）ConcurrentHashMap中没有threshold和loadFactor这两个字段，而是采用sizeCtl来控制；
 * <p>
 * （6）sizeCtl = -1，表示正在进行初始化；
 * <p>
 * （7）sizeCtl = 0，默认值，表示后续在真正初始化的时候使用默认容量；
 * <p>
 * （8）sizeCtl > 0，在初始化之前存储的是传入的容量，在初始化或扩容后存储的是下一次的扩容门槛；
 * <p>
 * （9）sizeCtl = (resizeStamp << 16) + (1 + nThreads)，表示正在进行扩容，高位存储扩容邮戳，低位存储扩容线程数加1；
 * <p>
 * （10）更新操作时如果正在进行扩容，当前线程协助扩容；
 * <p>
 * （11）更新操作会采用synchronized锁住当前桶的第一个元素，这是分段锁的思想；
 * <p>
 * （12）整个扩容过程都是通过CAS控制sizeCtl这个字段来进行的，这很关键；
 * <p>
 * （13）迁移完元素的桶会放置一个ForwardingNode节点，以标识该桶迁移完毕；
 * <p>
 * （14）元素个数的存储也是采用的分段思想，类似于LongAdder的实现；
 * <p>
 * （15）元素个数的更新会把不同的线程hash到不同的段上，减少资源争用；
 * <p>
 * （16）元素个数的更新如果还是出现多个线程同时更新一个段，则会扩容段（CounterCell）；
 * <p>
 * （17）获取元素个数是把所有的段（包括baseCount和CounterCell）相加起来得到的；
 * <p>
 * （18）查询操作是不会加锁的，所以ConcurrentHashMap不是强一致性的；
 * <p>
 * （19）ConcurrentHashMap中不能存储key或value为null的元素；
 * @Date 11:21 2020/5/16
 * @Version 1.0
 **/
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put("Aa", "sdd");
        map.put("BB", "sdd2");
        map.put("uid", "1");
        map.put("uname", "sdd");
        map.put("usex", "man");
        map.put("uage", "18");

    }
}
