package cn.com.sdd.study.list;

import java.util.HashSet;
import java.util.Iterator;

/**
 * @ClassName HashSetDemo
 * @Author suidd
 * @Description TODO
 * @Date 19:40 2020/5/13
 * @Version 1.0
 **/
public class HashSetDemo {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet(16);
        set.add("1");
        set.add("2");
        Math.max(10, 15);
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String str = iter.next().toString();
            System.out.println(str);
        }

    }
}
