package cn.com.sdd.study.list;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName ArrayBlockingQueueDemo
 * @Author suidd
 * @Description ArrayBlockingQueue是java并发包下一个以数组实现的阻塞队列，它是线程安全的
 * @Date 16:36 2020/5/17
 * @Version 1.0
 **/
public class ArrayBlockingQueueDemo {
    public static void main(String[] args) {
        ArrayBlockingQueue q = new ArrayBlockingQueue(3);
        q.add("1");
        q.add("2");
        q.add("3");

        q.remove();
        q.remove();
        q.remove();
    }
}
