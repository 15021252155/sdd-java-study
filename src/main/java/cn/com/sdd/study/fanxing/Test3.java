package cn.com.sdd.study.fanxing;

/**
 * @author suidd
 * @name Test3
 * @description Class<T> 和 Class<?> 区别
 * @date 2020/6/3 11:29
 * Version 1.0
 **/
public class Test3 {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        try {
            // 通过反射的方式生成  multiLimit
            // 对象，这里比较明显的是，我们需要使用强制类型转换
            MultiLimit multiLimit = (MultiLimit) Class.forName("cn.com.sdd.study.fanxing").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        A a = createInstance(A.class);
        B b = createInstance(B.class);
    }

    public static <T> T createInstance(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        return (T) clazz.newInstance();
    }


}

class A {
}

class B {
}