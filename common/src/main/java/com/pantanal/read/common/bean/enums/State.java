package com.pantanal.read.common.bean.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum State implements IEnum<Integer> {
    NORMAL(0,"正常"),
    DELETED(-1,"已删除");

    private final int value;
    private  final String desc;

    State(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}


