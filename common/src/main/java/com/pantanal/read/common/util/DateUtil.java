package com.pantanal.read.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    private static SimpleDateFormat monthFormat = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String[] formmat = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy", };

    /**
     *
     * @param dateString
     * @return
     */
    public static Date toDate(String dateString) {
        try {
            if (dateString == null || dateString.length() == 0) {
                return null;
            }
            return DateUtils.parseDate(dateString, formmat);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     *
     * @param dateString
     * @param formatter
     * @return
     */
    public static Date toDate(String dateString, String formatter) {
        try {
            if (dateString == null || dateString.length() == 0) {
                return null;
            }
            return DateUtils.parseDate(dateString, formatter);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     *
     * @param date
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        } else {
            return DateFormatUtils.format(date, pattern);
        }
    }

    /**
     *
     * @param date
     * @return yyyyMM 格式的值
     */
    public static String formatMonth(Date date) {
        if (date == null) {
            return "";
        }
        try {
            return monthFormat.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        try {
            return dateTimeFormat.format(date);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     *
     * @param date
     * @return
     */
    public static Date toDate(java.sql.Date date) {
        if (date == null) {
            return null;
        } else {
            return new Date(date.getTime());
        }
    }

    /**
     *
     * @param time
     * @return
     */
    public static Date toDate(java.sql.Timestamp time) {
        if (time == null) {
            return null;
        } else {
            return new Date(time.getTime());
        }
    }



    /**
     * 把时间转换为另外一个时区的时间
     *
     * @param date
     * @param timezone
     * @return
     */
    public static Date toOtherTimeZone(Date date, TimeZone timezone) {
        try {
            if (timezone != null) {
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                simpleFormat.setTimeZone(timezone);
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(simpleFormat.format(date));
            }
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     *
     * @param timezoneID
     * @return
     */
    public static TimeZone getTimeZone(String timezoneID) {
        if (StringUtils.isBlank(timezoneID)) {
            return TimeZone.getDefault();
        } else {
            return TimeZone.getTimeZone(timezoneID);
        }
    }

    /**
     * 返回2个日期相差几个自然月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int subMonth(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) {
            return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
        } else {
            return 12 * (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
        }
    }

    /**
     * 获取当月最后一天23：59：59
     *
     * @param date
     * @return
     */
    public static Date getLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setLenient(false);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     *
     * @param c1
     * @param c2
     * @return
     */
    public static boolean isSameHour(Calendar c1, Calendar c2) {
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH) && c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameHour(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        return isSameHour(c1, c2);
    }

    /**
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param date1
     * @param c2
     * @return
     */
    public static boolean isSameHour(Date date1, Calendar c2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);

        return isSameHour(c1, c2);
    }

    /**
     *
     * @param year
     * @param month
     *          一年中的第一个月是 JANUARY，它为 0
     * @param day
     *          一个月中第一天的值为 1
     * @param hours
     *          0-23
     * @param minute
     * @param second
     * @return
     */
    public static Date setValue(int year, int month, int day, int hours, int minute, int second) {
        // getInstance() returns a new object, so this method is thread safe.
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        return c.getTime();
    }

    /**
     *
     * @param date
     * @param day
     * @param hours
     * @param minute
     * @param second
     * @return
     */
    public static Date setValue(Date date, int day, int hours, int minute, int second) {
        // getInstance() returns a new object, so this method is thread safe.
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        return c.getTime();
    }

    /**
     *
     * @param date
     * @param hours
     * @param minute
     * @param second
     * @return
     */
    public static Date setValue(Date date, int hours, int minute, int second) {
        // getInstance() returns a new object, so this method is thread safe.
        final Calendar c = Calendar.getInstance();
        c.setLenient(false);
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        return c.getTime();
    }

    public static void main(String[] agrs) {
        Date date1 = DateUtil.toDate("2015-01-12");
        Date date2 = DateUtil.toDate("2014-11-01");
        System.out.println(subMonth(date1, date2));
        System.out.println(DateUtil.toOtherTimeZone(new Date(), TimeZone.getTimeZone("GMT+1")));
        System.out.println(getLastDate(new Date()));

        Date date = setValue(2018, 10, 8, 16, 39, 20);
        System.out.println(date);
    }
}
