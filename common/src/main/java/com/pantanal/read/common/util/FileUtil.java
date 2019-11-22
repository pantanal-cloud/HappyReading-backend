package com.pantanal.read.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;

/**
 *
 */
@Slf4j
public class FileUtil {

    /**
     * 获取后缀
     *
     * @param filepath filepath 文件全路径
     */
    public static String getSuffix(String filepath) {
        if (StringUtils.isBlank(filepath)) {
            return "";
        }
        int index = filepath.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return filepath.substring(index + 1, filepath.length());
    }

    /**
     * @param dir
     * @param url
     * @return
     */
    public static String downloadAttachment(String url, String dir) {
        return downloadAttachment(url, dir, null);
    }

    public static HttpClientUtil.HttpResult downloadAttachment4Result(String url, String dir) {
        return downloadAttachment4Result(url, dir, null);
    }

    /**
     * @param url
     * @param dir
     * @return
     */
    public static String downloadAttachment(String url, String dir, String proxyIpPort) {
        HttpClientUtil.HttpResult result = downloadAttachment4Result(url, dir, proxyIpPort);
        if (result != null) {
            return result.getResult();
        } else {
            return "";
        }
    }

    /**
     * @param url
     * @param dir
     * @return
     */
    public static HttpClientUtil.HttpResult downloadAttachment4Result(String url, String dir, String proxyIpPort) {
        try {
            Date date = new Date();
            String fileDir = dir + "/" + DateFormatUtils.format(date, "yyyy/MM/dd/HH/mm");
            FileUtils.forceMkdir(new File(fileDir));
            String fileName = DateFormatUtils.format(date, "yyyyMMddHHmmssSSS") + "-" + RandomStringUtils.randomAlphanumeric(10);
            return downloadAttachment4Result(url, fileDir, fileName, true, proxyIpPort);
        } catch (Exception ex) {
            log.error("下载url:{}失败", url, ex);
            return null;
        }
    }

    /**
     * @param url
     * @param fileDir
     * @param fileName
     * @param formatType
     * @param proxyIpPort
     * @return
     */
    public static String downloadAttachment(String url, String fileDir, String fileName, boolean formatType, String proxyIpPort) {
        HttpClientUtil.HttpResult result = downloadAttachment4Result(url, fileDir, fileName, formatType, proxyIpPort);
        if (result != null) {
            return result.getResult();
        } else {
            return "";
        }
    }

    /**
     * @param url
     * @param fileDir
     * @param fileName
     * @param formatType
     * @param proxyIpPort
     * @return
     */
    public static HttpClientUtil.HttpResult downloadAttachment4Result(String url, String fileDir, String fileName, boolean formatType, String proxyIpPort) {
        HttpGet httpGet = new HttpGet(url);
        //proxy
        if (StringUtils.isBlank(proxyIpPort) || StringUtils.indexOf(proxyIpPort, ":") < 0) {
            RequestConfig reqConfig = RequestConfig.custom().setCircularRedirectsAllowed(true) // 允许多次重定向
                    .build();
            httpGet.setConfig(reqConfig);
        } else {
            String[] proxyAndPort = StringUtils.split(proxyIpPort, ":");
            RequestConfig reqConfig = RequestConfig.custom().setProxy(new HttpHost(proxyAndPort[0], NumberUtils.toInt(proxyAndPort[1])))
                    .setCircularRedirectsAllowed(true) // 允许多次重定向
                    .build();
            httpGet.setConfig(reqConfig);
        }

        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse response = httpclient.execute(httpGet);
             InputStream inputStream = response.getEntity().getContent()) {

            FileUtils.forceMkdir(new File(fileDir));
            File destination = new File(fileDir + "/" + fileName);
            FileUtils.copyInputStreamToFile(inputStream, destination);
            String filePath = destination.getPath();


            //重命名为:带类型后缀
            if (formatType && StringUtils.isNotBlank(filePath)) {
                File file = new File(filePath);
                File newFile = null;
                try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(file))) {
                    String type = FileUtil.getFileType(is);
                    newFile = new File(filePath + "." + type);
                } catch (Exception e) {
                    log.error("获取文件：{}的类型失败", filePath, e);
                }
                if (newFile != null && file.renameTo(newFile)) {
                    filePath = newFile.getPath();
                }
            }
            filePath = StringUtils.replace(filePath, "\\", "/");
            filePath = filePath.replaceAll("^[a-zA-Z]:", "");

            HttpClientUtil.HttpResult result = new HttpClientUtil.HttpResult();
            result.setResult(filePath);
            Header[] headers = response.getAllHeaders();
            if (headers != null) {
                for (Header header : headers) {
                    result.getResponseHeader().put(header.getName(), header.getValue());
                }
            }
            return result;
        } catch (Exception ex) {
            log.error("下载url:{}失败", url, ex);
            return null;
        }
    }

    /**
     * @param inputStream
     * @return
     */
    public static String getFileType(InputStream inputStream) {
        try {
            org.apache.tika.mime.MediaType mimetype = new TikaConfig().getDetector().detect(
                    inputStream, new Metadata());
            return mimetype.getSubtype();
        } catch (Exception e) {
            log.error("获取类型失败", e);
            return "";
        }
    }

    public static void main(String[] args) {
        downloadAttachment("http://img3m9.ddimg.cn/40/15/27933979-1_w.jpg", "/gudong", "notfund.jpg", false, null);

        if (1 == 0) {
            String url = "https://www.amazon.cn/gp/product/B0030DFF9K/ref=cn_ags_s9_asin?pf_rd_p=33e63d50-addd-4d44-a917-c9479c457e1a&pf_rd_s=merchandised-search-3&pf_rd_t=101&pf_rd_i=1403206071&pf_rd_m=A1AJ19PSB66TGU&pf_rd_r=78BGN8Y8QEECNN49EMWS&pf_rd_r=78BGN8Y8QEECNN49EMWS&pf_rd_p=33e63d50-addd-4d44-a917-c9479c457e1a&ref=cn_ags_s9_asin_1403206071_merchandised-search-3";
            HttpClientUtil.HttpResult result = HttpClientUtil.doGet(url, "58.218.200.226:6412");
            System.out.println(result);

            downloadAttachment("https://unicover.duxiu.com/coverNew/CoverNew.dll?iid=6767656A6A6F656C6B6EA29D5C9AABAE9FAB5C673732323032373936", "/gudogn", "58.218.200.226:6412");

            String file = "F:/VM/CentOS7/CentOS7.vmdk";
            long start = System.currentTimeMillis();
            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File(file)))) {
                String type = FileUtil.getFileType(inputStream);
                log.info("type:{},cost:{}", type, (System.currentTimeMillis() - start));

                String s = URLEncoder.encode("https://book.douban.com/tag/%E5%B0%8F%E8%AF%B4", "utf-8");
                System.out.println(s);
                s = URLEncoder.encode("https://book.douban.com/tag/小说", "utf-8");
                System.out.println(s);
                //String filePath = downloadAttachment(null, "https://img1.doubanio.com/view/ark_article_cover/retina/public/20531237.jpg?v=0", "/downloadDir");
                //log.info(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
