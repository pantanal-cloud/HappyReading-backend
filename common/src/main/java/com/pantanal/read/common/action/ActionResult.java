package com.pantanal.read.common.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class ActionResult extends ModelAndView {
    /**
     *
     */
    public ActionResult() {
        super();
        setSuccess(true);
    }

    /**
     *
     * @param success
     */
    public ActionResult(boolean success) {
        super();
        setSuccess(success);
    }

    /**
     *
     * @param success
     * @param msg
     */
    public ActionResult(boolean success, String msg) {
        super();
        setSuccess(success);
        addMsg(msg);
    }

    /**
     *
     * @param success
     * @param msg
     */
    public ActionResult(boolean success, String msg, Object result) {
        super();
        setSuccess(success);
        addMsg(msg);
        setResult(result);
    }

    /**
     *
     * @param viewName
     */
    public ActionResult(String viewName) {
        super(viewName);
        setSuccess(true);
    }

    /**
     *
     * @param view
     */
    public ActionResult(View view) {
        super(view);
        setSuccess(true);
    }

    /**
     *
     * @param viewName
     * @param success
     */
    public ActionResult(String viewName, boolean success) {
        super(viewName);
        setSuccess(success);
    }

    /**
     *
     * @param view
     * @param success
     */
    public ActionResult(View view, boolean success) {
        super(view);
        setSuccess(success);
    }

    /**
     *
     * @param viewName
     * @param success
     * @param result
     */
    public ActionResult(String viewName, boolean success, Object result) {
        super(viewName);
        setSuccess(success);
        setResult(result);
    }

    /**
     *
     * @param view
     * @param success
     * @param result
     */
    public ActionResult(View view, boolean success, Object result) {
        super(view);
        setSuccess(success);
        setResult(result);
    }

    /**
     *
     * @param success
     */
    public ActionResult setSuccess(boolean success) {
        addObject("success", success);
        return this;
    }

    /**
     *
     * @param result
     */
    public ActionResult setResult(Object result) {
        addObject("result", result);
        return this;
    }

    /**
     * add page data for jquery easyui
     *
     * @param total
     * @param rows
     */
    public ActionResult addPage(int total, Collection<?> rows) {
        addObject("total", total);
        addObject("rows", rows);
        return this;
    }

    /**
     * add page data for datatables
     *
     * @param draw
     * @param recordsTotal
     * @param recordsFiltered
     * @param data
     */
    public ActionResult addPage(int draw, int recordsTotal, int recordsFiltered, Collection<?> data) {
        addObject("draw", draw);
        addObject("recordsTotal", recordsTotal);
        addObject("recordsFiltered", recordsFiltered);
        addObject("data", data);
        return this;
    }

    /**
     *
     * @param error
     */
    public ActionResult addError(String error) {
        setSuccess(false);
        addMsg(error);
        return this;
    }

    /**
     *
     * @param msg
     */
    public ActionResult addMsg(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            addObject("msg", msg);
            List<String> msgList = (List<String>) super.getModel().get("msgList");
            if (msgList == null) {
                msgList = new ArrayList<String>();
                addObject("msgList", msgList);
            }
            msgList.add(msg);
        }
        return this;
    }
}
