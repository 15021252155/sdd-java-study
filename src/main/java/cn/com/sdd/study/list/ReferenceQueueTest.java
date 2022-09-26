package cn.com.sdd.study.list;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suidd
 * @name ReferenceQueueTest
 * @description ReferenceQueue的使用
 * <p>
 * 对于软引用和弱引用，我们希望当一个对象被gc掉的时候通知用户线程，进行额外的处理时，就需要使用引用队列了。
 * ReferenceQueue即这样的一个对象，当一个obj被gc掉之后，其相应的包装类，即ref对象会被放入queue中。
 * 我们可以从queue中获取到相应的对象信息，同时进行额外的处理。比如反向操作，数据清理等。
 * @date 2021/8/27 14:56
 * Version 1.0
 **/
public class ReferenceQueueTest {
    private static ReferenceQueue<byte[]> rq = new ReferenceQueue<>();
    private static int _1M = 1024 * 1024;

    public static void main(String[] args) {
        Object value = new Object();
        Map<Object, Object> map = new HashMap<>();
        Thread thread = new Thread(() -> {
            try {
                int cnt = 0;
                WeakReference<byte[]> k;
                while ((k = (WeakReference) rq.remove()) != null) {
                    System.out.println((cnt++) + "回收了:" + k);
                }
            } catch (InterruptedException e) {
                //结束循环
            }
        });
        thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 10000; i++) {
            byte[] bytes = new byte[_1M];
            WeakReference<byte[]> weakReference = new WeakReference<>(bytes, rq);
            map.put(weakReference, value);
        }
        System.out.println("map.size->" + map.size());


        /**
         * 结果分析：
         *
         * 因为map的key是WeakReference，所以在内存不足的时候，weakReference所指向的对象就会被GC，在对象被GC的同时，
         *
         * 会把该对象的包装类即weakReference放入到ReferenceQueue里面。但是这个map的大小是10000.
         */
    }
}
