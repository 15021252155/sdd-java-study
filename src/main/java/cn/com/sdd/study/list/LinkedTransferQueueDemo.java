package cn.com.sdd.study.list;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @author suidd
 * @name LinkedTransferQueue
 * @description TODO
 * @date 2020/5/18 15:09
 * Version 1.0
 **/
public class LinkedTransferQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue q = new LinkedTransferQueue();
        q.add("111");
        q.add("222");
        q.take();
        q.add("333");

    }
}