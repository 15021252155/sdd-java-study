package cn.com.sdd.study.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suidd
 * @name ArrayListDemo
 * @description ArrayList测试
 * 总结
 * （1）ArrayList内部使用数组存储元素，当数组长度不够时进行扩容，每次加一半的空间，ArrayList不会进行缩容；
 * <p>
 * （2）ArrayList支持随机访问，通过索引访问元素极快，时间复杂度为O(1)；
 * <p>
 * （3）ArrayList添加元素到尾部极快，平均时间复杂度为O(1)；
 * <p>
 * （4）ArrayList添加元素到中间比较慢，因为要搬移元素，平均时间复杂度为O(n)；
 * <p>
 * （5）ArrayList从尾部删除元素极快，时间复杂度为O(1)；
 * <p>
 * （6）ArrayList从中间删除元素比较慢，因为要搬移元素，平均时间复杂度为O(n)；
 * <p>
 * （7）ArrayList支持求并集，调用addAll(Collection c)方法即可；
 * <p>
 * （8）ArrayList支持求交集，调用retainAll(Collection c)方法即可；
 * <p>
 * （7）ArrayList支持求单向差集，调用removeAll(Collection c)方法即可；
 * @date 2020/5/7 8:38
 * Version 1.0
 **/
public class ArrayListDemo {
    public static void main(String[] args) {
        //比较两个List中的元素是否完全相等
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(3);
        list1.add(6);
        list1.add(3);
        list1.add(8);
        list1.add(5);

        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(1);
        list2.add(3);
        list2.add(8);
        list2.add(5);
        list2.add(6);

        System.out.println(eq(list1, list2));
        System.out.println(eq(list2, list1));
        System.out.println("=================================================================");
        List<String> list8ss = new ArrayList<>(9);
        System.out.println(list8ss.size());

        List<String> list7 = new ArrayList<>(0);
        list7.add("1");

        List<String> list8 = new ArrayList<>(9);
        list8.add("1");
        List<String> list9 = new ArrayList<>(10);
        list9.add("1");
        List<String> list11 = new ArrayList<>(11);
        list11.add("1");

        List<String> oldList = new ArrayList<>(10);
        oldList.add("1");
        oldList.add("2");
        oldList.add("3");

        List<String> newList = new ArrayList<>(10);
        newList.add("3");
        newList.add("4");
        newList.add("5");

        List<String> removeList = new ArrayList<>(oldList);
        //筛选出不包含在newList元素，执行删除错误
        removeList.removeAll(newList);
        System.out.println(removeList);

        List<String> addList = new ArrayList<>(newList);
        //筛选出不包含oldList的元素，用于添加操作1
        addList.removeAll(oldList);
        System.out.println(addList);
        // List<String> retainList =oldList.retainAll(newList);
    }

    private static <T> boolean eq(List<T> list1, List<T> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        // 标记某个元素是否找到过，防止重复
        boolean matched[] = new boolean[list2.size()];

        outer:
        for (T t : list1) {
            for (int i = 0; i < list2.size(); i++) {
                // i这个位置没找到过才比较大小
                if (!matched[i] && list2.get(i).equals(t)) {
                    matched[i] = true;
                    continue outer;
                }
            }
            return false;
        }

        return true;
    }

}