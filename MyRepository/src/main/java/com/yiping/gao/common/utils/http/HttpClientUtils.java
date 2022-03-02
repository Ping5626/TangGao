package com.yiping.gao.common.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 高一平
 * @Date: 2019/9/11 11:28
 * @Description:
 **/
@Slf4j
public class HttpClientUtils extends HttpUtils {

    private static final int SUCCESS = 200;

    /**
     * HTTP 请求相关配置
     *
     * @return
     */
    @Override
    protected RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .build();
    }

    /**
     * GET 请求
     *
     * @param url     请求路径
     * @param headers 请求头设置
     * @return 请求结果
     * @throws Exception 请求异常
     */
    @Override
    public String get(String url, List<Header> headers) throws Exception {
        HttpGet httpGet = getHttpGet(url, headers);
        return getResponse(httpGet);
    }

    /**
     * 构建 HTTP GET 请求对象
     *
     * @param url     请求路径
     * @param headers 请求头设置
     * @return
     */
    private HttpGet getHttpGet(String url, List<Header> headers) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getRequestConfig());
        // 设置请求头
        if (headers != null && !headers.isEmpty()) {
            headers.forEach(httpGet::setHeader);
        }
        return httpGet;
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
    @Override
    public String post(String url, Map<String, String> params, List<Header> headers) throws Exception {
        HttpPost httpPost = getHttpPost(url, headers);
        // 组装请求参数
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> list = new ArrayList<>(params.size());
            params.entrySet().forEach(entry -> {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            });
            httpPost.setEntity(new UrlEncodedFormEntity(list, StandardCharsets.UTF_8));
        }
        return getResponse(httpPost);
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
    @Override
    public String postByJson(String url, String param, List<Header> headers) throws Exception {
        HttpPost httpPost = getHttpPost(url, headers);
        httpPost.setHeader(new BasicHeader(HttpConstant.HEADER_NAME_CONTENT_TYPE, HttpConstant.CONTENT_TYPE_JSON));
        if (param != null && !param.isEmpty()) {
            httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8));
        }
        return getResponse(httpPost);
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
    @Override
    public String postByFile(String url, MultipartFile file, String fileParamName, Map<String, String> params, List<Header> headers) throws Exception {
        HttpPost httpPost = getHttpPost(url, headers);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(StandardCharsets.UTF_8);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody(fileParamName, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        return getResponse(httpPost);
    }

    /**
     * 构建 HTTP POST 请求对象
     *
     * @param url     请求路径
     * @param headers 请求头设置
     * @return
     */
    private HttpPost getHttpPost(String url, List<Header> headers) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
        if (headers != null && !headers.isEmpty()) {
            headers.forEach(httpPost::setHeader);
        }
        return httpPost;
    }

    /**
     * 请求响应，获取结果
     *
     * @param request 构建好的请求对象
     * @return 响应结果
     * @throws Exception
     */
    private String getResponse(HttpUriRequest request) throws Exception {
        String uri = request.getRequestLine().getUri();
        log.info("[HTTP]请求开始，请求地址：{}", uri);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                if (response.getStatusLine().getStatusCode() == SUCCESS) {
                    // 请求成功，解析结果
                    HttpEntity httpEntity = response.getEntity();
                    return EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
                } else {
                    log.error("[HTTP]请求失败！请求地址：{}，错误码：{}", uri, response.getStatusLine().getStatusCode());
                }
            } catch (Exception e) {
                log.error("[HTTP]请求失败！请求地址：{}，错误信息：{}", uri, e.getMessage());
            } finally {
                response.close();
            }
        } catch (Exception e) {
            log.error("[HTTP]连接失败！请求地址：{}，错误信息：{}", uri, e.getMessage());
        } finally {
            httpClient.close();
        }
        return null;
    }
}
