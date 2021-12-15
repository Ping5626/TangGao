package com.jvm.http;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: 高一平
 * @Date: 2021/4/19
 * @Description:
 **/
@Slf4j
@Component
public abstract class HttpUtils {

    public static final String DEFAULT_CHARSET = "UTF-8";
    protected static final int SUCCESS = 200;

    @Getter
    @AllArgsConstructor
    public enum HttpHeaderEnums {
        /**
         * HTTP 请求头相关参数
         */
        DEFAULT_CONTENT("Content-Type", "application/x-www-form-urlencoded"),
        JSON_CONTENT("Content-Type", "application/json"),
        MULTIPART_FILE_CONTENT("Content-Type", "multipart/form-data"),
        ;

        private String headerName;
        private String headerValue;
    }

    /**
     * 转换参数
     *
     * @param params
     * @return
     */
    public String convertParam(Map<String, String> params) {
        StringBuilder paramBuilder = new StringBuilder();
        if (params != null && params.size() > 0) {
            params.entrySet().forEach(entry -> {
                paramBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            });
        }
        String param = paramBuilder.toString();
        if (param.endsWith("&")) {
            param = param.substring(0, param.length() - 1);
        }
        return param;
    }

    /**
     * HTTP 请求相关配置
     *
     * @return
     */
    protected abstract RequestConfig getRequestConfig();

    /**
     * GET 请求
     *
     * @param url 请求路径
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public String get(String url) throws Exception {
        return get(url, null);
    }

    /**
     * GET 请求
     *
     * @param url     请求路径
     * @param headers 请求头设置
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public abstract String get(String url, List<HttpHeaderEnums> headers) throws Exception;

    /**
     * POST 请求
     *
     * @param url   请求路径
     * @param param 请求参数
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public String post(String url, String param) throws Exception {
        return post(url, param, null);
    }

    /**
     * POST 请求——JSON
     *
     * @param url   请求路径
     * @param param 请求参数
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public String postByJson(String url, JSON param) throws Exception {
        return post(url, JSON.toJSONString(param), Arrays.asList(HttpHeaderEnums.JSON_CONTENT));
    }

    /**
     * POST 请求
     *
     * @param url     请求路径
     * @param param   请求参数
     * @param headers 请求头设置
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public abstract String post(String url, String param, List<HttpHeaderEnums> headers) throws Exception;

    /**
     * POST 请求上传文件
     *
     * @param url           请求路径
     * @param file          上传文件
     * @param fileParamName 上传文件请求中的参数名称
     * @param params        其余请求参数
     * @param headers       请求头设置
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public abstract String postByFile(String url, MultipartFile file, String fileParamName, Map<String, String> params, List<HttpHeaderEnums> headers) throws Exception;

}
