package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pantanal.read.common.bean.AppReleaseBean;
import com.pantanal.read.common.bean.BookBean;
import com.pantanal.read.common.dao.AppReleaseDao;
import com.pantanal.read.common.dao.ChannelDao;
import com.pantanal.read.common.form.DataList;
import com.pantanal.read.common.form.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@Api(description = "AppRelease API")
@RestController
@RequestMapping("")
@Slf4j
public class AppReleaseApi {
  @Resource
  private AppReleaseDao appReleaseDao;

  @ApiOperation("查询Android最新版本")
  @GetMapping("/android/releases/latest")
  public ResponseEntity getAndriodLatest() {
    log.info("====getAndriodLatest:{}===");
    QueryWrapper<AppReleaseBean> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(AppReleaseBean::getType, "andriod");
    AppReleaseBean appReleaseBean = appReleaseDao.selectOne(queryWrapper);
    Result<AppReleaseBean> result = new Result<>(appReleaseBean);
    return ResponseEntity.ok(result);
  }
}
