package cn.com.sdd.study.sha;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author suidd
 * @name SHA256Util
 * @description AES加密工具类
 * AES的区块长度固定为128比特，密钥长度则可以是128，192或256比特；
 * @date 2021/8/6 13:44
 * Version 1.0
 **/
public class SHA256Util {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";
    /**
     * 编码格式
     */
    private static final String UTF8 = "UTF-8";

    /**
     * AES加密
     *
     * @param content
     * @param pkey
     * @return
     * @throws DecoderException
     */
    private static byte[] encrypt(String content, String pkey) throws DecoderException {
        try {
            String private_key = pkey;
            byte[] encodeFormat = null;
            try {
                //秘钥 Hex解码为什么秘钥要进行解码，因为秘钥是某个秘钥明文进行了Hex编码后的值，所以在使用的时候要进行解码
                encodeFormat = Hex.decodeHex(private_key.toCharArray());
            } catch (DecoderException e) {
                e.printStackTrace();
            }
            SecretKeySpec key = new SecretKeySpec(encodeFormat, ALGORITHM);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            // 加密内容进行编码
            byte[] byteContent = content.getBytes(UTF8);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 正式执行加密操作
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param contents
     * @param password
     * @return
     * @throws DecoderException
     */
    private static byte[] decrypt(String contents, String password) throws DecoderException {
        try {
            //密文使用Hex解码
            byte[] content = Hex.decodeHex(contents.toCharArray());
            //秘钥 Hex解码为什么秘钥要进行解码，因为秘钥是某个秘钥明文进行了Hex编码后的值，所以在使用的时候要进行解码
            byte[] encodeFormat = Hex.decodeHex(password.toCharArray());
            SecretKeySpec key = new SecretKeySpec(encodeFormat, ALGORITHM);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 正式执行解密操作
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Aes加密
     *
     * @param context     明文
     * @param private_key 秘钥
     * @return
     * @throws DecoderException
     */
    public static String encryption(String context, String private_key) throws DecoderException {
        //加密后的明文也就变成了密文
        byte[] encryptResult = encrypt(context, private_key);
        //密码文Hex编码
        String encryptResultStr = Hex.encodeHexString(encryptResult);
        return encryptResultStr;
    }

    /**
     * Aes解密
     *
     * @param context     密文
     * @param private_key 秘钥
     * @return
     * @throws DecoderException
     * @throws UnsupportedEncodingException
     */
    public static String decryption(String context, String private_key) throws DecoderException, UnsupportedEncodingException {
        //这里的密文解密前先进行了Hex解码
        byte[] decryptResult = decrypt(context, private_key);
        String result = new String(decryptResult, UTF8);
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, DecoderException {
        //加密内容
        String content = "{\"system_id\":\"xxxxxxxxxxxxxx\",\"mac\":\"00000001000001FF\",\"device_address\":\"\",\"sim_encryption\":0,\"msg_id\":\"82\",\"SN\":0,\"oper_type\":1,\"CmdType\":0,\"DeviceType\":2,\"type\":4,\"heating_season\":1,\"non_heating_season\":1,\"wait_time\":30,\"unit\":\"\",\"start_time\":\"\",\"end_time\":\"\"}";
        //AES加密解密秘钥

        String pk = Hex.encodeHexString("0123456789012345".getBytes());//长度=16---对应128比特
        //String pk = Hex.encodeHexString("012345678901234567890123".getBytes()); //长度=24---对应192比特，
        //String pk =Hex.encodeHexString("abcdefghijklmnop".getBytes()); //长度=32---对应256比特
        System.out.println("密钥=" + pk);
        // 加密
        System.out.println("加密前：" + content);
        // 调用加密方法
        String encryptResultStr = encryption(content, pk);
        System.out.println("加密后：" + encryptResultStr);
        // 调用解密方法
        String result = decryption(encryptResultStr, pk);
        // 解密内容进行解码
        System.out.println("解密后：" + result);
    }
}
