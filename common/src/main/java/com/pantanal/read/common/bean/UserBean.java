package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pantanal.read.common.bean.enums.State;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
public class UserBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     *
     */
    private String wechatOpenid;

    /**
     * 密码
     */
    private String phone;

    /**
     * 0:NORMAL;-1:DELETED
     */
    private State state;
    /**
     * 余额
     */
    private int balance;

    private Long roleid;

    @TableField(exist = false)
    private RoleBean role;
    /**
     * 备注
     */
    private String description;

    @TableField(exist = false)
    private String token;

}
