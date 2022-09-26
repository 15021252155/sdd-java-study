package cn.com.sdd.study.fanxing;

/**
 * @author suidd
 * @name Test2
 * @description 区别2：类型参数可以多重限定而通配符不行
 * @date 2020/6/3 11:23
 * Version 1.0
 **/
public class MultiLimit implements MultiLimitInterfaceA, MultiLimitInterfaceB {

    /**
     * 使用 & 符号设定多重边界（Multi Bounds)，指定泛型类型 T 必须是 MultiLimitInterfaceA 和 MultiLimitInterfaceB 的共有子类型，
     * 此时变量 t 就具有了所有限定的方法和属性。对于通配符来说，因为它不是一个确定的类型，所以不能进行多重限定。
     * @param t
     * @param <T>
     */
    public static <T extends MultiLimitInterfaceA & MultiLimitInterfaceB> void test(T t) {

    }

    public static void main(String[] args) {

    }
}