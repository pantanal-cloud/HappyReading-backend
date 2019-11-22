package com.pantanal.read.common.bean.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum AuthorityType implements IEnum<Integer> {
    BACKEND_MENU(0,"后台菜单"),
    BACKEND_ACTION(1,"后台操作"),
    WEB_MENU(2,"前台菜单"),
    WEB_ACTION(3,"前台操作");

    private final int value;
    private  final String desc;

    AuthorityType(int value, String desc) {
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