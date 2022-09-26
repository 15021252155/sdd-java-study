package cn.com.sdd.study.aop.cglib;

import cn.com.sdd.study.aop.service.HouseSalesService;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author suidd
 * @name CGlibProxy
 * @description CGlib实现（ CGlib 动态代理）
 *
 * CGlib动态代理的优点：
 * 1、直接继承自委托类，这样委托类的逻辑不需要做任何改动，不要求委托类实现任何接口
 * 2、委托类的所有非final方法就能被方法拦截器拦截，从而在拦截器里实现增强
 * 3、CGLIB是高效的代码生成包，底层依靠ASM（开源的java字节码编辑类库）操作字节码实现的，性能比JDK强
 *
 * 使用限制：
 * 1、只能代理委托类中任意的非final的方法，委托类不是final的
 * @date 2021/4/23 15:58
 * Version 1.0
 **/
public class CGlibProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //代理逻辑
        System.out.println("卖房前==>已取得销售许可");
        System.out.println("卖房前==>无不良记录");

        //目标逻辑 卖房子
        //注意这里的方法调用，不是用反射，采用了FastClass的机制来实现对被拦截方法的调用
        //FastClass 机制就是对一个类的方法建立索引，通过索引来直接调用相应的方法，建议参考下https://www.cnblogs.com/cruze/p/3865180.html
        Object object = methodProxy.invokeSuper(o, objects);

        //代理逻辑
        System.out.println("卖房后==>与买方和卖方费用已结清");
        System.out.println("卖房后==>已获得买方和卖方评价");

        return object;
    }

    public static void main(String[] args) {
        // 创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
        Enhancer enhancer = new Enhancer();
        // 设置目标类
        enhancer.setSuperclass(HouseSalesService.class);
        // 设置拦截对象
        enhancer.setCallback(new CGlibProxy());

        //这里的creat方法就是正式创建代理类
        HouseSalesService houseSalesService = (HouseSalesService) enhancer.create();
        //调用代理类的sell()方法
        houseSalesService.sell();
    }
}
