package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("book_chapter")
public class BookChapterBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("`index`")
    private Integer index;

    private String name;

    private String url;
    @TableField("`desc`")
    private String desc;

    private LocalDateTime createTime;

    private Long bookId;

    @TableField(exist = false)
    private BookBean book;

    @TableField(exist = false)
    private boolean free;

    private Boolean vip;
}
