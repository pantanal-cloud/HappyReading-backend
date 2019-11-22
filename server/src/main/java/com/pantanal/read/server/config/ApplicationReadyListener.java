package com.pantanal.read.server.config;

import com.pantanal.read.server.common.Constant;
import com.pantanal.read.server.common.MyResourceBundleModel;
import com.pantanal.read.server.common.SysCfg;
import com.pantanal.read.common.util.DateUtil;
import com.pantanal.read.server.ui.converter.DateConverter;
import com.pantanal.read.server.ui.converter.DoubleConverter;
import com.pantanal.read.server.ui.converter.FloatConverter;
import com.pantanal.read.server.ui.converter.IntegerConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jagregory.shiro.freemarker.ShiroTags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
@Component
@Slf4j
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Resource
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // shiro tag
        freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
        //SecurityUtils.setSecurityManager(securityManager);

        GenericWebApplicationContext context = (GenericWebApplicationContext) event.getApplicationContext();
        Constant.CONTEXT_PATH = context.getServletContext().getContextPath();
        context.getServletContext().setAttribute("base", context.getServletContext().getContextPath());

        context.getServletContext().setAttribute("syscfg", SysCfg.get());

        MyResourceBundleModel rsbm = MyResourceBundleModel.get();
        context.getServletContext().setAttribute("i18n", rsbm);

        // add converter
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
        genericConversionService.addConverter(new DateConverter());
        genericConversionService.addConverter(new IntegerConverter());
        genericConversionService.addConverter(new DoubleConverter());
        genericConversionService.addConverter(new FloatConverter());

        // add RequestBody jackson Serializer & Deserializer
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
            public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException,
                    JsonProcessingException {
                jsonGenerator.writeString(DateUtil.formatDateTime(date));
            }
        });
        simpleModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                return DateUtil.toDate(jsonParser.getText());
            }
        });
        mappingJackson2HttpMessageConverter.getObjectMapper().registerModule(simpleModule);


        log.info("======初始化 DONE! base:" + Constant.CONTEXT_PATH + "=====");
    }
}