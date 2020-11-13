package com.sinovatech.wechatwork.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.jni.OS;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author hukui
 * @Date 2018/09/08 上午9:47
 */
@Slf4j
public class NewHttpCilentUtil {

    private static final CloseableHttpClient httpClient;

    public static final String CHARSET = "UTF-8";

    // 采用静态代码块，初始化超时时间配置，再根据配置生成默认httpClient对象
    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(2000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, CHARSET);
    }


    public static String doPost(String url, Map<String, String> params){
        return doPost(url, params, CHARSET);
    }


    /**
     * HTTP Get 获取内容
     * @param url 请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        String result = null;
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                // 将请求参数和url进行拼接
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            long start = System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            response.close();
        } catch (Exception e) {
            log.error("GET请求异常",e);
        }
        return result;
    }

    /**
     * HTTP Post 获取内容
     * @param url 请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset 编码格式
     * @return 页面内容
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String charset){
        if (StringUtils.isBlank(url)) {
            return null;
        }
        List<NameValuePair> pairs = null;
        if (params != null && !params.isEmpty()) {
            pairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
            }

            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            return result;
        }catch (Exception e){
            log.info("http-Post调用异常，",e);
            return null;
        }finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    log.info("关闭响应异常",e);
                }
            }
        }
    }



    /**
     * 指定 JSON格式的ContentType 的post请求.
     * @param url
     * @param json
     * @param charset
     * @return
     */
    public static String doPost(String url, String json, String charset){
        if (StringUtils.isBlank(url)) {
            return null;
        }
        charset = StringUtils.isBlank(charset) ? CHARSET : charset;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            if (StringUtils.isNotBlank(json)) {
                StringEntity stringEntity = new StringEntity(json,charset);
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200 ) {
                HttpEntity entity = response.getEntity();
                String result = null;
                if (entity != null) {
                    result = EntityUtils.toString(entity, charset);
                }
                EntityUtils.consume(entity);
                return result;
            }else{
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
        }catch (Exception e){
            log.error("http-Post调用异常，",e);
            return null;
        }finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("关闭响应异常",e);
                }
            }
        }
    }


    public static String doGetWithCookies(String url, Map<String, String> params,Map<String,String> cookiesMap) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        String result = null;
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                // 将请求参数和url进行拼接
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
            }
            //添加JSESSIONID的Cookie
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> stringStringEntry : cookiesMap.entrySet()) {
                BasicClientCookie cookie = new BasicClientCookie(stringStringEntry.getKey(), stringStringEntry.getValue());
                //cookie.setVersion(0);
                cookie.setDomain("mygiftcard.jd.com");
                cookie.setPath("/");
                cookieStore.addCookie(cookie);
            }

            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .build();

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();


            HttpGet httpGet = new HttpGet(url);
            //设置Header的User-Agent
            httpGet.setHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
            httpGet.setConfig(requestConfig);


            CloseableHttpResponse response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            response.close();
        } catch (Exception e) {
            log.error("GET请求异常",e);
        }
        return result;
    }

    public static String doPostWithCookies(String url, Map<String, String> params ,Map<String,String> cookiesMap) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        String result = null;
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                // 将请求参数和url进行拼接
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
            }
            //添加JSESSIONID的Cookie
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> stringStringEntry : cookiesMap.entrySet()) {
                BasicClientCookie cookie = new BasicClientCookie(stringStringEntry.getKey(), stringStringEntry.getValue());
                //cookie.setVersion(0);
                cookie.setDomain("mygiftcard.jd.com");
                cookie.setPath("/");
                cookieStore.addCookie(cookie);
            }

            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .build();

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .build();


            HttpPost httpPost = new HttpPost(url);
            //设置Header的User-Agent
            httpPost.addHeader("Accept","*/*");
            httpPost.addHeader("Host","mygiftcard.jd.com");
            httpPost.addHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            httpPost.addHeader("Accept-Encoding","gzip, deflate, br");
            httpPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
            httpPost.addHeader("X-Requested-With","XMLHttpRequest");
            //httpPost.setHeader("Content-Length","165");
            httpPost.addHeader("Origin","https://mygiftcard.jd.com");
            httpPost.addHeader("Connection","keep-alive");
            httpPost.addHeader("Referer","https://mygiftcard.jd.com/giftcard/myGiftCardInit.action");
            httpPost.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:76.0) Gecko/20100101 Firefox/76.0");
            httpPost.addHeader("Pragma","no-cache");
            httpPost.addHeader("Cache-Control","no-cache");

            httpPost.setConfig(requestConfig);


            CloseableHttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 302) {
                httpPost.abort();
                log.info("请求重定向.");
            } else if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }else{
                log.info("请求成功.");
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET);
            }
            EntityUtils.consume(entity);
            response.close();
        } catch (Exception e) {
            log.error("POST请求异常",e);
        }
        return result;
    }





}
