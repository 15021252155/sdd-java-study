package cn.com.sdd.study.list;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ClassName CopyOnWriteArraySetDem
 * @Author suidd
 * @Description CopyOnWriteArraySet
 * CopyOnWriteArraySet底层是使用CopyOnWriteArrayList存储元素的，所以它并不是使用Map来存储元素的。
 * <p>
 * 但是，我们知道CopyOnWriteArrayList底层其实是一个数组，它是允许元素重复的
 * @Date 15:13 2020/5/17
 * @Version 1.0
 **/
public class CopyOnWriteArraySetDem {
    public static void main(String[] args) {
        //比较两个Set中的元素是否完全相等
        Set<Integer> set1 = new CopyOnWriteArraySet<>();
        set1.add(1);
        set1.add(5);
        set1.add(2);
        set1.add(7);
        //set1.add(3);
        set1.add(4);

        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        set2.add(5);
        set2.add(2);
        set2.add(7);
        set2.add(3);

        System.out.println(eq(set1, set2));

        System.out.println(eq(set2, set1));
    }

    private static <T> boolean eq(Set<T> set1, Set<T> set2) {
        if (set1.size() != set2.size()) {
            return false;
        }

        for (T t : set1) {
            // contains相当于一层for循环
            if (!set2.contains(t)) {
                return false;
            }
        }

        return true;
    }

}
