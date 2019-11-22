package com.pantanal.read.common;

import com.pantanal.read.common.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args){
        Map map = new HashMap<>();
        map.put("a",2);
        map.put("23","qq");

        System.out.println(JsonUtil.objectNode().pojoNode(map));
    }
}
