package cn.com.sdd.jiguang;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * <p> Description: http/https报文发送处理类 </p>
 * <p> Copyright: Copyright (c) 2013 </p>
 * <p> Create Date: 2014-01-23 </p>
 * <p> Company: CITIC BANK </p>
 *
 * @author CITIC
 * @version $Id: HttpUtil.java,v 1.0.0
 */
public class HttpUtil {

    public static String encoding = "utf-8";

    private static final HttpConnectionManager connectionManager;

    private static final HttpClient client;

    static {

        HttpConnectionManagerParams params = loadHttpConfFromFile();

        connectionManager = new MultiThreadedHttpConnectionManager();

        connectionManager.setParams(params);

        client = new HttpClient(connectionManager);
    }

    private static HttpConnectionManagerParams loadHttpConfFromFile() {

        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(15000);
        params.setSoTimeout(30000);
        params.setStaleCheckingEnabled(Boolean.parseBoolean("true"));
        params.setTcpNoDelay(Boolean.parseBoolean("true"));
        params.setDefaultMaxConnectionsPerHost(100);
        params.setMaxTotalConnections(1000);
        params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
        return params;
    }


    public static String sendPost(String url, Map<String, String> params) {
        return post(url, encoding, params);
    }

    public static String post(String url, String encoding, Map<String, String> paras) {
        try {
            byte[] resp = post(url, paras);
            if (null == resp)
                return null;
            return new String(resp, encoding);
        } catch (Exception e) {
            return null;
        }
    }


    public static byte[] post(String url, Map<String, String> params) throws Exception {

        Protocol myhttps = new Protocol("https", new HttpsProSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);

        PostMethod method = new PostMethod(url);
        for (String key : params.keySet()) {
            NameValuePair param = new NameValuePair();
            param.setName(key);
            param.setValue(params.get(key).trim());
            method.addParameter(param);
        }
        method.setContentChunked(false);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        try {
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            return method.getResponseBody();

        } catch (SocketTimeoutException e) {
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            method.releaseConnection();
        }
    }

    /**
     * http---post請求
     * auther:hzs
     *
     * @param @param  url
     * @param @param  json参数
     * @param @return
     * @return String
     * @Title: HttpPostWithJson
     * @Description: TODO
     * @date 2017年12月6日下午5:39:50
     */
    public static String doPost(String url, String json) {
        String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            // 第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();
            // 第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);
            // 第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);
            // 第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost, responseHandler); // 调接口获取返回值时，必须用此方法.
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 第五步：处理返回值
        return null;
    }

    public static String doPostJG(String url, String json, String appKey, String masterSecret) {
        String returnValue = "fail";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            // 第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();
            // 第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);
            // 第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            String tmp = appKey.concat(":").concat(masterSecret);
            String base64Key = Base64.encodeBase64String(tmp.getBytes());
            String authorization = " Basic ".concat(base64Key);
            httpPost.setHeader("Authorization", authorization);
            httpPost.setEntity(requestEntity);
            // 第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost, responseHandler); // 调接口获取返回值时，必须用此方法.
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 第五步：处理返回值
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
        audienceEntity.setAlias(new String[]{"4"});

        JiguangPushEntity jiguangPushEntity=new JiguangPushEntity();
        jiguangPushEntity.setPlatform(new String[]{"android", "ios"});
        jiguangPushEntity.setAudience(audienceEntity);
        jiguangPushEntity.setNotification(notificationEntity);


        String data = JSONObject.toJSONString(jiguangPushEntity);
        String appKey = "ab1afc457e12a6197f138fae";
        String masterSecret = "eb67fdf41c89000c6c700851";
        try{
            String result = HttpUtil.doPostJG(url, data, appKey, masterSecret);
            System.out.println("result=" + result);
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}
