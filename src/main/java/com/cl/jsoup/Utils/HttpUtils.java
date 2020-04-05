package com.cl.jsoup.Utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
public class HttpUtils {

    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        this.cm.setMaxTotal(100);
        this.cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址下载页面数据
     *
     * @param url
     * @return
     */
    public String doGet(String url) {
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();

        HttpGet httpGet = new HttpGet(url);
//        Header[] headers = new Header[15];
//        headers[0] = new BasicHeader(":authority","search.jd.com");
//        headers[1] = new BasicHeader(":path","/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.def.0.V09--12s0%2C38s0%2C97s0&wq=shouji&s=113&click=0&page=");
//        headers[2] = new BasicHeader(":scheme","https");
//        headers[3] = new BasicHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n");
//        headers[4] = new BasicHeader("accept-encoding","gzip, deflate, br");
//        headers[5] = new BasicHeader("accept-language","zh-CN,zh;q=0.9");
//        headers[6] = new BasicHeader("cache-control","max-age=0");
//        headers[7] = new BasicHeader("sec-fetch-dest","document");
//        headers[8] = new BasicHeader("sec-fetch-mode","navigate");
//        headers[9] = new BasicHeader("sec-fetch-site","none");
//        headers[10] = new BasicHeader("sec-fetch-user","?1");
//        headers[11] = new BasicHeader("upgrade-insecure-requests","1");
//        headers[12] = new BasicHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
//
//        httpGet.setHeaders(headers);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
        //httpGet.setHeader("User-Agent","PostmanRuntime/7.24.0");

        httpGet.setConfig(getConfig());

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    String content = EntityUtils.toString(entity);
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 根据地址下载图片
     *
     * @param url
     * @return
     */
    public String doGetImage(String url) {
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
        System.out.println(url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getConfig());

        CloseableHttpResponse response = null;
        OutputStream os = null;
        try {
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    String extName = url.substring(url.lastIndexOf("."));

                    String picName = UUID.randomUUID().toString() + extName;

                    String path = "D:\\pa\\"+picName;
                    os = new FileOutputStream(path);
                    response.getEntity().writeTo(os);
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                if (null != os){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    private RequestConfig getConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000) //创建连接的最长时间
                .setConnectionRequestTimeout(500) //获取连接的最长时间
                .setSocketTimeout(10000) //数据传输的最长时间
                .build();
        return requestConfig;
    }

}
