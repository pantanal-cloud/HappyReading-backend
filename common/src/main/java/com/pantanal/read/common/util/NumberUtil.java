package com.pantanal.read.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class NumberUtil {
    public static int defaultValue(Integer i) {
        return defaultValue(i, 0);
    }

    public static int defaultValue(Integer i, int defaultValue) {
        if (i == null) {
            return defaultValue;
        } else {
            return i;
        }
    }

    public static boolean defaultValue(Boolean b) {
        if (b == null) {
            return false;
        } else {
            return b;
        }
    }

    public static double defaultValue(Double i) {
        return defaultValue(i, 0.0);
    }

    public static double defaultValue(Double i, double defaultValue) {
        if (i == null) {
            return defaultValue;
        } else {
            return i;
        }
    }

    public static long defaultValue(Long l) {
        return defaultValue(l, 0);
    }

    public static long defaultValue(Long l, long defaultValue) {
        if (l == null) {
            return defaultValue;
        } else {
            return l;
        }
    }

    /**
     *
     * @param doubleValue
     * @return
     */
    public static Double toDouble(String doubleValue) {
        if (doubleValue == null || doubleValue.trim().length() == 0) {
            return null;
        } else {
            try {
                return Double.valueOf(doubleValue);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     *
     * @param doubleValue
     * @return
     */
    public static Float toFloat(String doubleValue) {
        if (doubleValue == null || doubleValue.trim().length() == 0) {
            return null;
        } else {
            try {
                return Float.valueOf(doubleValue);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     *
     * @param intValue
     * @return
     */
    public static Integer toInteger(String intValue) {
        if (intValue == null || intValue.trim().length() == 0) {
            return null;
        } else if (StringUtils.startsWithIgnoreCase(intValue, "0x")) {
            try {
                return Integer.parseInt(intValue.substring(2), 16);
            } catch (Exception e) {
                return null;
            }
        } else {
            try {
                return Integer.valueOf(intValue);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     *
     * @param doubleValue
     * @return
     */
    public static String toString(Double doubleValue) {
        if (doubleValue == null) {
            return null;
        } else {
            return doubleValue.toString();
        }
    }

    /**
     *
     * @param intValue
     * @return
     */
    public static String toString(Integer intValue) {
        if (intValue == null) {
            return null;
        } else {
            return intValue.toString();
        }
    }

    /**
     *
     * @param longValue
     * @return
     */
    public static String toString(Long longValue) {
        if (longValue == null) {
            return null;
        } else {
            return longValue.toString();
        }
    }

    /**
     *
     * @param date
     * @return
     */
    public static Date defaultValue(Date date, Date defaultValue) {
        if (date == null) {
            return defaultValue;
        } else {
            return date;
        }
    }

    /**
     * 四舍五入截取
     *
     * @param v
     * @param scale
     * @return
     */
    public static double scaleHalfUp(double v, int scale) {
        return new BigDecimal(v).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 截短
     *
     * @param v
     * @param scale
     * @return
     */
    public static double scale(double v, int scale) {
        return new BigDecimal(v).setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     *
     * @param v
     * @param scale
     * @return
     */
    public static String scaleToStr(double v, int scale) {
        return new java.text.DecimalFormat("0." + StringUtils.repeat('#', scale)).format(v);
    }

    /**
     *
     * @param value
     * @return 0x0000
     */
    public static String intToHexStr(int value) {
        String strHex = Integer.toHexString(value);
        if (strHex.length() == 1) {
            strHex = "0x000" + strHex;
        } else if (strHex.length() == 2) {
            strHex = "0x00" + strHex;
        } else if (strHex.length() == 3) {
            strHex = "0x0" + strHex;
        } else {
            strHex = "0x" + strHex;
        }
        return strHex;
    }

    /**
     *
     * @param dataList
     * @return
     */
    public static String formatGps(List<Integer> dataList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i) < 10) {
                sb.append(0).append(dataList.get(i));
            } else {
                sb.append(dataList.get(i));
            }

            if (i == 0) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param intValue
     * @return
     */
    public static Long toLong(String intValue) {
        if (intValue == null || intValue.trim().length() == 0) {
            return null;
        } else if (StringUtils.startsWithIgnoreCase(intValue, "0x")) {
            try {
                return Long.parseLong(intValue.substring(2), 16);
            } catch (Exception e) {
                return null;
            }
        } else {
            try {
                return Long.valueOf(intValue);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(scale(10000.000000 / 2, 15));
        System.out.println(scaleToStr(10000.000000 / 2, 15));
    }
}
