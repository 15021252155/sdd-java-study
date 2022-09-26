package cn.com.huak.databasic.receive.common;


import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @param
 * @author suidd
 * @description //多源配置文件属性读取工具类
 * @date 2020/5/28 15:44
 * @return change notes
 **/
public class EnvTools {
    private static Properties prop = new Properties();

    static {
        //判断是否多源配置
        Properties tempProp = new Properties();
        InputStream inputStream = EnvTools.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            tempProp.load(inputStream);
            String env = tempProp.getProperty("spring.profiles.active");
            if (StringUtils.isEmpty(env)) {
                //非多源配置
                prop = tempProp;
            } else {
                //多源配置，加载对应的配置文件
                String envPropertiesName = "application-".concat(env).concat(".properties");
                InputStream envStream = EnvTools.class.getClassLoader().getResourceAsStream(envPropertiesName);
                prop.load(envStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getPropertyForInt(String key) {
        return Integer.parseInt(prop.getProperty(key));
    }

    public static String getPropertyForString(String key) {
        return prop.getProperty(key);
    }
}