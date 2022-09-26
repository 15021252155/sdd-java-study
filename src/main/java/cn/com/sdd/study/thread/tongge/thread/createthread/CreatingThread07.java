package cn.com.sdd.study.thread.tongge.thread.createthread;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName CreatingThread07
 * @Author suidd
 * @Description 并行计算（Java8+）
 * @Date 21:50 2020/5/28
 * @Version 1.0
 **/
public class CreatingThread07 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // 串行，打印结果为12345
        list.stream().forEach(System.out::print);
        System.out.println();
        // 并行，打印结果随机，比如35214
        list.parallelStream().forEach(System.out::print);
        System.out.println();
        System.out.println("sdd");
    }
}
