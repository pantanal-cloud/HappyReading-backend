package com.pantanal.read.server.ui.action;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pantanal.read.common.action.Action;
import com.pantanal.read.common.action.ActionResult;
import com.pantanal.read.common.bean.SysconfigBean;
import com.pantanal.read.common.dao.SysconfigDao;
import com.pantanal.read.server.ui.form.SysconfigForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class SysconfigAction extends Action {
    @Resource
    private SysconfigDao sysconfigDao;

    /**
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/sysconfigIndex.do")
    public ActionResult index(SysconfigForm form, HttpServletRequest request, HttpServletResponse response) {
        ActionResult actionResult = new ActionResult("/sysconfigIndex");
        actionResult.addObject("sysconfig", sysconfigDao.selectOne(new QueryWrapper<SysconfigBean>()));
        return actionResult;
    }


    /**
     * @return
     */
    @RequestMapping(value = "/sysconfigSave.do", method = RequestMethod.POST)
    public ActionResult save(SysconfigForm form, HttpServletRequest request, HttpServletResponse response) {
        ActionResult actionResult = new ActionResult();
        sysconfigDao.updateById(form.getSysconfig());

        writeToJSON(response, actionResult);
        return null;
    }
}
