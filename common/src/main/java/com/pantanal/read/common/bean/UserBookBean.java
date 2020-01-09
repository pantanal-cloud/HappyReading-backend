package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.pantanal.read.common.bean.BaseBean;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2020-01-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_book")
public class UserBookBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long bookId;

    /**
     * 可以免费到哪个章节index
     */
    private Integer freeChapterIndex;
    /**
     * 已经读到哪个章节index
     */
    private Integer readChapterIndex;


}
