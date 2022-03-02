package com.yiping.gao.common.utils.http;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 高一平
 * @date 2021/4/14
 * @description
 */
public abstract class HttpUtils {

    public class HttpConstant {
        public static final String HEADER_NAME_CONTENT_TYPE = "Content-Type";

        public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String CONTENT_TYPE_XML = "application/xml";
        public static final String CONTENT_TYPE_MULTIPART_FILE = "multipart/form-data";
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
    public abstract String get(String url, List<Header> headers) throws Exception;

    /**
     * POST 请求
     *
     * @param url     请求路径
     * @param params  请求参数
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public String post(String url, Map<String, String> params) throws Exception {
        return post(url, params, null);
    }

    /**
     * POST 请求
     *
     * @param url     请求路径
     * @param params  请求参数
     * @param headers 请求头设置
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public abstract String post(String url, Map<String, String> params, List<Header> headers) throws Exception;

    /**
     * POST 请求——JSON
     *
     * @param url   请求路径
     * @param param 请求参数
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public String postByJson(String url, String param) throws Exception {
        return postByJson(url, param, null);
    }

    /**
     * POST 请求——JSON
     *
     * @param url     请求路径
     * @param param   请求参数
     * @param headers 请求头设置
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public abstract String postByJson(String url, String param, List<Header> headers) throws Exception;

    /**
     * POST 请求上传文件
     *
     * @param url           请求路径
     * @param file          上传文件
     * @param fileParamName 上传文件请求中的参数名称
     * @param params        其余请求参数
     * @return 请求结果
     * @throws Exception 请求异常
     */
    public String postByFile(String url, MultipartFile file, String fileParamName, Map<String, String> params) throws Exception {
        return postByFile(url, file, fileParamName, params, null);
    }

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
    public abstract String postByFile(String url, MultipartFile file, String fileParamName, Map<String, String> params, List<Header> headers) throws Exception;

}
