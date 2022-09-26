package cn.com.sdd.http;

import cn.com.sdd.jiguang.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author suidd
 * @name HttpClientUtil
 * @description TODO
 * @date 2021/9/1 12:50
 * Version 1.0
 **/

public class HttpClientUtil {
    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPostJiguang(String url, String params, String appKey, String masterSecret) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String tmp = appKey.concat(":").concat(masterSecret);
        String base64Key = Base64.encodeBase64String(tmp.getBytes());
        String authorization = " Basic ".concat(base64Key);
        httpPost.setHeader("Authorization", authorization);
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            } else {
                System.err.println("请求返回:" + state + "(" + url + ")");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "https://api.jpush.cn/v3/push";

        AndroidEntity androidEntity = new AndroidEntity();
        androidEntity.setAlert("sdd通知测试-android");
        androidEntity.setTitle("长春热力");

        IosEntity iosEntity = new IosEntity();
        iosEntity.setAlert("sdd通知测试-ios");

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setAndroid(androidEntity);
        notificationEntity.setIos(iosEntity);

        AudienceEntity audienceEntity = new AudienceEntity();
        /*audienceEntity.setAlias(new String[]{"1497979988790611975"});*/
        audienceEntity.setAlias(new String[]{"1"});

        JiguangPushEntity jiguangPushEntity = new JiguangPushEntity();
        jiguangPushEntity.setPlatform(new String[]{"android", "ios"});
        jiguangPushEntity.setAudience(audienceEntity);
        jiguangPushEntity.setNotification(notificationEntity);


        String data = JSONObject.toJSONString(jiguangPushEntity);
        String appKey = "ab1afc457e12a6197f138fae";
        String masterSecret = "eb67fdf41c89000c6c700851";
        try {
            String result = HttpClientUtil.doPostJiguang(url, data, appKey, masterSecret);
            System.out.println("result=" + result);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
