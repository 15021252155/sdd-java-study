package cn.com.sdd.study.aop.proxy;

import cn.com.sdd.study.aop.service.HouseSalesService;
import cn.com.sdd.study.aop.service.SalesService;

/**
 * @author suidd
 * @name HouseSalesProxy
 * @description 销售房子接口代理类（静态代理）
 * 静态代理缺点：
 * 1、代理类只代理一个委托类（其实可以代理多个，但不符合单一职责原则），也就意味着如果要代理多个委托类，就要写多个代理（别忘了静态代理在编译前必须确定）
 * 2、第一点还不是致命的，再考虑这样一种场景：如果每个委托类的每个方法都要被织入同样的逻辑，比如说我要计算前文提到的每个委托类每个方法的耗时，就要在方法开始前，开始后分别织入计算时间的代码，那就算用代理类，它的方法也有无数这种重复的计算时间的代码
 * @date 2021/4/23 15:23
 * Version 1.0
 **/
public class HouseSalesProxy implements SalesService {
    private HouseSalesService houseSalesService;

    public HouseSalesProxy(HouseSalesService houseSalesService) {
        this.houseSalesService = houseSalesService;
    }

    @Override
    public void sell() {
        //代理逻辑
        System.out.println("卖房前==>已取得销售许可");
        System.out.println("卖房前==>无不良记录");

        //目标逻辑 卖房子
        houseSalesService.sell();

        //代理逻辑
        System.out.println("卖房后==>与买方和卖方费用已结清");
        System.out.println("卖房后==>已获得买方和卖方评价");
    }

    public static void main(String[] args) {
        // 被代理对象
        HouseSalesService houseSalesService = new HouseSalesService();

        // 代理
        HouseSalesProxy houseSalesProxy = new HouseSalesProxy(houseSalesService);

        // 代理请求
        houseSalesProxy.sell();
    }
}
