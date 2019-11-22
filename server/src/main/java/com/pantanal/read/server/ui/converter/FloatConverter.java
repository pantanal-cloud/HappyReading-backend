package com.pantanal.read.server.ui.converter;

import com.pantanal.read.common.util.NumberUtil;
import org.springframework.core.convert.converter.Converter;


/**
 * @author gudong
 *
 */
public class FloatConverter implements Converter<String, Float> {

    @Override
    public Float convert(String arg0) {
        return NumberUtil.toFloat(arg0);
    }
}