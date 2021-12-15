package com.yiping.gao.common.utils;

import java.util.Random;

/**
 * @author 高一平
 * @date 2021/2/19
 * @description ID相关生成规则
 */
public class IdUtil {

    /**
     * 获取指定位数的随机数
     *
     * @param length 随机数位数
     * @return 随机数
     */
    public static String getRandomNum(int length) {
        String source = "0123456789";
        return getRandomCode(length, source);
    }

    /**
     * 获取指定位数的随机字符
     *
     * @param length 随机字符位数
     * @return 随机字符
     */
    public static String getRandomCode(int length) {
        String source = "0123456789ABCDEFGHIGKLMNOPQRSTUVWXWZ";
        return getRandomCode(length, source);
    }

    /**
     * 获取指定源中指定位数的随机字符
     *
     * @param length 指定位数
     * @param source 指定源
     * @return 随机字符
     */
    private static String getRandomCode(int length, String source) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < length; j++) {
            builder.append(source.charAt(random.nextInt(source.length())));
        }
        return builder.toString();
    }

}
