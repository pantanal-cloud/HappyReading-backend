package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pantanal.read.common.bean.type.JsonTypeHandler;
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

  /**
   * 资源详情url
   */
  private String url;

  private Boolean free;

  private String typeName;

  private Integer wordNum;

  private Integer clickNum;

  private Integer collectionNum;

  private Integer recommendNum;

  private LocalDateTime createTime;

  private Integer chapterCount;

  @TableField(typeHandler = JsonTypeHandler.class)
  private Long[] channelIds;

  private Long typeId;

  @TableField(exist = false)
  private BookTypeBean type;

  private Long lastChapterId;
  @TableField(exist = false)
  private BookChapterBean lastChapter;

  @TableField(exist = false)
  private BookChapterBean freeChapter;

  public Long getBookId() {
    return id;
  }
}
