package com.pantanal.read.server.common;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

/**
 * Token
 */
@Slf4j
public class Token {
  
  private static final String MAGIC_CODE = "wj79124e122478";
  private static final String encryptKey = "c4b84456c1379bec99c4d1b7e9f13172";
  private static final String iv = "abcdefgh12345678";

  /**
   * A = MAGIC_CODE + UID + TIMESTAMP;
   * B = base64(aes-256-cbc(A))
   */
  public static String genUserToken(long userId) {
    String token = null;
    try {
      // a
      String a = MAGIC_CODE + userId + System.currentTimeMillis();
      // b
      token = aesEncryptToCBC(a);
    } catch (Exception e) {
      log.info("gen user token occur exception:", e);
    }
    return token;
  }

  public static long getUserIdFromToken(String token) {
    long userId = 0;
    if (token != null && !"".equals(token)) {
      try {
        String a = aesDecryptToCBC(token);
        if (a.startsWith(MAGIC_CODE)) {
          String uid = a.substring(a.indexOf(MAGIC_CODE) + MAGIC_CODE.length(), a.length() - 13);
          userId = Integer.parseInt(uid);
        }
      } catch (Exception e) {
        log.info("get userId from token occur exception:", e);
      }
    }
    return userId;
  }


  /**
   * 验证密钥长度是否有效
   *
   * @param key 密钥bytes
   * @throws Exception
   */
  private static void checkkey(byte[] key) throws Exception {

    if (key.length != 16 && key.length != 32) {
      throw new Exception("密钥长度错误，key byte[]必须是16或者32位");
    }
  }

  private static String aesEncryptToCBC(String content) throws Exception {
    byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(encryptKey.toCharArray());
    checkkey(key);
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    // 算法参数
    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
    cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), paramSpec);
    return Base64.getEncoder().encodeToString(cipher.doFinal(content.getBytes("utf-8")));
  }

  private static String aesDecryptToCBC(String content) throws Exception {
    byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(encryptKey.toCharArray());
    checkkey(key);
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    // 算法参数
    AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), paramSpec);
    byte[] decryptBytes = cipher.doFinal(Base64.getDecoder().decode(content));
    return new String(decryptBytes, "utf-8");
  }

  public static void main(String[] args) throws Exception {
    String content_str = "helloworld 你好";
    // cbc256 String
    String encryptString = aesEncryptToCBC(content_str);
    System.out.println(encryptString);
    String decryptString = aesDecryptToCBC(encryptString);
    System.out.println(decryptString);

    int userId = 1003;
    String token = genUserToken(userId);
    System.out.println(token);

    System.out.println(getUserIdFromToken(token));
  }

}