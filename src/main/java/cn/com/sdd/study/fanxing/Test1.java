package cn.com.sdd.study.fanxing;

import java.util.List;

/**
 * @author suidd
 * @name Test1
 * @description 通过 T 来 确保 泛型参数的一致性
 * @date 2020/6/3 11:12
 * Version 1.0
 **/
public class Test1 {
    public static void main(String[] args) {
       /* List<T> list = new ArrayList<T>();
        List<?> list2 = new ArrayList<?>();*/

    }


    // 通过 T 来 确保 泛型参数的一致性
    public <T extends Number> void test(List<T> dst, List<T> src) {

    }

    //通配符是 不确定的，所以这个方法不能保证两个 List 具有相同的元素类型
    public void test2(List<? extends Number> dst, List<? extends Number> src) {

    }
}