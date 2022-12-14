package cn.com.sdd.study.list;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author suidd
 * @name LRUTest
 * @description 基于LinkedHashMap，LRU缓存淘汰策略测试
 * @date 2020/5/9 16:54
 * Version 1.0
 **/
public class LRUTest {
    public static void main(String[] args) {
        // 创建一个只有5个元素的缓存
        LRU<Integer, Integer> lru = new LRU<>(5, 0.75f);
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        lru.put(4, 4);
        lru.put(5, 5);
        lru.put(6, 6);
        lru.put(7, 7);

        System.out.println(lru.get(4));

        lru.put(6, 666);

        // 输出: {3=3, 5=5, 7=7, 4=4, 6=666}
        // 可以看到最旧的元素被删除了，且最近访问的4被移到了后面
        // 如果LRU构造accessOrder设置为false，则输出{3=3, 4=4, 5=5, 6=666, 7=7}
        System.out.println(lru);
    }
}

class LRU<K, V> extends LinkedHashMap<K, V> {

    // 保存缓存的容量
    private int capacity;

    public LRU(int capacity, float loadFactor) {
        //accessOrder：true:按访问顺序排序（LRU），false:按插入顺序排序；
        super(capacity, loadFactor, true);
        this.capacity = capacity;
    }

    /**
     * 重写removeEldestEntry()方法设置何时移除旧元素
     *
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当元素个数大于了缓存的容量, 就移除元素
        return size() > this.capacity;
    }
}
