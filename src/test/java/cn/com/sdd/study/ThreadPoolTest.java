package cn.com.sdd.study;

import cn.com.sdd.study.test.ThreadPoolExecutorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author suidd
 * @name ThreadPoolTest
 * @description TODO
 * @date 2020/5/28 15:17
 * Version 1.0
 **/
@SpringBootTest
public class ThreadPoolTest {
    @Test
    void test() {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutorFactory.getInstance();
        threadPoolExecutor.execute(new doGetId());
    }

    static class doGetId implements Runnable {
        @Override
        public void run() {
        }
    }
}