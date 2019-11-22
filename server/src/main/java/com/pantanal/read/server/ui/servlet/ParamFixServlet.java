package com.pantanal.read.server.ui.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 把 columns[2][search][value]=xxx 转换成 columns[2].search.value=xxx
 *
 * @author gudong
 *
 */
public class ParamFixServlet extends HttpServletRequestWrapper {
    private Map<String, String[]> paramMap;

    public ParamFixServlet(HttpServletRequest request) {
        super(request);
        // 把 columns[2][search][value]=xxx 转换成 columns[2].search.value=xxx 不然不能json解析
        Pattern p = Pattern.compile("(\\[[0-9]*[\\$\\_\\.a-zA-Z]+[0-9]*\\])");
        Matcher m;
        boolean find = false;
        int start = 0;
        String newKey = "";
        Map<String, String[]> paramMap = new HashMap<String, String[]>();
        Map<String, String[]> oldMap = super.getParameterMap();
        for (String paramKey : oldMap.keySet()) {
            find = false;
            start = 0;
            newKey = "";
            m = p.matcher(paramKey);
            while (m.find()) {
                find = true;
                newKey += paramKey.substring(start, m.start());
                newKey += "." + paramKey.substring(m.start() + 1, m.end() - 1);
                start = m.end();
            }
            paramMap.put(find ? newKey : paramKey, oldMap.get(paramKey));
        }
        this.paramMap = paramMap;
    }

    @Override
    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        if (values != null && values.length > 0) {
            return values[0];
        } else {
            return null;
        }
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.paramMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        return getParameterMap().get(name);
    }
}