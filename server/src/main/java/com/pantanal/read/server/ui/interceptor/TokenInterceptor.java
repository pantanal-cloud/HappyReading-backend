package com.pantanal.read.server.ui.interceptor;

import com.pantanal.read.common.util.StringUtil;
import com.pantanal.read.server.common.Token;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author gudong
 *
 */
@Configuration
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private static ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    private static MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
    private static MarshallingView xmlView = new MarshallingView(new XStreamMarshaller());
    static {
        // jsonView.getObjectMapper().setSerializationInclusion(Include.NON_NULL);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime.set(System.currentTimeMillis());

        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
        log.info("TokenInterceptor---------------------------" + url);
        String token = request.getHeader("X-Token");
        log.info("token from header---------------------------" + token);
        long userId = Token.getUserIdFromToken(token);
        log.info("userId is---------------------------" + userId);
        if (userId == 0) {
            log.info("userId is invalid!");
            return false;
        }
		request.setAttribute("userId", userId);

        
//         String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
//         Enumeration<String> attributeNameList = request.getParameterNames();
//         String paramName;
//         boolean first = false;
//         while (attributeNameList.hasMoreElements()) {
//             if (first == false) {
//                 first = true;
//                 url += "?";
//             } else {
//                 url += "&";
//             }
//             paramName = (String) attributeNameList.nextElement();
//             url += paramName + "=" + request.getParameter(paramName);
//         }
// //        UserBean loginUser = (UserBean) SecurityUtils.getSubject().getPrincipal();
// //        String logStr = "==Action== " + (loginUser == null ? "" : loginUser.getUsername()) + " from:" + request.getRemoteHost() + " request:" + url
// //                + " , cost:" + (System.currentTimeMillis() - startTime.get()) + "ms";
//         String logStr = "==Action== " + " from:" + request.getRemoteHost() + " request:" + url
//                 + " , cost:" + (System.currentTimeMillis() - startTime.get()) + "ms";

//         if (modelAndView != null) {
//             boolean json = request.getParameter("json") != null || request.getRequestURI().startsWith(request.getContextPath() + "/json/")
//                     || request.getRequestURI().startsWith(request.getContextPath() + "/api/") || (modelAndView.getView() instanceof MappingJackson2JsonView);
//             boolean xml = request.getParameter("xml") != null || request.getRequestURI().startsWith(request.getContextPath() + "/xml/")
//                     || (modelAndView.getView() instanceof MarshallingView);
//             if (json || xml) {
//                 // 删除controller形参
//                 String springValidationPrefix = "org.springframework.validation.BindingResult.";
//                 HandlerMethod hanlderMethod = (HandlerMethod) handler;
//                 MethodParameter[] mps = hanlderMethod.getMethodParameters();
//                 if (mps != null) {
//                     for (MethodParameter mp : mps) {
//                         modelAndView.getModel().remove(mp.getParameterName());
//                         modelAndView.getModel().remove(StringUtil.toLowerCaseFirst(mp.getParameterType().getSimpleName()));
//                         // modelAndView.getModel().remove(springValidationPrefix + mp.getParameterName());
//                     }
//                 }
//                 if (json) {
//                     modelAndView.setView(jsonView);
//                     logStr += " ==pages: json";
//                 } else if (xml) {
//                     LinkedHashMap<String, Object> xmlModel = new LinkedHashMap<String, Object>();
//                     Set<Entry<String, Object>> set = modelAndView.getModel().entrySet();
//                     for (Entry<String, Object> entry : set) {
//                         if (!entry.getKey().startsWith(springValidationPrefix)) {
//                             xmlModel.put(entry.getKey(), entry.getValue());
//                         }
//                     }
//                     modelAndView.getModel().clear();
//                     modelAndView.getModel().put("Result", xmlModel);

//                     modelAndView.setView(xmlView);
//                     logStr += " ==pages: xml";
//                 }
//             } else {
//                 String pages = request.getRequestURI();
//                 pages = pages.substring(request.getContextPath().length(), pages.lastIndexOf('/'));
//                 if (StringUtils.isNotBlank(modelAndView.getViewName())) {
//                     if ("/".equals(modelAndView.getViewName().substring(0, 1))) {
//                         modelAndView.setViewName(pages + modelAndView.getViewName());
//                     } else {
//                         modelAndView.setViewName(pages + "/" + modelAndView.getViewName());
//                     }
//                 }
//                 logStr += " ==pages: " + modelAndView.getViewName();
//             }
//         }
//         log.info(logStr);

        return true;
    }


    @Override
    public void
    postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception e) throws Exception {
        if (e != null) {
            String errorText = "";
            errorText += "The exception class is: <br/>";
            errorText += e.getClass().getName() + "<br/>";
            errorText += "The exception message is: <br/>";
            errorText += e.getMessage() + "<br/>";
            errorText += "<br/>";
            errorText += "The exception stack trace is: <br/>";
            ByteArrayOutputStream ostr = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(ostr));
            errorText += ostr.toString().replaceAll("\\n", "<br/>");
            errorText += "<br/>";
            errorText += "<br/>";

            request.setAttribute("exText", errorText);
        }
    }
}