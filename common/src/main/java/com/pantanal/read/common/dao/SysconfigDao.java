package com.pantanal.read.common.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pantanal.read.common.bean.SysconfigBean;

/**
 * <p>
 * 系统配置参数表 Mapper 接口
 * </p>
 *
 * @author mybatis plus genertor
 * @since 2019-11-12
 */
@DS("master")
public interface SysconfigDao extends BaseMapper<SysconfigBean> {

}
