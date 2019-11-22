package com.pantanal.read.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

    public static enum Type {
        UPPERCASE,              //全部大写
        LOWERCASE,              //全部小写
        FIRSTUPPER            //首字母大写
    }


    /**
     * 转换全部大写
     *
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是YHY
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinYinUppercase(String str) {
        return toPinYin(str, "", Type.UPPERCASE);
    }

    /**
     * 转换全部大写
     *
     * @param str   字符串
     * @param spera 转换字母间隔加的字符串,如果不需要为""
     * @return str为颐和园 ,spera为** return获取到的是Y**H**Y
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinYinUppercase(String str, String spera) {
        return toPinYin(str, spera, Type.UPPERCASE);
    }

    /**
     * 转换全部小写
     *
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是yhy
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinYinLowercase(String str) {
        return toPinYin(str, "", Type.LOWERCASE);
    }

    /**
     * 转换全部小写
     *
     * @param str   字符串
     * @param spera 转换字母间隔加的字符串,如果不需要为""
     * @return str为颐和园 ,spera为** return获取到的是y**h**y
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinYinLowercase(String str, String spera) {
        return toPinYin(str, spera, Type.LOWERCASE);
    }

    /**
     * 获取拼音首字母(大写)
     *
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是Y
     * @throws BadHanyuPinyinOutputFormatCombination 异常信息
     */
    public static String toPinYinUppercaseInitials(String str) {
        String initials = null;
        String py = toPinYinUppercase(str);
        if (py.length() > 1) {
            initials = py.substring(0, 1);
        }
        if (py.length() <= 1) {
            initials = py;
        }
        return initials.trim();
    }

    /**
     * 获取拼音首字母(小写)
     *
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是y
     * @throws BadHanyuPinyinOutputFormatCombination 异常信息
     */
    public static String toPinYinLowercaseInitials(String str) {
        String initials = null;
        String py = toPinYinLowercase(str);
        if (py.length() > 1) {
            initials = py.substring(0, 1);
        }
        if (py.length() <= 1) {
            initials = py;
        }
        return initials.trim();
    }

    /**
     * 将str转换成拼音，如果不是汉字或者没有对应的拼音，则不作转换
     *
     * @param str   字符串
     * @param spera 默认,可为""
     * @param type  转换格式
     * @return 按照转换格式转换成字符串
     * @throws BadHanyuPinyinOutputFormatCombination 异常信息
     */
    public static String toPinYin(String str, String spera, Type type) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }

        String py = "";
        String temp = "";
        String[] t;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((int) c <= 128) {
                py += c;
            } else {
                t = PinyinHelper.toHanyuPinyinStringArray(c);
                if (t == null) {
                    py += c;
                } else {
                    temp = t[0];
                    if (type == Type.FIRSTUPPER) {
                        temp = temp.toUpperCase().charAt(0) + temp.substring(1);
                    } else if (type == Type.UPPERCASE) {
                        temp = temp.toUpperCase();
                    } else if (type == Type.LOWERCASE) {
                        temp = temp.toLowerCase();
                    }
                    if (temp.length() >= 1) {
                        temp = temp.substring(0, temp.length() - 1);
                    }
                    py += temp + (i == str.length() - 1 ? "" : spera);
                }
            }
        }
        py = py.replaceAll("u:","v");
        return py.trim();
    }

    public static void main(String[] args) {
        //System.out.println(toPinYinUppercase("当\\当-文:学-中*国/美?国\"英<国>韩|国"));
        System.out.println(toPinYinLowercase("当当-童书-11-14岁-益智游戏"));
    }
}