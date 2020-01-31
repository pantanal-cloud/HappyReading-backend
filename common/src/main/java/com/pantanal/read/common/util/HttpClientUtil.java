package com.pantanal.read.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gudong
 */
@Slf4j
public class HttpClientUtil {

    private static ThreadLocal<String> proxyIpPortLocal = new ThreadLocal<>();
    private static ThreadLocal<String> userAgentLocal = new ThreadLocal<>();

    /**
     * @param proxyIpPort ip:port
     */
    public static void setProxyIpPort(String proxyIpPort) {
        if (StringUtils.isBlank(proxyIpPort)) {
            proxyIpPortLocal.remove();
        } else {
            proxyIpPortLocal.set(proxyIpPort);
        }
    }

    /**
     * @return
     */
    public static String getProxyIpPort() {
        return proxyIpPortLocal.get();
    }

    /**
     * @param userAgent
     */
    public static void setUserAgent(String userAgent) {
        if (StringUtils.isBlank(userAgent)) {
            userAgentLocal.remove();
        } else {
            userAgentLocal.set(userAgent);
        }
    }

    /**
     * @return
     */
    public static String getUserAgent() {
        return StringUtils.defaultIfBlank(userAgentLocal.get(), userAgent[RandomUtils.nextInt(0, userAgent.length)]);
    }

    private static String[] userAgent = new String[]{
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3314.0 Safari/537.36 SE 2.X MetaSr 1.0",
        "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36",
        "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0",
        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3732.400 QQBrowser/10.5.3819.400",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:69.0) Gecko/20100101 Firefox/69.0",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/18.17763",
        "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
        "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko",
        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3314.0 Safari/537.36 SE 2.X MetaSr 1.0",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.25 Safari/537.36 Core/1.70.3704.400 QQBrowser/10.4.3587.400",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36 LBBROWSER",
        "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    };


    /**
     * 创建CloseableHttpClient 支持https
     *
     * @param url
     * @return
     */
    public static CloseableHttpClient createDefault(String url) throws Exception {
        return createDefault(url, null);
    }

    /**
     * 创建CloseableHttpClient 支持https
     *
     * @param url
     * @return
     */
    public static CloseableHttpClient createDefault(String url, CookieStore cookieStore) throws Exception {
        if (StringUtils.startsWithIgnoreCase(url, "https")) {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            if (cookieStore != null) {
                return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(cookieStore).build();
            } else {
                return HttpClients.custom().setSSLSocketFactory(sslsf).build();
            }
        } else {
            if (cookieStore != null) {
                return HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            } else {
                return HttpClients.createDefault();
            }
        }
    }


    @Data
    public static class HttpResult {
        public static final int CODE_SOCKET_ERROR = -1;
        public static final int CODE_NO_RESPONSE_ERROR = -2;
        public static final int CODE_TIMEOUT_ERROR = -3;

        /**
         * -1:java.net.SocketException
         * -2:org.apache.http.NoHttpResponseException
         */
        private int code;
        private String result;
        private List<Cookie> cookieList;
        private String cookie;
        private Map<String, String> responseHeader = new HashMap<>();
        /**
         * result字节大小
         */
        private long resultSize;
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
        httpPost.setHeader("User-Agent", getUserAgent());
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
        httpGet.setHeader("User-Agent", getUserAgent());
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
     * @param proxyIpPortStr
     * @param httpRequest
     */
    private static void setProxy(String proxyIpPortStr, HttpRequestBase httpRequest) {
        String proxyIpPort = StringUtils.defaultIfBlank(proxyIpPortStr, proxyIpPortLocal.get());
        if (StringUtils.isBlank(proxyIpPort) || StringUtils.indexOf(proxyIpPort, ":") < 0) {
            RequestConfig reqConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(10000) // 设置连接超时时间
                .setSocketTimeout(5 * 60 * 1000) // 设置读取超时时间
                .setConnectTimeout(5 * 60 * 1000)
                .setConnectionRequestTimeout(5 * 60 * 1000)
                .setCircularRedirectsAllowed(true) // 允许多次重定向
                .build();
            httpRequest.setConfig(reqConfig);
        } else {
            String[] proxyAndPort = StringUtils.split(proxyIpPort, ":");
            RequestConfig reqConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(10000) // 设置连接超时时间
                .setSocketTimeout(5 * 60 * 1000) // 设置读取超时时间
                .setConnectTimeout(5 * 60 * 1000)
                .setConnectionRequestTimeout(5 * 60 * 1000)
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
        try (CloseableHttpClient httpclient = createDefault(url, cookieStore);
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
        } catch (java.net.SocketTimeoutException e) {
            log.error("==getHttpResult error, 代理{}, method: {}, url: {}", httpRequest.getConfig().getProxy() != null ? httpRequest.getConfig().getProxy().toHostString() : "", httpRequest.getMethod(), url, e);
            HttpResult result = new HttpResult();
            result.setCode(HttpResult.CODE_TIMEOUT_ERROR);
            return result;
        } catch (org.apache.http.NoHttpResponseException e) {
            log.error("==getHttpResult error, 代理{}, method: {}, url: {}", httpRequest.getConfig().getProxy() != null ? httpRequest.getConfig().getProxy().toHostString() : "", httpRequest.getMethod(), url, e);
            HttpResult result = new HttpResult();
            result.setCode(HttpResult.CODE_NO_RESPONSE_ERROR);
            return result;
        } catch (java.net.SocketException e) {
            log.error("==getHttpResult error, 代理{}, method: {}, url: {}", httpRequest.getConfig().getProxy() != null ? httpRequest.getConfig().getProxy().toHostString() : "", httpRequest.getMethod(), url, e);
            HttpResult result = new HttpResult();
            result.setCode(HttpResult.CODE_SOCKET_ERROR);
            return result;
        } catch (Exception e) {
            log.error("==getHttpResult error, 代理{}, method: {}, url: {}", httpRequest.getConfig().getProxy() != null ? httpRequest.getConfig().getProxy().toHostString() : "", httpRequest.getMethod(), url, e);
            return null;
        }
    }

    public static void main(String[] args) {
        String cookie = "";
        Map<String, String> header = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            header.put("Cookie", cookie);
            HttpResult httpResult = doGet("http://127.0.0.1:8079/protector/admin/index.do", header);
            cookie = httpResult.getCookie();
            System.out.println(i + " cookie: " + cookie);
        }
    }
}
