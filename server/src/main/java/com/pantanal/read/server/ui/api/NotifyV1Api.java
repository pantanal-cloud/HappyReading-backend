package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pantanal.read.common.bean.OrderBean;
import com.pantanal.read.common.bean.UserBean;
import com.pantanal.read.common.dao.OrderDao;
import com.pantanal.read.common.dao.UserDao;
import com.pantanal.read.common.util.DateUtil;
import com.pantanal.read.server.pay.wechat.WechatPayHolder;
import com.pantanal.read.server.pay.wechat.sdk.WXPayUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Api：修饰整个类，描述Controller的作用
 * @ApiOperation：描述一个类的一个方法，或者说一个接口
 * @ApiParam：单个参数描述
 * @ApiModel：用对象来接收参数
 * @ApiProperty：用对象接收参数时，描述对象的一个字段
 * @ApiResponse：HTTP响应其中1个描述
 * @ApiResponses：HTTP响应整体描述
 * @ApiIgnore：使用该注解忽略这个API
 * @ApiError ：发生错误返回的信息
 * @ApiParamImplicitL：一个请求参数
 * @ApiParamsImplicit 多个请求参数
 */
@Api(description = "Notify API")
@RestController
@RequestMapping("/v1")
@Slf4j
public class NotifyV1Api {
  @Resource
  private OrderDao orderDao;
  @Resource
  private UserDao userDao;


  @ApiOperation("支付通知")
  @PostMapping("/payNotify")
  @ResponseBody
  public String payNotify(HttpServletRequest request, @RequestBody String notifyData) throws Exception {
    log.info("====payNotify===");

    Map<String, String> result = new HashMap<String, String>();
    try {
      Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);  // 转换成map
      System.out.println(notifyMap);
      if (WechatPayHolder.wxpayClient.isPayResultNotifySignatureValid(notifyMap)) {
        // 签名正确
        // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
        if ("SUCCESS".equals(notifyMap.get("return_code")) && "SUCCESS".equals(notifyMap.get("result_code"))) {
          // order update
          String orderNo = notifyMap.get("out_trade_no");
          // order search first
          QueryWrapper<OrderBean> queryWrapper = new QueryWrapper<>();
          queryWrapper.lambda().eq(OrderBean::getOrderNo, orderNo);
          OrderBean order = orderDao.selectOne(queryWrapper);
          if (order != null) {
            if (order.getStatus() == 1) {
              order.setStatus(2);
              order.setPayAt(DateUtil.formatDateTime(new Date()));
              orderDao.updateById(order);
              // update user balance
              UserBean user = userDao.selectById(order.getUserId());
              if (user != null) {
                user.setBalance(user.getBalance() + order.getRealPrice());
                userDao.updateById(user);
              } else {
                log.info("该订单的用户ID查不到用户信息:" + order.getUserId());
              }
            } else {
              log.info("订单状态不是待支付:" + order.getStatus());
            }
          } else {
            log.info("订单不存在:" + orderNo);
          }
        }
        result.put("return_code", "SUCCESS");
        result.put("return_msg", "OK");
        return WXPayUtil.mapToXml(result);
      } else {
        // 签名错误，如果数据里没有sign字段，也认为是签名错误
        result.put("return_code", "FAIL");
        result.put("return_msg", "签名错误");
        return WXPayUtil.mapToXml(result);
      }
    } catch (Exception e) {
      log.error("微信通知校验签名异常:", e);
      result.put("return_code", "FAIL");
      result.put("return_msg", "校验签名异常");
      return WXPayUtil.mapToXml(result);
    }
  }
  
}
