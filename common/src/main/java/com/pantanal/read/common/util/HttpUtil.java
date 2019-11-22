package com.pantanal.read.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.UUID;

/**
 * @author gudong
 */
@Slf4j
public class HttpUtil {
    private static final String PREFIX = "--"; // 前缀
    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
    private static final String LINE_END = "\r\n";

    static class TrustDefaultManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    /**
     * @param url         http://XXXXXXX or https://XXXXXXXX
     * @param jsonContent
     * @return 响应结果
     */
    public static JsonNode sendJson(String url, String getOrPost, String jsonContent) {
        String s = doRequest(url, getOrPost, jsonContent);
        return JsonUtil.stringToJson(s);
    }

    /**
     * @param url         http://XXXXXXX or https://XXXXXXXX
     * @param jsonContent
     * @return 响应结果
     */
    public static JsonNode sendJsonWithPost(String url, String jsonContent) {
        return sendJson(url, "POST", jsonContent);
    }

    /**
     * @param url     http://XXXXXXX or https://XXXXXXXX
     * @param content
     * @return
     */
    public static String doRequest(String url, String getOrPost, String content) {
        HttpURLConnection connection = null;
        try {
            // 创建连接
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(getOrPost.toUpperCase());
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            // connection.setRequestProperty("Content-Type", "application/json");
            if (connection instanceof HttpsURLConnection) {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, new TrustManager[]{new TrustDefaultManager()}, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();

                ((HttpsURLConnection) connection).setSSLSocketFactory(ssf);
            }

            connection.connect();

            // POST请求
            if (StringUtils.isNotBlank(content)) {
                try (OutputStream out = connection.getOutputStream()) {
                    out.write(content.getBytes("UTF-8"));
                    out.flush();
                }
            }

            // 读取响应
            StringBuffer sb = new StringBuffer();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String lines;
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    sb.append(lines);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("=======HttpUtil.doRequest error=====,url:" + url + ",content:" + content, e);
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                log.error("=======HttpUtil.doRequest connection.disconnect error=====,url:" + url + ",content:" + content, e);
            }
        }
    }

    /**
     * @param url      http://XXXXXXX or https://XXXXXXXX
     * @param filename
     * @return
     */
    public static String doRequest(String url, String paramName, String filename, byte[] fileBytes) {
        HttpURLConnection connection = null;
        try {
            // 创建连接
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);// Post 请求不能使用缓存
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            // connection.setRequestProperty("Content-Type", "application/json");
            if (connection instanceof HttpsURLConnection) {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, new TrustManager[]{new TrustDefaultManager()}, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();

                ((HttpsURLConnection) connection).setSSLSocketFactory(ssf);
            }

            connection.connect();

            /**
             *
             * 请求体
             *
             */

            // 上传参数
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            // 文件上传

            StringBuilder fileSb = new StringBuilder();
            fileSb.append(PREFIX).append(BOUNDARY).append(LINE_END)

                    /**
                     *
                     * 这里重点注意： name里面的值为服务端需要的key 只有这个key 才可以得到对应的文件
                     *
                     * filename是文件的名字，包含后缀名的 比如:abc.png
                     *
                     */
                    .append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + filename + "\"" + LINE_END)
                    .append("Content-Type: image/jpg" + LINE_END) // 此处的ContentType不同于
                    // 请求头
                    // 中Content-Type
                    .append("Content-Transfer-Encoding: 8bit" + LINE_END).append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容
            dos.writeBytes(fileSb.toString());
            dos.flush();

            dos.write(fileBytes);
            dos.writeBytes(LINE_END);

            // 请求结束标志
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
            dos.flush();
            dos.close();

            // 读取服务器返回信息
            StringBuilder sb = new StringBuilder();
            if (connection.getResponseCode() == 200) {
                // 读取响应
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String lines;
                    while ((lines = reader.readLine()) != null) {
                        lines = new String(lines.getBytes(), "utf-8");
                        sb.append(lines);
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("=======HttpUtil.doRequest error=====,url:" + url, e);
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                log.error("=======HttpUtil.doRequest connection.disconnect error=====,url:" + url, e);
            }
        }
    }

    /**
     * @param url        http://XXXXXXX or https://XXXXXXXX
     * @param fileParams
     * @return
     */
    public static String doRequest(String url, Map<String, String> strParams, final Map<String, File> fileParams) {
        HttpURLConnection connection = null;
        try {
            // 创建连接
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);// Post 请求不能使用缓存
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            // connection.setRequestProperty("Content-Type", "application/json");
            if (connection instanceof HttpsURLConnection) {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, new TrustManager[]{new TrustDefaultManager()}, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();

                ((HttpsURLConnection) connection).setSSLSocketFactory(ssf);
            }

            connection.connect();

            /**
             *
             * 请求体
             *
             */

            // 上传参数
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            // getStrParams()为一个
            dos.writeBytes(getStrParams(strParams).toString());
            dos.flush();

            // 文件上传

            StringBuilder fileSb = new StringBuilder();
            for (Map.Entry<String, File> fileEntry : fileParams.entrySet()) {
                fileSb.append(PREFIX).append(BOUNDARY).append(LINE_END)

                        /**
                         *
                         * 这里重点注意： name里面的值为服务端需要的key 只有这个key 才可以得到对应的文件
                         *
                         * filename是文件的名字，包含后缀名的 比如:abc.png
                         *
                         */
                        .append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileEntry.getKey() + "\"" + LINE_END)
                        .append("Content-Type: image/jpg" + LINE_END) // 此处的ContentType不同于 请求头 中Content-Type
                        .append("Content-Transfer-Encoding: 8bit" + LINE_END).append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容
                dos.writeBytes(fileSb.toString());
                dos.flush();

                InputStream is = new FileInputStream(fileEntry.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    dos.write(buffer, 0, len);
                }
                is.close();
                dos.writeBytes(LINE_END);
            }

            // 请求结束标志
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
            dos.flush();
            dos.close();

            // 读取服务器返回信息
            StringBuilder sb = new StringBuilder();
            if (connection.getResponseCode() == 200) {
                // 读取响应
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String lines;
                    while ((lines = reader.readLine()) != null) {
                        lines = new String(lines.getBytes(), "utf-8");
                        sb.append(lines);
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("=======HttpUtil.doRequest error=====,url:" + url, e);
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                log.error("=======HttpUtil.doRequest connection.disconnect error=====,url:" + url, e);
            }
        }
    }

    /**
     * 对post参数进行编码处理
     */

    private static StringBuilder getStrParams(Map<String, String> strParams) {
        StringBuilder strSb = new StringBuilder();
        if (strParams != null) {
            for (Map.Entry<String, String> entry : strParams.entrySet()) {
                strSb.append(PREFIX).append(BOUNDARY).append(LINE_END).append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END)
                        .append("Content-Type: text/plain; charset=utf-8" + LINE_END).append("Content-Transfer-Encoding: 8bit" + LINE_END).append(LINE_END)// 参数头设置完以后需要两个换行，然后才是参数内容
                        .append(entry.getValue()).append(LINE_END);
            }
        }
        return strSb;
    }

    /**
     * 执行 cert.p12 安全请求
     *
     * @param url
     * @param requestContent 发送内容
     * @param certFilePath   证书路径
     * @param certPassword   证书密码
     * @return
     */
    public static String doCertP12Post(String url, String requestContent, String certFilePath, String certPassword) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try (FileInputStream instream = new FileInputStream(new File(certFilePath))) {
                keyStore.load(instream, certPassword.toCharArray());
            } catch (Exception e) {
                log.error("===load KeyStore error!", e);
                return null;
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPassword.toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
                HttpPost httpPost = new HttpPost(url);
                StringEntity stringEntity = new StringEntity(requestContent, "UTF-8");
                stringEntity.setContentEncoding("UTF-8");
                httpPost.setEntity(stringEntity);
                try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                    HttpEntity entity = response.getEntity();
                    String result = "";
                    if (entity != null) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }
                    }
                    EntityUtils.consume(entity);
                    return result;
                } catch (Exception e) {
                    log.error("===do response error!", e);
                    return null;
                }
            } catch (Exception e) {
                log.error("===build httpclient error!", e);
                return null;
            }
        } catch (Exception e) {
            log.error("===doHttpsPost url:" + url + ", content:" + requestContent, e);
            return null;
        }
    }

    /**
     * 文件下载
     *
     * @param fileUrl  下载路径
     * @param savePath 存放地址 示例：D:/ceshi/1.png
     * @throws Exception
     */
    public static boolean downloadFile(String fileUrl, String savePath) {
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            File file = new File(savePath);
            FileUtils.forceMkdir(file.getParentFile());
            //判断文件是否存在，不存在则创建文件
            if (!file.exists()) {
                file.createNewFile();
            }
            URL url = new URL(fileUrl);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setConnectTimeout(6000);
            urlCon.setReadTimeout(6000);
            int code = urlCon.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new Exception("文件读取失败");
            }

            in = new DataInputStream(urlCon.getInputStream());
            out = new DataOutputStream(new FileOutputStream(savePath));

            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            return true;
        } catch (Exception e) {
            log.error("下载文件失败,url:" + fileUrl, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e1) {
                log.error("关闭输入输出流失败,url:" + fileUrl, e1);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s = doRequest("https://msc.nz/io/rcw2000", "get", "");
        System.out.println(s);
    }
}