package cn.com.sdd.study.aop.proxy;

import cn.com.sdd.study.aop.service.HouseSalesService;
import cn.com.sdd.study.aop.service.SalesService;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author suidd
 * @name HouseSalesDynamicProxy
 * @description 销售房子动态代理类（JDK 动态代理）
 *
 * JDK动态代理的优点：
 * 1、由于动态代理是程序运行后才生成的，哪个委托类需要被代理到，只要生成动态代理即可，避免了静态代理那样的硬编码。
 * 2、另外所有委托类实现接口的方法都会在Proxy的InvocationHandler.invoke()中执行，这样如果要统计所有方法执行时间这样相同的逻辑，可以统一在InvocationHandler里写，也就避免了静态代理那样需要在所有的方法中插入同样代码的问题，代码的可维护性极大的提高了。
 *
 * JDK动态代理的缺点：
 * 1、 Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)方法，第二个参数Interfaces是委托类的接口，是必传的。JDK动态代理是通过与委托类实现同样的接口，然后在实现的接口方法里进行增强来实现的，这就意味着如果要用JDK代理，委托类必须实现接口
 *
 * @date 2021/4/23 15:35
 * Version 1.0
 **/
public class HouseSalesDynamicProxy {
    private Object target;

    public HouseSalesDynamicProxy(Object target) {
        this.target = target;
    }

    // 为目标对象生成代理对象
    public Object getProxyInstance() {
        // lambda 写法
        // JDK 动态代理的拦截对象是通过反射的机制来调用被拦截方法的
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (Object proxy, Method method, Object[] args) -> {
            //代理逻辑
            System.out.println("卖房前==>已取得销售许可");
            System.out.println("卖房前==>无不良记录");

            //目标逻辑 卖房子
            method.invoke(target, args);

            //代理逻辑
            System.out.println("卖房后==>与买方和卖方费用已结清");
            System.out.println("卖房后==>已获得买方和卖方评价");
            return null;
        });

         /* return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //代理逻辑
                System.out.println("卖房前==>已取得销售许可");
                System.out.println("卖房前==>无不良记录");

                //目标逻辑 卖房子
                method.invoke(target, args);

                //代理逻辑
                System.out.println("卖房后==>与买方和卖方费用已结清");
                System.out.println("卖房后==>已获得买方和卖方评价");
                return null;
            }
        });*/

    }

    public static void main(String[] args) {
        HouseSalesService houseSalesService = new HouseSalesService();
        System.out.println(houseSalesService.getClass());
        SalesService salesService = (SalesService) new HouseSalesDynamicProxy(houseSalesService).getProxyInstance();
        System.out.println(salesService.getClass());
        salesService.sell();

    }
}
