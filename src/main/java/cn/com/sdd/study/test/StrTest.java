package cn.com.sdd.study.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @author suidd
 * @name StrTest
 * @description Semaphore测试
 * @date 2020/7/16 11:38
 * Version 1.0
 **/
@Slf4j
public class StrTest {
    public static void main(String[] args) {
        // s1与s2是相等的，为字节码常亮
        String s1 = "abc";
        String s2 = "abc";

        // s3创建在堆内存中
        String s3 = new String("abc");

        // intern方法可以将对象变为运行时常量
        // intern是一个native方法
        System.out.println(s1 == s3.intern()); // true
    }

  class  HuakWorker implements Runnable{
    private int wno;
    private Semaphore semaphore;

      public HuakWorker(int wno, Semaphore semaphore) {
          this.wno = wno;
          this.semaphore = semaphore;
      }

      @Override
      public void run() {
          try {
              semaphore.acquire();

          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  }
}
