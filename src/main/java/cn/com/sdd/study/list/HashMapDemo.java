package cn.com.sdd.study.list;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author suidd
 * @name HashMapDemo
 * @description HashMap学习示例
 * @date 2020/5/8 9:18
 * Version 1.0
 **/
public class HashMapDemo {
    public static void main(String[] args) {
        Map map = new HashMap(16);
        map.put(null, null);
        Map map2 = new LinkedHashMap(16);
        map2.put(null, null);
        Map map3 = new WeakHashMap(16);
        map3.put(null, null);
        map.put("Aa", "sdd");
        map.put("BB", "sdd2");
        map.put("age", "1");
        map.put("sex", "2");
        map.put("no", "3");
        map.put("id", "4");
        System.out.println(hash("sdd"));
        System.out.println(hash("qqq"));
        System.out.println(hash("aaa"));

        //第一步：准备数据
        Node root = new Node("火车头");
        Node n1 = new Node("车厢A");
        Node n2 = new Node("车厢B");

        root.setNext(n1);
        n1.setNext(n2);

        //第二步：取出所有数据
        Node currentNode = root;//从当前根节点开始读取
        while (currentNode != null) {
            System.out.println(currentNode.getData());
            //将下一个节点设置为当前节点s
            currentNode = currentNode.getNext();
        }

        //利用递归操作链接节点
        print(root);
    }

    private static void print(Node current) {
        if (current == null) {//递归结束条件
            return;
        }
        System.out.println(current.getData());
        print(current.getNext());
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}