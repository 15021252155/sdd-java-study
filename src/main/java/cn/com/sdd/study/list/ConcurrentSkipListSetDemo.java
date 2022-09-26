package cn.com.sdd.study.list;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @ClassName ConcurrentSkipListSetDemo
 * @Author suidd
 * @Description TODO
 * @Date 16:10 2020/5/17
 * @Version 1.0
 **/
public class ConcurrentSkipListSetDemo {
    public static void main(String[] args) {
        ConcurrentSkipListSet set = new ConcurrentSkipListSet();
        set.add("a");
        set.add("b");
    }
}
