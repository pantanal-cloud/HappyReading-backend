package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pantanal.read.common.bean.enums.AuthorityType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2019-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("authority")
public class AuthorityBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 标题
     */
    private String label;

    /**
     * 0:BACKEND_MENU;1:BACKEND_ACTION;2:WEB_MENU;3:WEB_ACTION
     */
    private AuthorityType type;

    /**
     * 备注
     */
    private String description;


}
