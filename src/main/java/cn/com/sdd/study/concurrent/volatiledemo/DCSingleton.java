package cn.com.sdd.study.concurrent.volatiledemo;

import java.util.stream.IntStream;

/**
 * @author suidd
 * @name DCSingleton
 * @description 双检查的单例
 * @date 2021/8/27 12:41
 * Version 1.0
 **/
public class DCSingleton {
    private volatile static DCSingleton dcSingleton;

    private DCSingleton() {

    }

    public static DCSingleton getInstace() {
        if (dcSingleton == null) {
            synchronized (DCSingleton.class) {
                if (dcSingleton == null) {
                    dcSingleton = new DCSingleton();
                }
            }
        }
        return dcSingleton;
    }

    public static void main(String[] args) {

        IntStream.range(0, 20).forEach(F ->
                new Thread(() -> {
                    DCSingleton initDCS = DCSingleton.getInstace();
                    System.out.println("initDCS=" + initDCS);
                }).start()
        );
    }
}
