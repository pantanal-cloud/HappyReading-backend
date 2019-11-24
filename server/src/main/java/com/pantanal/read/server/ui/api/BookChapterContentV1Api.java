package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pantanal.read.common.bean.BookBean;
import com.pantanal.read.common.bean.BookChapterBean;
import com.pantanal.read.common.bean.BookChapterContentBean;
import com.pantanal.read.common.dao.BookChapterContentDao;
import com.pantanal.read.common.dao.BookChapterDao;
import com.pantanal.read.common.form.DataList;
import com.pantanal.read.common.form.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Api：修饰整个类，描述Controller的作用
 * @ApiOperation：描述一个类的一个方法，或者说一个接口
 * @ApiParam：单个参数描述
 * @ApiModel：用对象来接收参数
 * @ApiProperty：用对象接收参数时，描述对象的一个字段
 * @ApiResponse：HTTP响应其中1个描述
 * @ApiResponses：HTTP响应整体描述
 * @ApiIgnore：使用该注解忽略这个API
 * @ApiError ：发生错误返回的信息
 * @ApiParamImplicitL：一个请求参数
 * @ApiParamsImplicit 多个请求参数
 */
@Api(description = "Book Chapter API")
@RestController
@RequestMapping("/v1")
@Slf4j
public class BookChapterContentV1Api {

  @Resource
  private BookChapterDao bookChapterDao;
  @Resource
  private BookChapterContentDao bookChapterContentDao;

  @ApiOperation("获取小说章节中的内容")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "query_direction", value = "取值: current", required = false, dataType = "string")
  })
  @GetMapping("/books/{bookId}/chapters/{position}")
  public ResponseEntity getChapterContent(@PathVariable("bookId") Long bookId, @PathVariable("position") int position, @RequestParam(defaultValue = "current") String query_direction) {
    log.info("====getChapterContent===");
    QueryWrapper<BookChapterContentBean> queryWrapper = new QueryWrapper<BookChapterContentBean>();
    queryWrapper.lambda().eq(BookChapterContentBean::getBookId, bookId).eq(BookChapterContentBean::getChapterIndex, position);
    BookChapterContentBean content = bookChapterContentDao.selectOne(queryWrapper);

    Result<BookChapterContentBean> result = new Result<>(content);
    return ResponseEntity.ok(result);
  }
}
