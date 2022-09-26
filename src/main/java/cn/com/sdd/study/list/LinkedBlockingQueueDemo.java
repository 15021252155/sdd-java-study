package cn.com.sdd.study.list;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName LinkedBlockingQueueDemo
 * @Author suidd
 * @Description LinkedBlockingQueue是java并发包下一个以单链表实现的阻塞队列，它是线程安全的
 * @Date 17:23 2020/5/17
 * @Version 1.0
 **/
public class LinkedBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        int NCPUS = Runtime.getRuntime().availableProcessors();

        System.out.println(NCPUS);

        LinkedBlockingQueue q = new LinkedBlockingQueue();
        q.offer("aaa");
        q.put("bbb");


        ReentrantLock lock=new ReentrantLock();
        lock.lock();

        lock.lockInterruptibly();
    }
}
