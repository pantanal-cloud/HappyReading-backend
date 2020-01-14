package com.pantanal.read.server.common;

import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * Order
 */
@Slf4j
public class Order {
  
  private static final String MAGIC_CODE = "wj79124e122478";

  /**
   * TIMESTAMP(10) + UID(8) + RANDOM(2);
   */
  public static String genOrderNo(long userId) {
    String orderNo = null;
    int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
    String userIdStr = String.valueOf(userId);
    String userIdStr1 = userIdStr;
    if (userIdStr.length() <= 8) {
      for (int i = 0; i < 8 - userIdStr.length(); i++) {
        userIdStr1 = "0" + userIdStr1;
      }
    } else {
      log.info("userId length is bigger than 8!");
    }
    orderNo = "" + System.currentTimeMillis()/1000 + userIdStr1 + randomNum;
    return orderNo;
  }

  public static void main(String[] args) throws Exception {
    System.out.println(genOrderNo(1));
    System.out.println(genOrderNo(13));
    System.out.println(genOrderNo(112));
    System.out.println(genOrderNo(1251251));
    System.out.println(genOrderNo(11521));
    System.out.println(genOrderNo(118240801241l));
  }

}