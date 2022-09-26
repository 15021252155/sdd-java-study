package cn.com.sdd.study.fanxing.wujie;

import cn.com.sdd.study.fanxing.Animal;
import cn.com.sdd.study.fanxing.Dog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suidd
 * @name WujieTest
 * @description 无界通配符测试
 * 对于不确定或者不关心实际要操作的类型，可以使用无限制通配符（尖括号里一个问号，即 <?> ），表示可以持有任何类型。
 * 像getLegs()方法中，限定了上界，但是不关心具体类型是什么，所以对于传入的 Animal 的所有子类都可以支持，并且不会报错。
 * 而 countLegs1 就不行。
 * Version 1.0
 **/
public class WujieTest {
    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        // 不会报错
        countLegs(dogs);
        // 报错
        //countLegs1(dogs);
    }

    static int countLegs(List<? extends Animal> animals) {
        int retVal = 0;
        for (Animal animal : animals) {
            retVal += animal.getLegs();
        }
        return retVal;
    }

    static int countLegs1(List<Animal> animals) {
        int retVal = 0;
        for (Animal animal : animals) {
            retVal += animal.getLegs();
        }
        return retVal;
    }

}