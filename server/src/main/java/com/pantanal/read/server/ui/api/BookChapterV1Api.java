package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pantanal.read.common.bean.BookBean;
import com.pantanal.read.common.bean.BookChapterBean;
import com.pantanal.read.common.bean.BookTypeBean;
import com.pantanal.read.common.bean.ChannelBean;
import com.pantanal.read.common.dao.BookChapterDao;
import com.pantanal.read.common.dao.BookDao;
import com.pantanal.read.common.dao.BookTypeDao;
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
import java.util.List;

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
public class BookChapterV1Api {
  @Resource
  private BookChapterDao bookChapterDao;

  @ApiOperation("获取小说章节列表")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page_index", value = "翻页第几页, start with 1.", required = false, dataType = "int"),
      @ApiImplicitParam(name = "page_size", value = "每页显示多少条", required = false, dataType = "int"),
      @ApiImplicitParam(name = "order", value = "排序: asc,desc", required = false, dataType = "string")
  })

  @GetMapping("/books/{bookId}/chapters")
  public ResponseEntity getBookTypes(@PathVariable("bookId") Long bookId, @RequestParam(defaultValue = "1") Integer page_index, @RequestParam(defaultValue = "10") Integer page_size, @RequestParam(defaultValue = "asc") String order) {
    log.info("====getBookTypes===");
    QueryWrapper<BookChapterBean> queryWrapper = new QueryWrapper();
    queryWrapper.lambda().eq(BookChapterBean::getBookId, bookId).orderBy("asc".equalsIgnoreCase(order) || "desc".equalsIgnoreCase(order), "asc".equalsIgnoreCase(order), BookChapterBean::getIndex);
    Page page = new Page<BookChapterBean>((page_index - 1) * page_size, page_size);
    IPage<BookChapterBean> pageResult = bookChapterDao.selectPage(page, queryWrapper);

    DataList<BookChapterBean> dataList = new DataList();
    dataList.setCount((int) pageResult.getTotal());
    dataList.setDataList(pageResult.getRecords());

    Result<DataList<BookChapterBean>> result = new Result<>(dataList);
    return ResponseEntity.ok(result);
  }
}
