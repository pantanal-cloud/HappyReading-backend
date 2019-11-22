package com.pantanal.read.server.ui.converter;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

/**
 * @author gudong
 *
 */
public class DateConverter implements Converter<String, Date> {
    // 支持转换的日期格式
    private static final String[] ACCEPT_DATE_FORMATS = { "yyyy-MM-dd", "MM/dd/yyyy", "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss" };

    @Override
    public Date convert(String arg0) {
        try {
            return DateUtils.parseDate(arg0, ACCEPT_DATE_FORMATS);
        } catch (ParseException e) {
            return null;
        }
    }
}