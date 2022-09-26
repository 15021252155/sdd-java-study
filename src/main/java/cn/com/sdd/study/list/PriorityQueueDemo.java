package cn.com.sdd.study.list;

import java.util.PriorityQueue;

/**
 * @ClassName PriorityQueueDemo
 * @Author suidd
 * @Description PriorityQueue优先级队列
 * @Date 22:04 2020/5/14
 * @Version 1.0
 **/
public class PriorityQueueDemo {
    HuffManDemo.Node[] nodes;

    public static void main(String[] args) {
        PriorityQueueDemo priorityQueueDemo = new PriorityQueueDemo();
        priorityQueueDemo.test();
    }

    public void test() {
        PriorityQueue queue = new PriorityQueue();
        int[] weights = {2, 3, 7, 9, 18, 25};
        nodes = new HuffManDemo.Node[weights.length];
        // 构建森林，初始化nodes数组
        for (int i = 0; i < weights.length; i++) {
            nodes[i] = new HuffManDemo.Node(weights[i]);
            queue.add(nodes[i]);
        }

        //PriorityQueue是小顶堆，每次poll是最小的元素
        for (int i = 0; i < weights.length; i++) {
            queue.poll();
        }
    }
}
