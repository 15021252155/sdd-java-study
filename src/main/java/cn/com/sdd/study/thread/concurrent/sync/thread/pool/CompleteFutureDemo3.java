package cn.com.sdd.study.thread.concurrent.sync.thread.pool;

import cn.com.sdd.common.DateUtil;
import cn.com.sdd.common.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author suidd
 * @name CompleteFutureDemo3
 * @description TODO
 * @date 2020/5/7 15:03
 * Version 1.0
 **/
public class CompleteFutureDemo3 {
    public static void main(String[] args) {
        Map<String, Object> resultMap = new HashMap<>(16);
        CompletableFuture<Result<List<String>>> test1Future = CompletableFuture.supplyAsync(() -> test1());
        CompletableFuture<Result<List<String>>> test2Future = CompletableFuture.supplyAsync(() -> test2());
        CompletableFuture<Result<List<String>>> test3Future = CompletableFuture.supplyAsync(() -> test3());
        CompletableFuture<Result<List<String>>> test4Future = CompletableFuture.supplyAsync(() -> test4());
        CompletableFuture<Result<List<String>>> test5Future = CompletableFuture.supplyAsync(() -> test5());

        test1Future.whenComplete((v, t) -> {
            if (v.getCode() != 200) {
                System.out.println("error,退出");
                return;
            }
            resultMap.put(v.toString(), v);
        });

        test2Future.whenComplete((v, t) -> {
            if (v.getCode() != 200) {
                System.out.println("error,退出");
                return;
            }
            resultMap.put(v.toString(), v);
        });
        test3Future.whenComplete((v, t) -> {
            if (v.getCode() != 200) {
                System.out.println("error,退出");
                return;
            }
            resultMap.put(v.toString(), v);
        });
        test4Future.whenComplete((v, t) -> {
            if (v.getCode() != 200) {
                System.out.println("error,退出");
                return;
            }
            resultMap.put(v.toString(), v);
        });
        test5Future.whenComplete((v, t) -> {
            if (v.getCode() != 200) {
                System.out.println("error,退出");
                return;
            }
            resultMap.put(v.toString(), v);
        });

        CompletableFuture.allOf(test1Future, test2Future, test3Future, test4Future, test5Future)
                .thenRun(() -> System.out.println("完成！！！！"))
                .join();
        System.out.println(resultMap);
    }

    public static Result<List<String>> test1() {
        System.out.println("test1开始..." + DateUtil.getNowTime());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test1结束..." + DateUtil.getNowTime());
        return Result.ok("ok", new ArrayList<String>());
    }

    public static Result<List<String>> test2() {
        System.out.println("test2开始..." + DateUtil.getNowTime());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test2结束..." + DateUtil.getNowTime());
        return Result.failed("error");
    }

    public static Result<List<String>> test3() {
        System.out.println("test3开始..." + DateUtil.getNowTime());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test3结束..." + DateUtil.getNowTime());
        return Result.ok("ok", new ArrayList<String>());
    }

    public static Result<List<String>> test4() {
        System.out.println("test4开始..." + DateUtil.getNowTime());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test4结束..." + DateUtil.getNowTime());
        return Result.ok("ok", new ArrayList<String>());
    }

    public static Result<List<String>> test5() {
        System.out.println("test5开始..." + DateUtil.getNowTime());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test5结束..." + DateUtil.getNowTime());
        return Result.ok("ok", new ArrayList<String>());
    }
}