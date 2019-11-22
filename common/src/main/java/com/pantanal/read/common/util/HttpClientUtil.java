package com.pantanal.read.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gudong
 */
@Slf4j
public class HttpClientUtil {

    @Data
    public static class HttpResult {
        private int code;
        private String result;
        private List<Cookie> cookieList;
        private String cookie;
        private Map<String,String> responseHeader = new HashMap<>();
    }

    public static HttpResult doPost(String url, Map<String, String> params) {
        return doPost(url, null, params, null);
    }

    /**
     * @param url
     * @param proxyIpPort ip:port
     * @return
     */
    public static HttpResult doPost(String url, String proxyIpPort, Map<String, String> params, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        //header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        //param
        if (params != null && params.size() > 0) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("==doPost UrlEncodedFormEntity error for params:{}", params, e);
            }
        }
        //proxy
        setProxy(proxyIpPort, httpPost);
        //result
        HttpResult result = getHttpResult(url, httpPost);
        return result;

    }


    public static HttpResult doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * @param url
     * @param proxyIpPort
     * @return
     */
    public static HttpResult doGet(String url, String proxyIpPort) {
        return doGet(url, proxyIpPort, null);
    }

    /**
     * @param url
     * @param headers
     * @return
     */
    public static HttpResult doGet(String url, Map<String, String> headers) {
        return doGet(url, null, headers);
    }

    /**
     * @param url
     * @return
     */
    public static HttpResult doGet(String url, String proxyIpPort, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(url);
        //header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        //proxy
        setProxy(proxyIpPort, httpGet);
        //result
        HttpResult result = getHttpResult(url, httpGet);
        return result;
    }


    /**
     * @param proxyIpPort
     * @param httpRequest
     */
    private static void setProxy(String proxyIpPort, HttpRequestBase httpRequest) {
        if (StringUtils.isBlank(proxyIpPort) || StringUtils.indexOf(proxyIpPort, ":") < 0) {
            RequestConfig reqConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(10000) // 设置连接超时时间
                    .setSocketTimeout(10000) // 设置读取超时时间
                    .setCircularRedirectsAllowed(true) // 允许多次重定向
                    .build();
            httpRequest.setConfig(reqConfig);
        } else {
            String[] proxyAndPort = StringUtils.split(proxyIpPort, ":");
            RequestConfig reqConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(10000) // 设置连接超时时间
                    .setSocketTimeout(10000) // 设置读取超时时间
                    .setProxy(new HttpHost(proxyAndPort[0], NumberUtils.toInt(proxyAndPort[1])))
                    .setCircularRedirectsAllowed(true) // 允许多次重定向
                    .build();
            httpRequest.setConfig(reqConfig);
        }
    }

    /**
     * @param url
     * @param httpRequest
     * @return
     */
    private static HttpResult getHttpResult(String url, HttpRequestBase httpRequest) {
        CookieStore cookieStore = new BasicCookieStore();
        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
             CloseableHttpResponse response = httpclient.execute(httpRequest)) {
            List<Cookie> cookies = cookieStore.getCookies();

            HttpEntity entity = response.getEntity();

            HttpResult result = new HttpResult();
            result.setCode(response.getStatusLine().getStatusCode());
            if (entity != null) {
                result.setResult(EntityUtils.toString(entity, "utf-8"));
            }

            result.setCookieList(cookies);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < cookies.size(); i++) {
                sb.append(cookies.get(i).getName() + "=" + cookies.get(i).getValue());
                if (i < cookies.size() - 1) {
                    sb.append(";");
                }
            }
            result.setCookie(sb.toString());
            return result;
        } catch (Exception e) {
            String proxy = "";
            if (httpRequest.getConfig().getProxy() != null) {
                proxy = httpRequest.getConfig().getProxy().toHostString();
            }
            log.error("==proxy: {} getHttpResult error, method: {}, url: {}", proxy, httpRequest.getMethod(), url, e);
        }
        return null;
    }
}
