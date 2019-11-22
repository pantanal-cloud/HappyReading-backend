package com.pantanal.read.common.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.pantanal.read.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author  gudong
 */
@RequestMapping(value = "/{pages}")
@Controller
@Slf4j
public class Action {


    /**
     *
     * @param jsonResult
     *          JsonResult对象
     */
    protected void writeToJSON(HttpServletResponse response, ActionResult jsonResult) {
        ValueNode json = JsonUtil.objectNode().pojoNode(jsonResult.getModel());
        response.setContentType("text/json; charset=utf-8");
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            log.error("==write json error! json:" + json, e);
        }
    }

    protected void writeToJSON(HttpServletResponse response, JsonNode jsonObject) {
        response.setContentType("text/json; charset=utf-8");
        try {
            response.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            log.error("==write json error! json:" + jsonObject, e);
        }
    }
}
