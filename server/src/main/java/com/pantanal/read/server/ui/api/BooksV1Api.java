package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pantanal.read.common.bean.BookBean;
import com.pantanal.read.common.bean.BookChapterBean;
import com.pantanal.read.common.bean.BookTypeBean;
import com.pantanal.read.common.dao.BookChapterDao;
import com.pantanal.read.common.dao.BookDao;
import com.pantanal.read.common.dao.BookTypeDao;
import com.pantanal.read.common.form.DataList;
import com.pantanal.read.common.form.Result;
import com.pantanal.read.common.util.NumberUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
@Api(description = "Books API")
@RestController
@RequestMapping("/v1")
@Slf4j
public class BooksV1Api {
  @Resource
  private BookDao bookDao;
  @Resource
  private BookChapterDao bookChapterDao;


  @ApiOperation("获取所有小说")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page_index", value = "翻页第几页, start with 1.", required = false, dataType = "int"),
      @ApiImplicitParam(name = "page_size", value = "每页显示多少条", required = false, dataType = "int"),
      @ApiImplicitParam(name = "book_type", value = "图书类型id", required = false, dataType = "long")
  })
  @GetMapping("/books")
  public ResponseEntity books(@RequestParam(defaultValue = "1") Integer page_index, @RequestParam(defaultValue = "10") Integer page_size, @RequestParam(defaultValue = "") Long book_type) {
    log.info("====books, page_index:{}, page_size:{}, book_type:{}===", page_index, page_size, book_type);


    Page page = new Page<BookBean>((page_index - 1) * page_size, page_size);
    QueryWrapper<BookBean> queryWrapper = new QueryWrapper<>();
    if (NumberUtil.defaultValue(book_type) > 0) {
      queryWrapper.lambda().eq(BookBean::getTypeId, book_type);
    }
    IPage pageResult = bookDao.selectPage(page, queryWrapper);
    DataList<BookBean> dataList = new DataList();
    dataList.setCount((int) pageResult.getTotal());
    dataList.setDataList(pageResult.getRecords());

    Result<DataList<BookBean>> result = new Result<>(dataList);
    return ResponseEntity.ok(result);
  }

  @ApiOperation("获取小说详情")
  @GetMapping("/books/{bookId}")
  public ResponseEntity detail(@PathVariable("bookId") Long bookId) {
    log.info("====detail, bookId:{}===", bookId);

    BookBean bookBean = bookDao.selectById(bookId);

    Map<String, Object> map = new HashMap<>();
    map.put("book", bookBean);
    map.put("chapter_count", bookBean != null ? NumberUtil.defaultValue(bookBean.getChapterCount()) : null);
    map.put("last_chapter", null);
    if (bookBean != null && bookBean.getLastChapterId() != null) {
      BookChapterBean bookChapterBean = bookChapterDao.selectById(bookBean.getLastChapterId());
      map.put("last_chapter", bookChapterBean);
    }

    Result result = new Result<>(map);
    return ResponseEntity.ok(result);
  }

  @ApiOperation("获取小说最新章节")
  @PostMapping("books/latest-chapter")
  public ResponseEntity latestChapter(@RequestBody Long[] bookIds) {
    log.info("====latestChapter===");

    BookBean queryBean = new BookBean();
    queryBean.setIds(bookIds);
    List<BookBean> chapterBeanList = bookDao.query(queryBean);

    DataList dataList = new DataList();
    dataList.setCount(chapterBeanList.size());
    for (BookBean book : chapterBeanList) {
      Map<String, Object> map = new HashMap<>();
      map.put("book_id", book.getId());
      map.put("chapter_count", NumberUtil.defaultValue(book.getChapterCount()));
      map.put("last_chapter", book.getLastChapter());
      dataList.getDataList().add(map);
    }

    Result<DataList> result = new Result<>(dataList);
    return ResponseEntity.ok(result);
  }

  @ApiOperation("搜索小说")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page_index", value = "翻页第几页, start with 1.", required = true, dataType = "int"),
      @ApiImplicitParam(name = "page_size", value = "每页显示多少条", required = true, dataType = "int"),
      @ApiImplicitParam(name = "keyword", value = "关键词", required = false, dataType = "string")
  })
  @GetMapping("/search/books")
  public ResponseEntity searchBooks(@RequestParam(defaultValue = "1") Integer page_index, @RequestParam(defaultValue = "10") Integer page_size, @RequestParam(defaultValue = "") String keyword) {
    log.info("====searchBooks, page_index:{}, page_size:{}, keyword:{}===", page_index, page_size, keyword);


    BookBean queryBean = new BookBean();
    queryBean.setPstart((page_index - 1) * page_size);
    queryBean.setPlimit(page_size);
    queryBean.setName(keyword);
    List<BookBean> chapterBeanList = bookDao.query(queryBean);

    DataList<BookBean> dataList = new DataList();
    dataList.setCount(chapterBeanList.size());
    dataList.setDataList(chapterBeanList);

    Result<DataList<BookBean>> result = new Result<>(dataList);
    return ResponseEntity.ok(result);
  }
}
