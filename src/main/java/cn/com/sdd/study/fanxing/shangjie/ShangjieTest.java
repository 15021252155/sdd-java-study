package cn.com.sdd.study.fanxing.shangjie;

/**
 * @author suidd
 * @name ShangjieTest
 * @description 上界：用 extends 关键字声明，表示参数化的类型可能是所指定的类型，或者是此类型的子类。
 * 在类型参数中使用 extends 表示这个泛型中的参数必须是 E 或者 E 的子类，这样有两个好处：
 * 如果传入的类型不是 E 或者 E 的子类，编译不成功
 * 泛型中可以使用 E 的方法，要不然还得强转成 E 才能使用
 * @date 2020/6/3 11:06
 * Version 1.0
 **/
public class ShangjieTest {
   /* private <K extends A, E extends B> E test(K arg1, E arg2){
        E result = arg2;
        arg2.compareTo(arg1);
        //.....
        return result;
    }*/
}