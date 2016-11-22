package com.xu.myWechat.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * http统一方法
 * Created by 徐华 on 2016/3/14.
 */
public class HttpHandler {

    public static final String UPLOAD_DIC_PATH = "D:\\ee_upload";

    /**
     * 统一构造方法http get请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpGet(String url) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
        String detail = bufferedReader.readLine();
        httpGet.abort();
        return detail;
    }

    /**
     * 统一构造方法http post请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String httpPost(String url) throws Exception {
        HttpClient httpClient = getHttpsClient();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = httpClient.execute(httpPost);
//        HttpEntity httpEntity = httpResponse.getEntity();
//        InputStream inputStream = httpEntity.getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
        String detail = bufferedReader.readLine();
        httpPost.abort();
        return detail;
    }

    /**
     * 银行卡获取卡名称 开卡行
     *
     * @param num
     * @return
     */
    public static String getCardType(String num) {
        String content = null;
        try {
            content = get("http://120.55.65.90:8007/bankCard?cardNum=" + num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * https get方法
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String get(String url) throws Exception {
        if (url != null) {
            HttpClient httpClient;
            if (url.startsWith("https")) {
                httpClient = getHttpsClient();
            } else {
                httpClient = getHttpClient();
            }

            return get(httpClient, url);
        } else {
            return "url is null";
        }
    }

    /**
     * https post方法
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String url, Map<String, String> params) throws Exception {
        if (url == null) {
            return "url is null";
        } else {
            HttpClient httpClient;
            if (url.startsWith("https")) {
                httpClient = getHttpsClient();
            } else {
                httpClient = getHttpClient();
            }

            ArrayList paras = new ArrayList();
            Iterator entity = params.keySet().iterator();

            while (entity.hasNext()) {
                String key = (String) entity.next();
                paras.add(new BasicNameValuePair(key, (String) params.get(key)));
            }
            UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(paras, "UTF-8");
            return post(httpClient, url, entity1);
        }
    }

    public static String postWithoutEc(String url, Map<String, String> params) throws Exception {
        if (url == null) {
            return "url is null";
        } else {
            HttpClient httpClient;
            if (url.startsWith("https")) {
                httpClient = getHttpsClient();
            } else {
                httpClient = getHttpClient();
            }

            ArrayList paras = new ArrayList();
            Iterator entity = params.keySet().iterator();

            while (entity.hasNext()) {
                String key = (String) entity.next();
                paras.add(new BasicNameValuePair(key, (String) params.get(key)));
            }

            UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(paras);
            return post(httpClient, url, entity1);
        }
    }

    public static String jsonPost(String url, JSONObject jsonObject) throws Exception {
        if (url == null) {
            return "url is null";
        } else {
            HttpClient httpClient;
            if (url.startsWith("https")) {
                httpClient = getHttpsClient();
            } else {
                httpClient = getHttpClient();
            }
            HttpPost httpPost = new HttpPost(url);
//            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(25000).setConnectTimeout(25000).build();
//            httpPost.setConfig(requestConfig);
            StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            String content = "";
            try {
                HttpResponse response = httpClient.execute(httpPost);
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    content = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } finally {
                httpPost.abort();
            }
            return content;
        }
    }

    /**
     * https post方法
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String post(String url, String json) throws Exception {
        if (url != null) {
            HttpClient httpClient;
            if (url.startsWith("https")) {
                httpClient = getHttpsClient();
            } else {
                httpClient = getHttpClient();
            }

            StringEntity entity = new StringEntity(json, "UTF-8");
            return post(httpClient, url, entity);
        } else {
            return "url is null";
        }
    }

    public static String postp(String url, String json) throws Exception {
        if (url != null) {
            HttpClient httpClient;
            if (url.startsWith("https")) {
                httpClient = getHttpsClient();
            } else {
                httpClient = getHttpClient();
            }

            StringEntity entity = new StringEntity(json, "UTF-8");
            return postp(httpClient, url, entity);
        } else {
            return "url is null";
        }
    }

    /**
     * https get方法
     *
     * @param httpClient
     * @param url
     * @return
     * @throws IOException
     */
    private static String get(HttpClient httpClient, String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        HttpEntity httpEntity = httpResponse.getEntity();

        String response = EntityUtils.toString(httpEntity, "utf-8");
        EntityUtils.consume(httpEntity);
        return response;

    }

    /**
     * https post方法
     *
     * @param httpClient
     * @param url
     * @param entity
     * @return
     * @throws IOException
     */
    private static String post(HttpClient httpClient, String url, HttpEntity entity) throws IOException {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        httpPost.setHeader("content-type", "application/json");
//      RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000).build();
//      httpPost.setConfig(requestConfig);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();

        String response = EntityUtils.toString(httpEntity, "utf-8");
        EntityUtils.consume(httpEntity);
        return response;
    }

    private static String postp(HttpClient httpClient, String url, HttpEntity entity) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        httpPost.setHeader("content-type", "application/x-www-form-urlencoded");
        HttpResponse httpResponse = httpClient.execute(httpPost);

        String result = null;
        if (httpResponse != null) {
            HttpEntity resEntity = httpResponse.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, "UTF-8");
            }
        }
        return result;
    }

    /**
     * 使用 x-forwarded-for
     *
     * @param httpClient
     * @param url
     * @param entity
     * @return
     * @throws IOException
     */
    private static String post_x(HttpClient httpClient, String url, HttpEntity entity) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);
        //httpPost.setHeader("x-forwarded-for",);
        httpPost.setHeader("content-type", "application");
        HttpResponse httpResponse = httpClient.execute(httpPost);

        HttpEntity httpEntity = httpResponse.getEntity();

        String response = EntityUtils.toString(httpEntity, "utf-8");
        EntityUtils.consume(httpEntity);
        return response;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }

    private static HttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    public static HttpClient getHttpsClient() throws Exception {
        SSLContext context = (new SSLContextBuilder()).loadTrustMaterial((KeyStore) null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        }).build();
        return HttpClients.custom().setSslcontext(context).build();
    }
}
