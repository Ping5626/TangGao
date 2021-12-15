package com.yiping.gao.common.utils;

import java.io.UnsupportedEncodingException;

/**
 * @author 高一平
 * @description 字符串工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 获取指定字符集的字符串
     *
     * @param string 字符串
     * @return 指定字符集的字符串
     * @throws UnsupportedEncodingException
     */
    public static String getStringByEncoding(String string) throws UnsupportedEncodingException {
        // 判断是否是系统设定的字符集
        if (!judgeEncoding(DEFAULT_CHARSET, string) == true) {
            // 不是，判断是什么字符集
            String encoding = getEncoding(string);
            // 重新使用指定字符集编码
            string = new String(string.getBytes(encoding), DEFAULT_CHARSET);
        }
        return string;
    }

    /**
     * 判断指定字符串的字符集
     *
     * @param string 字符串
     * @return 字符串的字符集
     * @throws UnsupportedEncodingException
     */
    public static String getEncoding(String string) throws UnsupportedEncodingException {
        String[] encodings = {"utf-8", "UTF-8", "iso8859-1", "ISO8859-1", "GB2312", "GBK"};
        for (String encoding : encodings) {
            if (judgeEncoding(encoding, string) == true) {
                return encoding;
            }
        }
        return null;
    }

    /**
     * 判断是否是对应字符集的字符串
     *
     * @param encoding 字符集
     * @param string   字符串
     * @return 是否是对应字符集的字符串
     * @throws UnsupportedEncodingException
     */
    public static boolean judgeEncoding(String encoding, String string) throws UnsupportedEncodingException {
        boolean flag;
        if (string.equals(new String(string.getBytes(encoding), encoding))) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStrings(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 string 中是否存在字符串 str
     *
     * @param str
     * @param string
     * @return
     */
    public static boolean inString(String str, String string) {
        boolean flag;
        if (string.indexOf(str) >= 0) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 移除字符串中的数字
     *
     * @param string
     * @return
     */
    public static String removeNumbers(String string) {
        string = string.replace("0", "")
                .replace("1", "")
                .replace("2", "")
                .replace("3", "")
                .replace("4", "")
                .replace("5", "")
                .replace("6", "")
                .replace("7", "")
                .replace("8", "")
                .replace("9", "");
        return string;
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     */
    public static String toCamelCase(String str) {
        if (str == null) {
            return null;
        }
        str = str.toLowerCase();
        StringBuilder builder = new StringBuilder(str.length());
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                builder.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCapitalizeCamelCase(" hello_world ") == "HelloWorld"
     */
    public static String toCapitalizeCamelCase(String str) {
        if (str == null) {
            return null;
        }
        str = toCamelCase(str);
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toUnderScoreCase(" helloWorld ") = "hello_world"
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            boolean nextUpperCase = true;
            if (i < (str.length() - 1)) {
                nextUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }
            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    builder.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
            builder.append(Character.toLowerCase(c));
        }
        return builder.toString();
    }

    /**
     * 转换为 JS 获取对象值，生成三目运算返回结果
     *
     * @param key 对象串
     *            例如：row.user.id
     *            返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String key) {
        StringBuilder keyResult = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        String[] keys = split(key, ".");
        for (int i = 0; i < keys.length; i++) {
            temp.append("." + keys[i]);
            keyResult.append("!" + (temp.substring(1)) + "?'':");
        }
        keyResult.append(temp.substring(1));
        return keyResult.toString();
    }

    /**
     * 检查指定的字符串是否为空
     *
     * @param str 字符串
     * @return 指定的字符串是否为空
     * <ul>
     *      <li>isEmpty(null) = true</li>
     *      <li>isEmpty("") = true</li>
     *      <li>isEmpty("   ") = true</li>
     *      <li>isEmpty("abc") = false</li>
     * </ul>
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

}
