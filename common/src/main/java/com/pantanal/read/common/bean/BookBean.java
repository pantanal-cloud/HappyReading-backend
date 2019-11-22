package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.pantanal.read.common.bean.BaseBean;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("book")
public class BookBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String coverUrl;

    private String name;

    private String intro;

    private String author;

    private Boolean isFinished;

    private Boolean free;

    private Long typeId;

    private String typeName;

    private Integer wordNum;

    private Integer clickNum;

    private Integer collectionNum;

    private Integer recommendNum;

    private LocalDateTime createTime;

    private Integer chapterCount;

    private Long lastChapterId;

    private Long freeChapterId;

    private String channelIds;


}
