package cn.com.sdd.study.list;

import java.util.*;

/**
 * @author suidd
 * @name TreeMapDemo
 * @description TreeMap Comparable接口测试
 * @date 2020/5/11 11:43
 * Version 1.0
 **/
public class TreeMapDemo {
    public static void main(String[] args) {
        //不明白初始化的时候，如何初始化comparator参数
        Comparator comparator = (k1, k2) -> ((Comparable) k1).compareTo(k2);

        //key是int类型
        //直接定义比较器
        Comparator<Integer> sortByNo = (Integer s1, Integer s2) -> (s1.compareTo(s2));
        TreeMap<Integer, String> treeMap = new TreeMap(sortByNo);
        treeMap.put(1, "sdd");
        treeMap.put(5, "aaaa");
        treeMap.put(4, "bbbbb");
        treeMap.put(6, "11111");
        treeMap.put(7, "222222");
        treeMap.put(3, "33333333");

        treeMap.remove(6);

        //key是String类型
        Comparator<String> sortByName = (String s1, String s2) -> (s1.compareTo(s2));
        TreeMap<String, Integer> treeMap2 = new TreeMap(sortByName);
        treeMap2.put("a", 100);
        treeMap2.put("b", 200);
        treeMap2.put("aa", 300);
        treeMap2.put("ab", 200);

        //使用自定义的比较器
        TreeMap<Integer, Double> treeMap3 = new TreeMap(new MyComparator());
        treeMap3.put(5, 12.1);
        treeMap3.put(4, 11.5);
        treeMap3.put(6, 13.44);
        treeMap3.put(7, 12.9);
        treeMap3.put(3, 8.88);


        // 构建一颗10个元素的树
        TreeNode<Integer> node = new TreeNode<>(1, null).insert(2)
                .insert(6).insert(3).insert(5).insert(9)
                .insert(7).insert(8).insert(4).insert(10);

        // 中序遍历，打印结果为1到10的顺序
        node.root().inOrderTraverse();
    }

    /**
     * treeMap底层获取树层数
     *
     * @param sz
     * @return
     */
    private static int computeRedLevel(int sz) {
        int level = 0;
        for (int m = sz - 1; m >= 0; m = m / 2 - 1)
            level++;
        return level;
    }
}


/**
 * 自定义排序比较对象
 */
class MyComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Integer v1 = (Integer) o1;
        Integer v2 = (Integer) o2;
        return v1.compareTo(v2);
    }
}

/**
 * 树节点，假设不存在重复元素
 *
 * @param <T>
 */
class TreeNode<T extends Comparable<T>> {
    T value;
    TreeNode<T> parent;
    TreeNode<T> left, right;

    public TreeNode(T value, TreeNode<T> parent) {
        this.value = value;
        this.parent = parent;
    }

    /**
     * 获取根节点
     */
    TreeNode<T> root() {
        TreeNode<T> cur = this;
        while (cur.parent != null) {
            cur = cur.parent;
        }
        return cur;
    }

    /**
     * 中序遍历
     */
    void inOrderTraverse() {
        if (this.left != null) this.left.inOrderTraverse();
        System.out.println(this.value);
        if (this.right != null) this.right.inOrderTraverse();
    }

    /**
     * 经典的二叉树插入元素的方法
     */
    TreeNode<T> insert(T value) {
        // 先找根元素
        TreeNode<T> cur = root();

        TreeNode<T> p;
        int dir;

        // 寻找元素应该插入的位置
        do {
            p = cur;
            if ((dir = value.compareTo(p.value)) < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        } while (cur != null);

        // 把元素放到找到的位置
        if (dir < 0) {
            p.left = new TreeNode<>(value, p);
            return p.left;
        } else {
            p.right = new TreeNode<>(value, p);
            return p.right;
        }
    }
}

class Item implements Comparable<Item> {
    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Item o) {
        if (this.name.compareTo(o.name) < 0) {
            return -1;
        } else if (this.name.compareTo(o.name) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("demo1，对name进行排序.............................................................");
        List<Item> items = Arrays.asList(new Item("banana", 200), new Item("apple", 400));
        System.out.println("before:" + items);
        Collections.sort(items);
        System.out.println("after:" + items);

        System.out.println("demo2，对price进行排序.............................................................");
        List<Item> items2 = Arrays.asList(new Item("banana", 200), new Item("apple", 400), new Item("orange", 100));
        Collections.sort(items2, Comparator.comparingInt((Item a) -> a.price));
        System.out.println(items2);

        System.out.println("demo3，先对name排序，相同的话在对price进行排序.............................................................");
        List<Item> items3 = Arrays.asList(new Item("banana", 100), new Item("orange", 100), new Item("apple", 400), new Item("orange", 50));
        items3.sort(Comparator.comparingInt((Item a) -> a.price));
        System.out.println(items3);

        // 使用上面的thenComparing，先对name排序，相同的话在对price进行排序
        items3.sort(Comparator.comparing(Item::getName).thenComparing((Item::getPrice)));
        System.out.println("after using thenComparing: " + items3);
    }
}