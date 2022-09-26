package cn.com.sdd.study.list;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @ClassName PriorityBlockingQueueDemo
 * @Author suidd
 * @Description PriorityBlockingQueue
 * PriorityBlockingQueue是java并发包下的优先级阻塞队列，它是线程安全的
 * @Date 21:23 2020/5/17
 * @Version 1.0
 **/
public class PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        PriorityBlockingQueue q = new PriorityBlockingQueue();
        q.add("1");
        q.offer("2");
    }
}
