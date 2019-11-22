package com.pantanal.read.server.common;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.ResourceBundleModel;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author gudong
 */
public class MyResourceBundleModel extends ResourceBundleModel {
    private static MyResourceBundleModel instance = null;

    /**
     * @param locale
     */
    private MyResourceBundleModel(Locale locale) {
        super(ResourceBundle.getBundle("LocaleString", locale), new BeansWrapper());
    }

    /**
     * @return
     */
    public static MyResourceBundleModel get() {
        if (instance == null) {
            instance = new MyResourceBundleModel(new Locale("zh"));
        }
        return instance;
    }

    public String msg(String key) {
        return format(key, null);
    }

    public String msg(String key, String param1) {
        return format(key, new String[]{param1});
    }

    public String msg(String key, String param1, String param2) {
        return format(key, new String[]{param1, param2});
    }

    public String msg(String key, String param1, String param2, String param3) {
        return format(key, new String[]{param1, param2, param3});
    }

    public String msg(String key, String param1, String param2, String param3, String param4) {
        return format(key, new String[]{param1, param2, param3, param4});
    }

    public String msg(String key, String param1, String param2, String param3, String param4, String param5) {
        return format(key, new String[]{param1, param2, param3, param4, param5});
    }

    public String msg(String key, String param1, String param2, String param3, String param4, String param5, String param6) {
        return format(key, new String[]{param1, param2, param3, param4, param5, param6});
    }

    public String msg(String key, String param1, String param2, String param3, String param4, String param5, String param6, String param7) {
        return format(key, new String[]{param1, param2, param3, param4, param5, param6, param7});
    }

    public String
    msg(String key, String param1, String param2, String param3, String param4, String param5, String param6, String param7, String param8) {
        return format(key, new String[]{param1, param2, param3, param4, param5, param6, param7, param8});
    }

    public String msg(String key,
                      String param1,
                      String param2,
                      String param3,
                      String param4,
                      String param5,
                      String param6,
                      String param7,
                      String param8,
                      String param9) {
        return format(key, new String[]{param1, param2, param3, param4, param5, param6, param7, param8, param9});
    }

    @Override
    public String format(String key, Object[] params) {
        try {
            return super.format(key, params);
        } catch (Exception e) {
            return key;
        }
    }
}