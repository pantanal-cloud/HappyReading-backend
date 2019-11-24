package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pantanal.read.common.bean.BookBean;
import com.pantanal.read.common.bean.BookTypeBean;
import com.pantanal.read.common.bean.ChannelBean;
import com.pantanal.read.common.dao.BookDao;
import com.pantanal.read.common.dao.BookTypeDao;
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
@Api(description = "Book Type API")
@RestController
@RequestMapping("/v1")
@Slf4j
public class BookTypeV1Api {
  @Resource
  private BookTypeDao bookTypeDao;
  @Resource
  private BookDao bookDao;


  @ApiOperation("获取小说类别")
  @GetMapping("/book-types")
  public ResponseEntity getBookTypes() {
    log.info("====getBookTypes===");

    List channelList = bookTypeDao.selectList(new QueryWrapper<BookTypeBean>());

    Result<List<ChannelBean>> result = new Result<>(channelList);
    return ResponseEntity.ok(result);
  }
}
