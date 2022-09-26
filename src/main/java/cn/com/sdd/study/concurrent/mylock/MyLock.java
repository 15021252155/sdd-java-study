package cn.com.sdd.study.concurrent.mylock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @ClassName MyLock
 * @Author suidd
 * @Description 自己手写一个锁Lock
 * @Date 11:06 2020/5/23
 * @Version 1.0
 **/
public class MyLock {
    //判断的当前是否加锁 0：未加锁 1：已加锁
    private volatile int state;
    // 链表头
    private volatile Node head;
    // 链表尾
    private volatile Node tail;
    // Unsafe实例
    private static Unsafe unsafe;
    // state偏移量
    private static long stateOffset;
    // 链表尾偏移量
    private static long tailOffset;
    // 空对象
    static final Node EMPTY = new Node();

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
            stateOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("state"));
            tailOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("tail"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static class Node {
        // 存储的元素为线程
        Thread thread;
        // 前一个节点（可以没有，但实现起来很困难）
        Node prev;
        // 后一个节点
        Node next;

        public Node() {
        }

        public Node(Thread thread, Node prev) {
            this.thread = thread;
            this.prev = prev;
        }
    }

    /**
     * @return
     * @Author suidd
     * @Description CAS原子更新state
     * @Date 11:15 2020/5/23
     * @Param
     * @Version 1.0
     **/
    private boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    /**
     * @return
     * @Author suidd
     * @Description CAS原子更新链表尾
     * @Date 11:25 2020/5/23
     * @Param
     * @Version 1.0
     **/
    private boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);

    }

    /**
     * @return
     * @Author suidd
     * @Description 构造方法
     * @Date 18:52 2020/5/23
     * @Param
     * @Version 1.0
     **/
    public MyLock() {
        // 初始化链表头和链表尾为空节点
        head = tail = EMPTY;
    }

    /**
     * @return
     * @Author suidd
     * @Description 加锁
     * @Date 11:26 2020/5/23
     * @Param
     * @Version 1.0
     **/
    public void lock() {
        // 尝试更新state字段，更新成功说明占有了锁
        if (compareAndSetState(0, 1)) {
            return;
        }

        //当前线程未取得锁，入队排队
        Node node = enqueue();
        Node prev = node.prev;
        // 再次尝试获取锁，需要检测上一个节点是不是head，按入队顺序加锁
        while (prev != head || !compareAndSetState(0, 1)) {
            // 未获取到锁，阻塞
            unsafe.park(false, 0L);
        }
        // 下面不需要原子更新，因为同时只有一个线程访问到这里
        // 获取到锁了且上一个节点是head
        // head后移一位
        head = node;
        // 清空当前节点的内容，协助GC
        node.thread = null;
        // 将上一个节点从链表中剔除，协助GC
        node.prev = null;
        prev.next = null;
    }

    /**
     * @return
     * @Author suidd
     * @Description 入队
     * @Date 11:28 2020/5/23
     * @Param
     * @Version 1.0
     **/
    private Node enqueue() {
        while (true) {
            // 获取尾节点
            Node t = tail;
            // 构造新节点
            Node node = new Node(Thread.currentThread(), t);
            // 不断尝试原子更新尾节点
            if (compareAndSetTail(t, node)) {
                // 更新尾节点成功了，让原尾节点的next指针指向当前节点
                t.next = node;
                return node;
            }
        }
    }

    /**
     * @return
     * @Author suidd
     * @Description 解锁
     * @Date 11:26 2020/5/23
     * @Param
     * @Version 1.0
     **/
    public void unlock() {
        // 把state更新成0，这里不需要原子更新，因为同时只有一个线程访问到这里
        state = 0;
        // 下一个待唤醒的节点
        Node next = head.next;
        // 下一个节点不为空，就唤醒它
        if (next != null) {
            unsafe.unpark(next.thread);
        }
    }

    private static int count = 0;

    //测试自己手写的锁
    public static void main(String[] args) throws InterruptedException {
        MyLock lock = new MyLock();
        CountDownLatch countDownLatch = new CountDownLatch(1000);

        IntStream.range(0, 1000).forEach(i -> new Thread(() -> {
            lock.lock();

            try {
                IntStream.range(0, 10000).forEach(j -> count++);
            } finally {
                lock.unlock();
            }

            countDownLatch.countDown();
        }, "thread-" + i).start());

        countDownLatch.await();

        System.out.println(count);
    }
}