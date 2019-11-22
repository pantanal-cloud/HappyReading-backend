package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pantanal.read.common.bean.enums.State;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role")
public class RoleBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 0:NORMAL;-1:DELETED
     */
    private State state;

    /**
     * 是否管理员
     */
    private Boolean isadmin;

    /**
     * 分号相隔的权限名，例如：backendaction:device:index;backendaction:device:edit
     */
    private String authorities;

    /**
     * 备注
     */
    private String description;


}
