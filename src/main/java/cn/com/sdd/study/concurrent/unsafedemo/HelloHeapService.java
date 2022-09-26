package cn.com.sdd.study.concurrent.unsafedemo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.ehcache.Cache;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author suidd
 * @name HelloHeapServiceImpl
 * @description Ehcach测试堆外内存demo
 * @date 2020/5/20 16:53
 * Version 1.0
 **/
public class HelloHeapService {
    //堆内缓存map
    private static Map<String, InHeapClass> inHeapCache = new HashMap<>();
    //堆外内存缓存
    private static Cache<String, OffHeapClass> offHeapCache;

    static {
        //用Ehcache新建了一个堆外缓存，缓存大小为1MB
        ResourcePools resourcePools = ResourcePoolsBuilder.newResourcePoolsBuilder()
                .offheap(1, MemoryUnit.MB)
                .build();
        CacheConfiguration<String, OffHeapClass> configuration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, OffHeapClass.class, resourcePools)
                .build();
        offHeapCache = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("cacher", configuration)
                .build(true)
                .getCache("cacher", String.class, OffHeapClass.class);

        //在两种缓存中，都放入10000个对象
        for (int i = 1; i < 10001; i++) {
            inHeapCache.put("InHeapKey" + i, new InHeapClass("InHeapKey" + i, "InHeapValue" + i));
            offHeapCache.put("OffHeapKey" + i, new OffHeapClass("OffHeapKey" + i, "OffHeapValue" + i));
        }
    }

    @Data
    @AllArgsConstructor
    private static class InHeapClass implements Serializable {
        private String key;
        private String value;
    }

    @Data
    @AllArgsConstructor
    private static class OffHeapClass implements Serializable {
        private String key;
        private String value;
    }

    public void helloHeap() {
        System.out.println(JSON.toJSONString(inHeapCache.get("InHeapKey1")));
        System.out.println(JSON.toJSONString(offHeapCache.get("OffHeapKey1")));
        Iterator iterator = offHeapCache.iterator();
        int sum = 0;
        while (iterator.hasNext()) {
            System.out.println(JSON.toJSONString(iterator.next()));
            sum++;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        //helloHeap方法做get测试，并统计堆外内存数量，验证先插入的对象是否被淘汰
        HelloHeapService service = new HelloHeapService();
        service.helloHeap();
    }
}