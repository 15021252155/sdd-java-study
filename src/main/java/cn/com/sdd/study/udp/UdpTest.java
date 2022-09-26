package cn.com.sdd.study.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author suidd
 * @name UdpTest
 * @description 模拟客户端，调用udp接口
 * @date 2020/11/2 8:52
 * Version 1.0
 **/
public class UdpTest {
    public static void main(String[] args) throws IOException {
        System.out.println("发送端启动......");

        // 1、创建UDP的Socket，使用DatagramSocket对象
        DatagramSocket ds = new DatagramSocket();

        // 2、将要发送的数据封装到数据包中
        String str = "0070000000010000007f0000201022042400dd29201022044400df29201022050400e029201022052400e029201022054400e129201022060400e229201022062500e029201022064500e029201022070500e029201022072500e129201022074500e129201022080500df29002800b0121a";

        byte[] buf = str.getBytes();  //使用DatagramPacket将数据封装到该对象的包中

        DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), 9999);

        // 3、通过UDP的Socket服务将数据包发送出去，使用send方法
        ds.send(dp);

        // 4、关闭Socket服务
        ds.close();
    }
}