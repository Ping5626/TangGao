package com.yiping.gao.service.sys;

import com.yiping.gao.pojo.Result;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: 高一平
 * @Date: 2019/9/2 14:42
 * @Description:
 **/
public class ServiceHandle {

    /**
     * @param <T>
     * @param method  请求方法
     * @param param   请求参数
     * @param service 请求Service
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T extends AbstractService> Result invoke(String method, Object param, Class<T> service)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method serviceMethod = service.getMethod(method, Object.class);
        Result result = (Result) serviceMethod.invoke(param, "");
        return result;
    }

}
