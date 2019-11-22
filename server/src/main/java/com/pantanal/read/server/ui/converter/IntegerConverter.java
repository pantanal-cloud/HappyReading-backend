package com.pantanal.read.server.ui.converter;

import com.pantanal.read.common.util.NumberUtil;
import org.springframework.core.convert.converter.Converter;


/**
 * @author gudong
 *
 */
public class IntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String arg0) {
        return NumberUtil.toInteger(arg0);
    }
}