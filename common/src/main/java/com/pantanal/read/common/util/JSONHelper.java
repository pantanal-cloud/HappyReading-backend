package com.pantanal.read.common.util;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gudong
 */
public class JSONHelper {

    /**
     * @return
     */
    public static JsonConfig getDefaultConfig() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setIgnoreTransientFields(true);
        jsonConfig.registerDefaultValueProcessor(Boolean.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return null;
            }
        });
        jsonConfig.registerDefaultValueProcessor(Double.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return null;
            }
        });
        jsonConfig.registerDefaultValueProcessor(Long.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return null;
            }
        });
        jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return null;
            }
        });
        jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return null;
            }
        });
        jsonConfig.registerDefaultValueProcessor(Boolean.class, new DefaultValueProcessor() {
            public Object getDefaultValue(Class type) {
                return null;
            }
        });
        jsonConfig.registerJsonValueProcessor(Double.class, new JsonValueProcessor() {
            @Override
            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                if (value != null) {
                    return new DecimalFormat("0.0#######").format((Double) value);
                } else {
                    return null;
                }
            }

            @Override
            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                if (value != null) {
                    return new DecimalFormat("0.0#######").format((Double) value);
                } else {
                    return null;
                }
            }
        });
        return jsonConfig;
    }

    public static Boolean getBoolean(JSONObject json, String key) {
        try {
            return json.getBoolean(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer getInteger(JSONObject json, String key) {
        try {
            return json.getInt(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getLong(JSONObject json, String key) {
        try {
            return json.getLong(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static Double getDouble(JSONObject json, String key) {
        try {
            return json.getDouble(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getString(JSONObject json, String key) {
        try {
            return json.getString(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDate(JSONObject json, String key) {
        try {
            return DateUtil.toDate(json.getString(key), "yyyy-MM-dd");
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDateTime(JSONObject json, String key) {
        try {
            return DateUtil.toDate(json.getString(key), "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONArray getJSONArray(JSONObject json, String key) {
        try {
            return json.getJSONArray(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return
     */
    public static JsonValueProcessor getDateJsonValueProcessor() {
        return new JsonValueProcessor() {
            private String format = "yyyy-MM-dd HH:mm:ss";

            public Object processArrayValue(Object value, JsonConfig jsonConfig) {

                String[] obj = {};
                if (value instanceof Date[]) {
                    SimpleDateFormat sf = new SimpleDateFormat(format);
                    Date[] dates = (Date[]) value;
                    obj = new String[dates.length];
                    for (int i = 0; i < dates.length; i++) {
                        obj[i] = sf.format(dates[i]);
                    }
                }
                return obj;
            }

            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {

                if (value instanceof Date) {
                    String str = new SimpleDateFormat(format).format((Date) value);
                    return str;
                }
                return value;
            }
        };
    }

    public static  String toString(Object json) {
        return toString(json, ",");
    }

    public static String toString(Object json, String separator) {
        if (json == null) {
            return "";
        } else if (json instanceof JSONArray) {
            return StringUtils.join((JSONArray) json, separator);
        } else {
            return json.toString();
        }
    }
}