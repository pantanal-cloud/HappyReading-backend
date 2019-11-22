package com.pantanal.read.server.config;

import com.pantanal.read.server.common.SysCfg;
import com.pantanal.read.server.ui.interceptor.ActionFilter;
import com.pantanal.read.server.ui.interceptor.ActionInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
  /**
   * 拦截器
   */
  @Bean
  public ActionInterceptor myActionInterceptor() {
    return new ActionInterceptor();
  }


  /**
   * 添加Interceptor 重写添加拦截器方法并添加配置拦截器<br>
   * 一个*：只匹配字符，不匹配路径（/） <br>
   * 两个**：匹配字符，和路径（/） <br>
   * <p>
   * The mapping matches URLs using the following rules: <br>
   * ? matches one character <br>
   * * matches zero or more characters <br>
   * ** matches zero or more directories in a path <br>
   * {spring:[a-z]+} matches the regexp [a-z]+ as a path variable named "spring"<br>
   *
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(myActionInterceptor()).addPathPatterns("/**/*.do");
  }

  /**
   * 配置固定页面
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("redirect:/admin/index.do");
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

  /**
   * 添加Filter
   *
   * @return
   */
  @Bean
  public FilterRegistrationBean actionFilter() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(new ActionFilter());
    registrationBean.addUrlPatterns("*.do");
    return registrationBean;
  }

  @Bean
  @ConfigurationProperties(prefix = "server.syscfg")
  public SysCfg sysCfg() {
    return SysCfg.get();
  }

}
