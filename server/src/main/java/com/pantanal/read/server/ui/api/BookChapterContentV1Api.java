package com.pantanal.read.server.ui.api;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pantanal.read.common.bean.BookChapterBean;
import com.pantanal.read.common.bean.BookChapterContentBean;
import com.pantanal.read.common.dao.BookChapterContentDao;
import com.pantanal.read.common.dao.BookChapterDao;
import com.pantanal.read.common.form.Result;
import com.pantanal.read.common.util.HttpClientUtil;
import com.pantanal.read.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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

    if (content == null) {
      BookChapterBean bookChapterBean = bookChapterDao.selectOne(Wrappers.<BookChapterBean>lambdaQuery().eq(BookChapterBean::getBookId, bookId).eq(BookChapterBean::getIndex, position));
      if (bookChapterBean != null) {
        if (StringUtils.isNotBlank(bookChapterBean.getUrl()) && bookChapterBean.getUrl().indexOf("www.3gsc.com.cn") > -1) {
          content = handlerGggChapterContent(bookChapterBean);
        }
      }
    }
    content.setContent(StringUtil.removeHtmlTag(StringUtils.defaultString(content.getContent())));
    Result<BookChapterContentBean> result = new Result<>(content);
    return ResponseEntity.ok(result);
  }

  /**
   *
   * @param bookChapterBean
   * @return
   */
  private BookChapterContentBean handlerGggChapterContent(BookChapterBean bookChapterBean) {
    HttpClientUtil.HttpResult result = HttpClientUtil.doGet(bookChapterBean.getUrl());
    if (result == null) {
      log.error("访问书本信息失败，url:{}", bookChapterBean.getUrl());
    } else {
      try {
        Page chapterPage = new Page(new CrawlDatum(bookChapterBean.getUrl()), "utf-8", result.getResult().getBytes("utf-8"));

        Elements elements = chapterPage.select(".menu-area");
        if (!elements.isEmpty()) {
          BookChapterContentBean content = new BookChapterContentBean();
          content.setBookId(bookChapterBean.getBookId());
          content.setChapterId(bookChapterBean.getId());
          content.setChapterName(bookChapterBean.getName());
          content.setChapterIndex(bookChapterBean.getIndex());
          content.setContent(elements.first().html());
          content.setCreateTime(LocalDateTime.now());
          bookChapterContentDao.insert(content);
          return content;
        }
      } catch (Exception e) {
        log.error("==get chapter content error, chapter url:{}", bookChapterBean.getUrl(), e);
      }
    }
    return null;
  }
}
