package cn.com.sdd.study.list;

/**
 * @author suidd
 * @name Node
 * @description TODO
 * @date 2020/5/8 10:24
 * Version 1.0
 **/
public class Node {    //定义一个节点
    private String data; //用于保存数据
    private Node next;   //用于保存下一个节点

    //每一个Node类对象都必须保存有响应的数据
    public Node(String data) {
        this.data = data;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return this.next;
    }

    public String getData() {
        return this.data;
    }

    // 实现节点的添加
    // 第一次调用（Link）：this代表Link.root
    // 第二次调用（Node）：this代表Link.root.next
    // 第三次调用（Node）：this代表Link.root.next.next
    public void addNode(Node newNode) {
        if (this.next == null) { // 如果只有一个节点
            this.next = newNode; // 保存新节点
        } else { // 当前节点后面还有节点
            // 当前节点的下一个节点继续保存
            this.next.addNode(newNode);

        }
    }

    // 第一次调用（Link）：this代表Link.root
    // 第二次调用（Node）：this代表Link.root.next
    // 第三次调用（Node）：this代表Link.root.next.next
    public void printNode() {
        System.out.println(this.data);// 输出当前数据
        if (this.next != null) {// 如果还有下一个节点
            this.next.printNode();// 输出下一节点
        }
    }
}