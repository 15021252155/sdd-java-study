package cn.com.sdd.study.fanxing.xiajie;

import cn.com.sdd.study.fanxing.Animal;
import cn.com.sdd.study.fanxing.Dog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suidd
 * @name XiajieTest
 * @description 下界: 用 super 进行声明，表示参数化的类型可能是所指定的类型，或者是此类型的父类型，直至 Object
 * @date 2020/6/3 11:08
 * Version 1.0
 **/
public class XiajieTest {
    private <T> void test(List<? super T> dst, List<T> src) {
        for (T t : src) {
            dst.add(t);
        }
    }

    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        List<Animal> animals = new ArrayList<>();
        new XiajieTest().test(animals, dogs);
    }
}