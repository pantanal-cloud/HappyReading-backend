package com.pantanal.read.server.ui.action;

import com.pantanal.read.common.action.Action;
import com.pantanal.read.common.action.ActionResult;
import com.pantanal.read.server.ui.form.IndexForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class IndexAction extends Action {
    /**
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/index.do")
    public ActionResult index(IndexForm form, HttpServletRequest request, HttpServletResponse response) {
        ActionResult actionResult = new ActionResult("/index");
        return actionResult;
    }
}
