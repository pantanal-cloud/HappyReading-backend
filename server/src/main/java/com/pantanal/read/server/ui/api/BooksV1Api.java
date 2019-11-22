package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pantanal.read.common.bean.BookBean;
import com.pantanal.read.common.dao.BookDao;
import com.pantanal.read.common.form.DataList;
import com.pantanal.read.common.form.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Api(description = "Books API")
@RestController
@RequestMapping("/v1")
@Slf4j
public class BooksV1Api {
  @Resource
  private BookDao bookDao;

  @ApiOperation("获取所有小说")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page_index", value = "翻页第几页, start with 1.", required = false, dataType = "Integer"),
      @ApiImplicitParam(name = "page_size", value = "每页显示多少条", required = false, dataType = "Integer"),
      @ApiImplicitParam(name = "book_type", value = "图书类型", required = false, dataType = "String")
  })
  @GetMapping("/books")
  public ResponseEntity books(@RequestParam(defaultValue = "1") Integer page_index, @RequestParam(defaultValue = "10") Integer page_size, @RequestParam(defaultValue = "") String book_type) {
    log.info("====books, page_index:{}, page_size:{}, book_type:{}===", page_index, page_size, book_type);


    Page page = new Page<BookBean>(page_index / page_size + 1, page_size);
    QueryWrapper<BookBean> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotBlank(book_type)) {
      queryWrapper.lambda().eq(BookBean::getTypeName, book_type);
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

    BookBean book = bookDao.selectById(bookId);

    Result<BookBean> result = new Result<>(book);
    return ResponseEntity.ok(result);
  }
}
