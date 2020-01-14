package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("order")
public class OrderBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * 订单号(20位)
     */
    private String orderNo;

    /**
     * 类型: 1:充值;2:会员
     */
    private int type;
    
    /**
     * 渠道: 1:微信;2:支付宝
     */
    private int channel;
    
    /**
     * 总价，单位：分
     */
    private int price;
    
    /**
     * 折扣，单位：分
     */
    private int discount;
    
    /**
     * 最终价，单位：分
     */
    private int realPrice;
    
    /**
     * 状态: 1: 未支付, 2: 已支付, 3: 已取消, 4: 已失效
     */
    private int status;

    private String createAt;
    private String payAt;
    
}
