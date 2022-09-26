package cn.com.sdd.study.sha;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author suidd
 * @name SHA256Test2
 * @description TODO
 * @date 2021/2/2 14:00
 * Version 1.0
 **/
public class SHA256Test2 {
    public static void main(String[] args) {
        String str1 = getSHA256Str("123456") + "2020-5-12";
        System.out.println("str1="+str1);
        String str2 = getSHA256Str(str1);
        System.out.println("str2="+str2);
    }

    /***
     *  利用Apache的工具类实现SHA-256加密
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256Str(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }
}
