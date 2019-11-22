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
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("book_chapter_content")
public class BookChapterContentBean extends BaseBean {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  private Integer chapterIndex;

  private String chapterName;

  private String content;

  private LocalDateTime createTime;

  private Long chapterId;
  @TableField(exist = false)
  private BookChapterBean chapter;

  private Long bookId;

  @TableField(exist = false)
  private BookBean book;
}
