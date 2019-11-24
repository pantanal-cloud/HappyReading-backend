package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.TableField;
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

  @TableField(exist = false)
  private Long bookId;

  private String coverUrl;

  private String name;

  private String intro;

  private String author;

  private Boolean isFinished;

  private Boolean free;

  private Long typeId;

  @TableField(exist = false)
  private BookTypeBean type;

  @TableField(exist = false)
  private String typeName;

  private Integer wordNum;

  private Integer clickNum;

  private Integer collectionNum;

  private Integer recommendNum;

  private LocalDateTime createTime;

  private Integer chapterCount;

  private Long lastChapterId;

  @TableField(exist = false)
  private BookChapterBean lastChapter;

  private Long freeChapterId;
  @TableField(exist = false)
  private BookChapterBean freeChapter;

  private String channelIds;


}
