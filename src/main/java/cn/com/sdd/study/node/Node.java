package cn.com.sdd.study.node;

/**
 * @author suidd
 * @name Node
 * @description 双向链表的节点类
 * @date 2020/5/8 10:32
 * Version 1.0
 **/
public class Node {
    public Object e;
    public Node next;
    public Node pre;

    public Node() {

    }

    public Node(Object e) {
        this.e = e;
        next = null;
        pre = null;
    }
}