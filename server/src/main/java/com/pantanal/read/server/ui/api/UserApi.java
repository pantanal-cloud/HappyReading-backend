package com.pantanal.read.server.ui.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pantanal.read.common.bean.BookBean;
import com.pantanal.read.common.bean.UserBean;
import com.pantanal.read.common.dao.BookDao;
import com.pantanal.read.common.dao.UserDao;
import com.pantanal.read.common.form.DataList;
import com.pantanal.read.common.form.Result;
import com.pantanal.read.server.common.Token;

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
@Api(description = "User API")
@RestController
@RequestMapping("/v1")
@Slf4j
public class UserApi {
  @Resource
  private UserDao userDao;

  @ApiOperation("微信登录API")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "wechatOpenId", value = "微信openId", required = true, dataType = "string")
  })
  @PostMapping("/login")
  public ResponseEntity get(@RequestParam String wechatOpenId) {
    log.info("====login user:{}===", wechatOpenId);
    QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
    queryWrapper.lambda().eq(UserBean::getWechatOpenid, wechatOpenId);
    List<UserBean> userList = userDao.selectList(queryWrapper);
    if (userList.size() > 0) {
      UserBean u0 = userList.get(0);
      u0.setToken(Token.genUserToken(u0.getId()));
      Result result = new Result<>(u0);
      return ResponseEntity.ok(result);
    } else {
      UserBean user = new UserBean();
      user.setWechatOpenid(wechatOpenId);
      userDao.insert(user);
      // TODO check user id is or not exist
      user.setToken(Token.genUserToken(user.getId()));
      Result result = new Result<>(user);
      return ResponseEntity.ok(result);
    }
  }
}
