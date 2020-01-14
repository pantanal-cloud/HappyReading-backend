package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pantanal.read.common.bean.OrderBean;
import com.pantanal.read.common.bean.ProductBean;
import com.pantanal.read.common.dao.OrderDao;
import com.pantanal.read.common.dao.ProductDao;
import com.pantanal.read.common.form.Result;
import com.pantanal.read.common.util.DateUtil;
import com.pantanal.read.server.common.Order;
import com.pantanal.read.server.pay.wechat.WechatPayHolder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@Api(description = "Order API")
@RestController
@RequestMapping("/v1")
@Slf4j
public class OrderV1Api {
  @Resource
  private ProductDao productDao;
  @Resource
  private OrderDao orderDao;


  @ApiOperation("下单")
  @PostMapping("/orderSubmit")
  public ResponseEntity orderSubmit(HttpServletRequest request, @RequestParam Long productId) {
    log.info("====orderSubmit===");

    long userId = (long)request.getAttribute("userId");
    log.info("userId is:" + userId);

    // product check
    ProductBean product = productDao.selectById(productId);
    if (product == null) {
      Result<String> result = new Result<>();
      result.setCode(1);
      result.setMsg("充值产品信息错误，请重试！");
      return ResponseEntity.ok(result);
    }
    String ipAddress = request.getHeader("X-FORWARDED-FOR");  
    if (ipAddress == null) {  
        ipAddress = request.getRemoteAddr();  
    }

    // order submit
    OrderBean order = new OrderBean();
    order.setUserId(userId);
    order.setOrderNo(Order.genOrderNo(userId));
    order.setType(1);
    order.setChannel(1);
    order.setPrice(product.getPrice());
    order.setDiscount(product.getDiscount());
    order.setRealPrice(product.getRealPrice());
    order.setStatus(1);
    order.setCreateAt(DateUtil.formatDateTime(new Date()));
    orderDao.insert(order);
    
    Map<String, String> data = new HashMap<String, String>();
    data.put("body", "HappyReading-付费书籍购买");
    data.put("out_trade_no", order.getOrderNo());
    data.put("device_info", "");
    data.put("fee_type", "CNY");
    data.put("total_fee", String.valueOf(order.getRealPrice()));
    data.put("spbill_create_ip", ipAddress);
    // TODO
    data.put("notify_url", "http://www.example.com/wxpay/notify");
    data.put("trade_type", "APP");

    try {
      Map<String, String> resp = WechatPayHolder.wxpayClient.unifiedOrder(data);
      System.out.println(resp);
      Result<Map<String, String>> result = new Result<>(resp);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      log.error("微信下单失败，错误信息:", e);
      Result<String> result = new Result<>();
      result.setCode(1);
      result.setMsg("下单失败，请重试！");
      return ResponseEntity.ok(result);
    }
  }
  
  @ApiOperation("支付结果查询")
  @PostMapping("/orderPayCheck")
  public ResponseEntity orderPayCheck(HttpServletRequest request, @RequestParam String orderNo) {
    log.info("====orderPayCheck===");

    long userId = (long)request.getAttribute("userId");
    log.info("userId is:" + userId);

    // order search first
    QueryWrapper<OrderBean> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(OrderBean::getOrderNo, orderNo);
    OrderBean order = orderDao.selectOne(queryWrapper);
    if (order == null) {
      Result<String> result = new Result<>();
      result.setCode(1);
      result.setMsg("订单信息错误，请重试！");
      return ResponseEntity.ok(result);
    }

    Map<String, String> data = new HashMap<String, String>();
    data.put("out_trade_no", order.getOrderNo());
    try {
      Map<String, String> resp = WechatPayHolder.wxpayClient.orderQuery(data);
      System.out.println(resp);
      // return_code 和result_code都为SUCCESS
      if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("result_code"))) {
        // 支付成功
        Result<Map<String, String>> result = new Result<>(resp);
        return ResponseEntity.ok(result);
      } else {
        log.error("微信定单支付失败，错误码:" + resp.get("err_code"));
        Result<String> result = new Result<>();
        result.setCode(1);
        result.setMsg("查询定单失败，请重试！");
        return ResponseEntity.ok(result);
      }
    } catch (Exception e) {
      log.error("微信查询定单失败，错误信息:", e);
      Result<String> result = new Result<>();
      result.setCode(1);
      result.setMsg("查询定单失败，请重试！");
      return ResponseEntity.ok(result);
    }
  }

}
