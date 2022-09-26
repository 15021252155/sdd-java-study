package cn.com.sdd.study.thread.concurrent;

/**
 * @ClassName MySharedObject
 * @Author suidd
 * @Description TODO
 * @Date 16:58 2020/5/4
 * @Version 1.0
 **/
public class MySharedObject {

    //static variable pointing to instance of MySharedObject
    public static final MySharedObject sharedInstance = new MySharedObject();


    //member variables pointing to two objects on the heap
    public Integer object2 = new Integer(22);
    public Integer object4 = new Integer(44);

    public long member1 = 12345;
    public long member2 = 67890;
}