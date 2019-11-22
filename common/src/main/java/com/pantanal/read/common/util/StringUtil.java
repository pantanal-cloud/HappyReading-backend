package com.pantanal.read.common.util;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @author gudong
 */
public class StringUtil {
    /**
     * 删除作为文件名的非法字符
     *
     * @param s
     * @return
     */
    public static String removeInvalidChars4DirName(String s) {
        String result = s == null ? "" : s.replaceAll("[^a-zA-Z0-9\\-]", "");
        result = result.trim();
        if (result.length() > 254) {
            result = result.substring(0, 254);
        }
        return result;
    }

    /**
     * 字符串 转成boolean 数组
     *
     * @param s     like:true,1,false,0,true
     * @param split like:,
     * @return [true, true, false, false, true]
     */
    public static boolean[] split2Boolean(String s, String split) {
        String[] strArray = StringUtils.split(s, split);
        boolean[] result = new boolean[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            result[i] = BooleanUtils.toBoolean(strArray[i]);
        }
        return result;
    }

    /**
     * Upper substring assigned length.
     *
     * @param s
     * @param start
     * @param end
     * @return
     */
    public static String toUpperCase(String s, int start, int end) {
        int length = s.length();
        if (length >= end) {
            s = s.substring(0, start) + s.substring(start, end).toUpperCase() + s.substring(end, length);
        }
        return s;
    }

    /**
     * Lower substring assigned length.
     *
     * @param s
     * @param start
     * @param end
     * @return
     */
    public static String toLowerCase(String s, int start, int end) {
        int length = s.length();
        if (length >= end) {
            s = s.substring(0, start) + s.substring(start, end).toLowerCase() + s.substring(end, length);
        }
        return s;
    }

    /**
     * Lower the first char.
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirst(String s) {
        return toLowerCase(StringUtils.defaultString(s), 0, 1);
    }

    /**
     * Upper the first char.
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirst(String s) {
        return toUpperCase(StringUtils.defaultString(s), 0, 1);
    }

    /**
     * 转化成数据库表名，如：LotteryOrder转成LOTTERY_ORDER
     *
     * @param name
     * @return
     */
    public static String toDBTableName(String name) {
        return toDBColumnName(name);
    }

    /**
     * 转化成数据库列表，如：isCurrentPeriod转成IS_CURRENT_PERIOD
     *
     * @param fieldName
     * @return
     */
    public static String toDBColumnName(String fieldName) {
        if (StringUtils.isBlank(fieldName))
            return "";
        StringBuffer columnNameBuffer = new StringBuffer();
        char[] charArray = StringUtils.defaultString(fieldName).toCharArray();
        columnNameBuffer.append(Character.toUpperCase(charArray[0]));
        for (int i = 1; i < charArray.length; i++) {
            if (Character.isUpperCase(charArray[i])) {
                columnNameBuffer.append("_");
            }
            columnNameBuffer.append(Character.toUpperCase(charArray[i]));
        }
        return columnNameBuffer.toString();
    }

    /**
     * @param o
     * @return
     */
    public static String toString(Object o) {
        if (o == null) {
            return "";
        } else {
            return o.toString();
        }
    }

    /**
     * json字符串的格式化，友好格式
     *
     * @param json           json串
     * @param fillStringUnit 填充字符，比如四个空格
     * @return
     */
    public static String formatJson(String json, String fillStringUnit) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }

        int fixedLenth = 0;
        ArrayList<String> tokenList = new ArrayList<String>();
        {
            String jsonTemp = json;
            // 预读取
            while (jsonTemp.length() > 0) {
                String token = getToken(jsonTemp);
                jsonTemp = jsonTemp.substring(token.length());
                token = token.trim();
                tokenList.add(token);
            }
        }

        for (int i = 0; i < tokenList.size(); i++) {
            String token = tokenList.get(i);
            int length = token.getBytes().length;
            if (length > fixedLenth && i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
                fixedLenth = length;
            }
        }

        StringBuilder buf = new StringBuilder();
        int count = 0;
        for (int i = 0; i < tokenList.size(); i++) {

            String token = tokenList.get(i);

            if (token.equals(",")) {
                buf.append(token);
                doFill(buf, count, fillStringUnit);
                continue;
            }
            if (token.equals(":")) {
                buf.append(" ").append(token).append(" ");
                continue;
            }
            if (token.equals("{")) {
                String nextToken = tokenList.get(i + 1);
                if (nextToken.equals("}")) {
                    i++;
                    buf.append("{ }");
                } else {
                    count++;
                    buf.append(token);
                    doFill(buf, count, fillStringUnit);
                }
                continue;
            }
            if (token.equals("}")) {
                count--;
                doFill(buf, count, fillStringUnit);
                buf.append(token);
                continue;
            }
            if (token.equals("[")) {
                String nextToken = tokenList.get(i + 1);
                if (nextToken.equals("]")) {
                    i++;
                    buf.append("[ ]");
                } else {
                    count++;
                    buf.append(token);
                    doFill(buf, count, fillStringUnit);
                }
                continue;
            }
            if (token.equals("]")) {
                count--;
                doFill(buf, count, fillStringUnit);
                buf.append(token);
                continue;
            }

            buf.append(token);
            // 左对齐
            if (i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
                int fillLength = fixedLenth - token.getBytes().length;
                if (fillLength > 0) {
                    for (int j = 0; j < fillLength; j++) {
                        buf.append(" ");
                    }
                }
            }
        }
        return buf.toString();
    }

    private static String getToken(String json) {
        StringBuilder buf = new StringBuilder();
        boolean isInYinHao = false;
        while (json.length() > 0) {
            String token = json.substring(0, 1);
            json = json.substring(1);

            if (!isInYinHao
                    && (token.equals(":") || token.equals("{") || token.equals("}") || token.equals("[") || token.equals("]") || token.equals(","))) {
                if (buf.toString().trim().length() == 0) {
                    buf.append(token);
                }

                break;
            }

            if (token.equals("\\")) {
                buf.append(token);
                buf.append(json.substring(0, 1));
                json = json.substring(1);
                continue;
            }
            if (token.equals("\"")) {
                buf.append(token);
                if (isInYinHao) {
                    break;
                } else {
                    isInYinHao = true;
                    continue;
                }
            }
            buf.append(token);
        }
        return buf.toString();
    }

    private static void doFill(StringBuilder buf, int count, String fillStringUnit) {
        buf.append("\n");
        for (int i = 0; i < count; i++) {
            buf.append(fillStringUnit);
        }
    }

    public static void main(String[] args) {
        String s = "a、，''；。？！：“‘￥.+d（）\\c|/:*?<>\"”bguo>!@#$%^&*()tu-jingj051fdi-jingjixue-zibenzhuyishehuishengchanfangshi-longduanzibenzhuyi-diguozhuyi-hougongyeshehui”lun";
        System.out.println(removeInvalidChars4DirName(s));

        String str = "{\"content\":\"'this,is the msg content.'\",\"tousers\":\"user1|user2\",\"msgtype\":\"texturl\",\"appkey\":\"test\",\"domain\":\"test\","
                + "\"system\":{\"wechat\":[{\"safe\":\"1\"}]},\"texturl\":{\"urltype\":\"0\",\"user1\":{\"spStatus\":\"user01\",\"workid\":\"work01\"},\"user2\":{\"spStatus\":\"user02\",\"workid\":\"work02\"}}}";
        System.out.println(formatJson(str, "  "));
    }
}
