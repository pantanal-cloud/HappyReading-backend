package com.pantanal.read.common.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseBean implements Serializable {
    @TableField(exist = false)
    private Long[] ids;
    @TableField(exist = false)
    private int pstart = 0;
    @TableField(exist = false)
    private int plimit = -1;
}
