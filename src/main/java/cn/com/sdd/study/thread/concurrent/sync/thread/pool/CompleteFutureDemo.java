package cn.com.sdd.study.thread.concurrent.sync.thread.pool;

import cn.com.sdd.common.DateUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author suidd
 * @name CompleteFutureDemo
 * @description CompleteFuture测试
 * @date 2020/5/6 13:33
 * Version 1.0
 **/
public class CompleteFutureDemo {
    public static void main(String[] args) {
        System.out.println("程序开始...时间：" + DateUtil.getNowTime());
        Future<String> nameFuture = CompletableFuture.supplyAsync(() -> getUserName("1"));
        Future<List<String>> permissionFuture = CompletableFuture.supplyAsync(() -> getPermission("1"));

        String userName = ((CompletableFuture<String>) nameFuture).join();
        System.out.println("获取用户名称：" + userName + "...时间" + DateUtil.getNowTime());
        List<String> permissionList = ((CompletableFuture<List<String>>) permissionFuture).join();
        System.out.println("获取用户权限：" + permissionList + "...时间" + DateUtil.getNowTime());
        for (String p : permissionList) {
            System.out.println(userName + "：" + p);
        }
        System.out.println("程序结束...时间：" + DateUtil.getNowTime());
    }

    public static String getUserName(String no) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        if ("1".equals(no))
            return "管理员";
        else
            return "用户";
    }

    public static List<String> getPermission(String no) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        if ("1".equals(no))
            return Arrays.asList("1,2,3,4,5,6,7,8,9".split(","));
        else
            return Arrays.asList("1,2,3".split(","));
    }

}