package com.pantanal.read.server.pay.wechat.sdk;

import com.pantanal.read.server.pay.wechat.sdk.WXPayConfig;
import java.io.*;

// TODO
public class MyWechatPayConfig extends WXPayConfig {

  private byte[] certData;

  public MyWechatPayConfig() throws Exception {
      String certPath = "/path/to/apiclient_cert.p12";
      File file = new File(certPath);
      InputStream certStream = new FileInputStream(file);
      this.certData = new byte[(int) file.length()];
      certStream.read(this.certData);
      certStream.close();
  }

  public String getAppID() {
      return "wx8888888888888888";
  }

  public String getMchID() {
      return "12888888";
  }

  public String getKey() {
      return "88888888888888888888888888888888";
  }

  public InputStream getCertStream() {
      ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
      return certBis;
  }

  public IWXPayDomain getWXPayDomain() {
    return null;
  }
  
}