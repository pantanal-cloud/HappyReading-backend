package com.pantanal.read.server.ui.converter;

import com.pantanal.read.common.util.NumberUtil;
import org.springframework.core.convert.converter.Converter;


/**
 * @author gudong
 *
 */
public class DoubleConverter implements Converter<String, Double> {

    @Override
    public Double convert(String arg0) {
        return NumberUtil.toDouble(arg0);
    }
}