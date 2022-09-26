package cn.com.sdd.study.concurrent.atomicstampedreference;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author suidd
 * @name ABATest2
 * @description 理解ABA的危害，现实的例子
 * @date 2020/5/21 10:10
 * Version 1.0
 **/
public class ABATest2 {
    static class Stack {
        // 将top放在原子类中
        private AtomicReference<Node> top = new AtomicReference<>();

        // 栈中节点信息
        static class Node {
            int value;
            Node next;

            public Node(int value) {
                this.value = value;
            }
        }

        // 出栈操作
        public Node pop() {
            for (; ; ) {
                // 获取栈顶节点
                Node t = top.get();
                if (t == null) {
                    return null;
                }
                // 栈顶下一个节点
                Node next = t.next;
                // CAS更新top指向其next节点
                if (top.compareAndSet(t, next)) {
                    // 把栈顶元素弹出，应该把next清空防止外面直接操作栈
                    t.next = null;
                    return t;
                }
            }
        }

        // 入栈操作
        public void push(Node node) {
            for (; ; ) {
                // 获取栈顶节点
                Node next = top.get();
                // 设置栈顶节点为新节点的next节点
                node.next = next;
                // CAS更新top指向新节点
                if (top.compareAndSet(next, node)) {
                    return;
                }
            }
        }
    }

    /**
     * 假如，我们初始化栈结构为 top->1->2->3，然后有两个线程分别做如下操作：
     * <p>
     * （1）线程1执行pop()出栈操作，但是执行到if (top.compareAndSet(t, next)) {这行之前暂停了，所以此时节点1并未出栈；
     * <p>
     * （2）线程2执行pop()出栈操作弹出节点1，此时栈变为 top->2->3；
     * <p>
     * （3）线程2执行pop()出栈操作弹出节点2，此时栈变为 top->3；
     * <p>
     * （4）线程2执行push()入栈操作添加节点1，此时栈变为 top->1->3；
     * <p>
     * （5）线程1恢复执行，比较节点1的引用并没有改变，执行CAS成功，此时栈变为 top->2；
     * <p>
     * What？栈变成 top->2 了？不是应该变成 top->3 吗？
     * <p>
     * 那是因为线程1在第一步保存的next是节点2，所以它执行CAS成功后top节点就指向了节点2了。
     */
    private static void testStack() {
        // 初始化栈为 top->1->2->3
        Stack stack = new Stack();
        stack.push(new Stack.Node(3));
        stack.push(new Stack.Node(2));
        stack.push(new Stack.Node(1));

        new Thread(() -> {
            // 线程1出栈一个元素
            stack.pop();
            System.out.println("线程1出栈操作，弹出节点1");
        }).start();

        new Thread(() -> {
            // 线程2出栈两个元素
            Stack.Node A = stack.pop();
            System.out.println("线程2出栈操作，弹出节点1");
            Stack.Node B = stack.pop();
            System.out.println("线程2出栈操作，弹出节点2");
            // 线程2又把A入栈了
            stack.push(A);
            System.out.println("线程2入栈操作，添加节点1");
        }).start();
    }

    public static void main(String[] args) {
        testStack();
    }
}