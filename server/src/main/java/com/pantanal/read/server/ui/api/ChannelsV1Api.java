package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pantanal.read.common.bean.BookBean;
import com.pantanal.read.common.bean.ChannelBean;
import com.pantanal.read.common.dao.BookDao;
import com.pantanal.read.common.dao.ChannelDao;
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
@Api(description = "Channels API")
@RestController
@RequestMapping("/v1")
@Slf4j
public class ChannelsV1Api {
  @Resource
  private ChannelDao channelDao;
  @Resource
  private BookDao bookDao;


  @ApiOperation("获取所有频道")
  @GetMapping("/channels")
  public ResponseEntity channels() {
    log.info("====channels===");

    List channelList = channelDao.selectList(new QueryWrapper<ChannelBean>());

    Result<List<ChannelBean>> result = new Result<>(channelList);
    return ResponseEntity.ok(result);
  }


  @ApiOperation("获取频道内容-榜单,TODO")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page_index", value = "翻页第几页, start with 1.", required = false, dataType = "int"),
      @ApiImplicitParam(name = "page_size", value = "每页显示多少条", required = false, dataType = "int")
  })
  @GetMapping("/channels/books/{channelId}")
  public ResponseEntity books(@RequestParam(defaultValue = "1") Integer page_index, @RequestParam(defaultValue = "10") Integer page_size, @PathVariable("channelId") Long channelId) {
    log.info("====books, page_index:{}, page_size:{}, channelId:{}===", page_index, page_size, channelId);


    Page page = new Page<BookBean>((page_index - 1) * page_size, page_size);
    QueryWrapper<BookBean> queryWrapper = new QueryWrapper<>();

    IPage pageResult = bookDao.selectPage(page, queryWrapper);
    DataList<BookBean> dataList = new DataList();
    dataList.setCount((int) pageResult.getTotal());
    dataList.setDataList(pageResult.getRecords());

    Result<DataList<BookBean>> result = new Result<>(dataList);
    return ResponseEntity.ok(result);
  }

  @ApiOperation("获取频道内容-书籍列表,TODO")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page_index", value = "翻页第几页, start with 1.", required = false, dataType = "int"),
      @ApiImplicitParam(name = "page_size", value = "每页显示多少条", required = false, dataType = "int")
  })
  @GetMapping("/channels/book-ranking/{channelId}")
  public ResponseEntity booksRanking(@RequestParam(defaultValue = "1") Integer page_index, @RequestParam(defaultValue = "10") Integer page_size, @PathVariable("channelId") Long channelId) {
    log.info("====booksRanking, page_index:{}, page_size:{}, channelId:{}===", page_index, page_size, channelId);


    Page page = new Page<BookBean>((page_index - 1) * page_size, page_size);
    QueryWrapper<BookBean> queryWrapper = new QueryWrapper<>();

    IPage pageResult = bookDao.selectPage(page, queryWrapper);
    DataList<BookBean> dataList = new DataList();
    dataList.setCount((int) pageResult.getTotal());
    dataList.setDataList(pageResult.getRecords());

    Result<DataList<BookBean>> result = new Result<>(dataList);
    return ResponseEntity.ok(result);
  }
}
