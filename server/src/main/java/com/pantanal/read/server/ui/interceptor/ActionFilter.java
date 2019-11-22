package com.pantanal.read.server.ui.interceptor;

import com.pantanal.read.server.ui.servlet.ParamFixServlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author gudong
 *
 */
public class ActionFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getRequestURI().startsWith(request.getContextPath() + "/paramfix/")) {
            // 把 columns[2][search][value]=xxx 转换成 columns[2].search.value=xxx
            chain.doFilter(new ParamFixServlet(request), resp);
        } else {
            chain.doFilter(request, resp);
        }
    }

    @Override
    public void destroy() {
    }
}