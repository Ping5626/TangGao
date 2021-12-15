package com.yiping.gao.thread;

import com.yiping.gao.common.utils.IdUtil;
import com.yiping.gao.pojo.entity.User;

import java.util.HashMap;

/**
 * @author 高一平
 * @date 2021/5/27
 * @description {@link CommonRunnable} 相关工具类
 */
public class ThreadUtils {

    /**
     * <String, String> 为 <KEY, 用户ID>
     */
    private static final HashMap<String, User> USER_KEY_MAP = new HashMap<>();
    /**
     * <String, String> 为 <用户ID, KEY>
     */
    private static final HashMap<Long, String> USER_ID_MAP = new HashMap<>();
    /**
     * 分隔符
     */
    private static final String TAG = "::";

    /**
     * 保存用户并返回线程后缀名
     * 线程开始运行时，需现将该后缀名添加至线程名后面
     * 应注意线程名最长为 15 位，超过此长度线程名左端超出部分会被丢弃
     *
     * @param loginUser 用户
     * @return 线程后缀名
     */
    public static String setThreadUser(User loginUser) {
        String key = USER_ID_MAP.get(loginUser.getUserId());
        if (key != null && !key.isEmpty()) {
            USER_KEY_MAP.put(key, loginUser);
            return key;
        } else {
            key = IdUtil.getRandomCode(4);
            if (USER_KEY_MAP.get(key) == null) {
                USER_KEY_MAP.put(key, loginUser);
                USER_ID_MAP.put(loginUser.getUserId(), key);
                return TAG + key;
            } else {
                return setThreadUser(loginUser);
            }
        }
    }

    /**
     * 获取线程对应的用户
     *
     * @param thread 线程
     * @return 线程对应的用户
     */
    public static User getUser(Thread thread) {
        String[] names = thread.getName().split(TAG);
        if (names.length > 1) {
            return USER_KEY_MAP.get(names[names.length - 1]);
        }
        return null;
    }

}
