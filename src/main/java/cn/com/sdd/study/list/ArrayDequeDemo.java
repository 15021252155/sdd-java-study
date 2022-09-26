package cn.com.sdd.study.list;

import java.util.ArrayDeque;

/**
 * @author suidd
 * @name ArrayDequeDemo
 * @description ArrayDeque双端队列
 * @date 2020/5/15 10:37
 * Version 1.0
 **/
public class ArrayDequeDemo {
    public static void main(String[] args) {
        tesZhan();
        //testAddFirstAndLast();
        // testAdd();
        //testpollFirst();
        // testPollFirstAndLast2();
    }


    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //从队列头、队列尾分别入队
     * 用来debug理解扩容时copy的逻辑
     * @date 2020/5/15 20:35
     **/
    public static void testAddFirstAndLast() {
        ArrayDeque arrayDeque = new ArrayDeque();
        //从队列头、队列尾分别入队
        for (int i = 1; i < 10; i++) {
            arrayDeque.addFirst("first" + i);
            arrayDeque.addLast("last" + i);
        }
    }

    public static void testpollFirst() {
        ArrayDeque arrayDeque = new ArrayDeque();
        //从队列头、队列尾分别入队
        for (int i = 1; i < 15; i++) {
            arrayDeque.addFirst("first" + i);
            arrayDeque.addLast("last" + i);
        }

        for (int i = 1; i < 1000; i++) {
            arrayDeque.pollFirst();
            System.out.println(arrayDeque);
        }

    }

    public static void testPollFirstAndLast2() {
        ArrayDeque arrayDeque = new ArrayDeque();
        //从队列头入队
        for (int i = 1; i < 17; i++) {
            arrayDeque.addFirst("first" + i);
        }

        for (int i = 1; i < arrayDeque.size() + 1; i++) {
            arrayDeque.pollFirst();
            arrayDeque.pollLast();
        }

    }

    public static void testAdd() {
        ArrayDeque arrayDeque = new ArrayDeque();
        //从队列头入队
        for (int i = 1; i < 17; i++) {
            arrayDeque.addFirst("first" + i);
        }

        ArrayDeque arrayDeque2 = new ArrayDeque();
        //从队列尾入队
        for (int i = 1; i < 18; i++) {
            arrayDeque2.addLast("last" + i);
        }
        System.out.println("testArrayCopy........................");
    }

    /**
     * @return
     * @Author suidd
     * @Description 作为栈使用
     * @Date 9:50 2020/5/16
     * @Param
     * @Version 1.0
     **/
    public static void tesZhan() {
        ArrayDeque arrayDeque = new ArrayDeque();
        //从队列头入队
        for (int i = 1; i < 8; i++) {
            arrayDeque.addFirst("first" + i);
        }

        for (int i = 1; i < 8; i++) {
            arrayDeque.removeFirst();
        }

    }
}