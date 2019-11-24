package com.pantanal.read.common.dao;

import com.pantanal.read.common.bean.BookBean;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2019-11-18
 */
public interface BookDao extends BaseMapper<BookBean> {
  List<BookBean> query(BookBean book);

  int queryCount(BookBean book);
}
