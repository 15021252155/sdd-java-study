package cn.com.sdd.study.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @ClassName LinkedListDemo
 * @Author suidd
 * @Description LinkedList
 * @Date 9:12 2020/5/16
 * @Version 1.0
 **/
public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList(10000);
        Thread linkThread = new Thread(() -> {
            long linkStartTimie = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                linkedList.add("data" + i);
            }
            System.out.println("link list 插入完毕，用时：" + (System.currentTimeMillis() - linkStartTimie));
        });
        linkThread.start();

        Thread arrayThread = new Thread(() -> {
            long arrayStartTimie = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                arrayList.add("data" + i);
            }
            System.out.println("array list 插入完毕，用时：" + (System.currentTimeMillis() - arrayStartTimie));
        });
        arrayThread.start();

        try {
            linkThread.join();
            arrayThread.join();

            System.out.println("link list................");
            long linkStart = System.currentTimeMillis();
            Thread linkFor = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    linkedList.get(i);
                }
                System.out.println("使用for-i迭代一共花了" + (System.currentTimeMillis() - linkStart));
            });
            linkFor.start();
            Thread linkFor2 = new Thread(() -> {
                Iterator linkIt = linkedList.iterator();
                while (linkIt.hasNext()) {
                    linkIt.next();
                }
                System.out.println("使用Iterator迭代一共花了" + (System.currentTimeMillis() - linkStart));
            });
            linkFor2.start();

            linkFor.join();
            linkFor2.join();

            System.out.println("array list................");
            Thread listFor = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    arrayList.get(i);
                }
                System.out.println("使用for-i迭代一共花了" + (System.currentTimeMillis() - linkStart));
            });
            listFor.start();
            Thread listFor2 = new Thread(() -> {
                Iterator arrayIT = arrayList.iterator();
                while (arrayIT.hasNext()) {
                    arrayIT.next();
                }
                System.out.println("使用Iterator迭代一共花了" + (System.currentTimeMillis() - linkStart));
            });
            listFor2.start();
            listFor.join();
            listFor2.join();

            System.out.println("end............................................");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
