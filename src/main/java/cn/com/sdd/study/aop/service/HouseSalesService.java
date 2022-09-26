package cn.com.sdd.study.aop.service;

/**
 * @author suidd
 * @name HouseSalesService
 * @description 销售房子接口实现类
 * @date 2021/4/23 15:22
 * Version 1.0
 **/
public class HouseSalesService implements SalesService {
    @Override
    public void sell() {
        System.out.println("租房...卖房...");
    }
}