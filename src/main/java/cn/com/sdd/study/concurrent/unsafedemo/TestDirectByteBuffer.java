package cn.com.sdd.study.concurrent.unsafedemo;

import java.nio.ByteBuffer;

/**
 * @author suidd
 * @name TestDirectByteBuffer
 * @description 用NIO包下的ByteBuffer分配直接内存
 * @date 2020/5/20 16:44
 * Version 1.0
 **/
public class TestDirectByteBuffer {

    public static void main(String[] args) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
        System.out.println(buffer);
    }
}
