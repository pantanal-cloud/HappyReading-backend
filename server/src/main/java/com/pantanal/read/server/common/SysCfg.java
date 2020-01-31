package com.pantanal.read.server.common;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统配置
 */
@Data
public class SysCfg {
    private static SysCfg instance = null;

    /**
     * 代理ip获取url
     */
    private String proxyIpUrl;

    //图书导入目录
    private String bookImportDir;

    private String collectorJarDir;
    private boolean syncProxyIp = true;


    private String projectDir;

    private SysCfg() {
        projectDir = System.getProperty("user.dir");
        projectDir = StringUtils.replace(projectDir,"\\","/");
        projectDir = projectDir.replaceAll("^[a-zA-Z]:","");
    }


    public static SysCfg get() {
        if (instance == null) {
            instance = new SysCfg();
        }
        return instance;
    }

    public String getCollectorJarDir() {
        return StringUtils.defaultIfBlank(collectorJarDir, projectDir);
    }
}
