package cn.com.sdd.study.thread.tongge.thread.createthread;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName CreatingThread08Test
 * @Author suidd
 * @Description Spring异步方法
 * 首先，springboot启动类加上@EnableAsync注解（@EnableAsync是spring支持的，这里方便举例使用springboot）。
 * 其次，方法加上@Async注解。
 * @Date 21:53 2020/5/28
 * @Version 1.0
 **/
public class CreatingThread08Test {
    @Autowired
    private static CreatingThread08Service creatingThread08Service;

    public static void main(String[] args) {
        // creatingThread08Service.call();
        //  CreatingThread08Service creatingThread08Service = new CreatingThread08Service();
        creatingThread08Service.call();
        creatingThread08Service.call();
        creatingThread08Service.call();
        creatingThread08Service.call();
    }


}
