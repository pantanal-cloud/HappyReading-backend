package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("product")
public class ProductBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型: 1:充值;2:会员
     */
    private int type;
    
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

    private String createAt;
    private String updateAt;

}
