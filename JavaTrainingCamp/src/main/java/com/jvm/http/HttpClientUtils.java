package com.jvm.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author 高一平
 * @date 2021/4/19
 * @description
 */
@Slf4j
@Component
public class HttpClientUtils extends HttpUtils {

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
    public String get(String url, List<HttpUtils.HttpHeaderEnums> headers) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(getRequestConfig());
        // 设置请求头
        if (headers != null && headers.size() > 0) {
            headers.forEach(header -> {
                httpGet.setHeader(header.getHeaderName(), header.getHeaderValue());
            });
        }
        // 发起请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                if (response.getStatusLine().getStatusCode() == SUCCESS) {
                    // 解析结果
                    HttpEntity httpEntity = response.getEntity();
                    return EntityUtils.toString(httpEntity, DEFAULT_CHARSET);
                } else {
                    log.error("[HTTP]请求响应失败！请求地址：{}，错误码：{}", url, response.getStatusLine().getStatusCode());
                }
            } catch (Exception e) {
                log.error("[HTTP]请求结果解析失败！请求地址：{}，错误信息：{}", url, e.getMessage());
            } finally {
                response.close();
            }
        } catch (Exception e) {
            log.error("[HTTP]请求失败！请求地址：{}，错误信息：{}", url, e.getMessage());
        } finally {
            httpClient.close();
        }
        return null;
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
    @Override
    public String post(String url, String param, List<HttpUtils.HttpHeaderEnums> headers) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
        if (headers != null && headers.size() > 0) {
            headers.forEach(header -> {
                httpPost.setHeader(header.getHeaderName(), header.getHeaderValue());
            });
        }
        if (param != null && !param.isEmpty()) {
            StringEntity reqEntity = new StringEntity(param, DEFAULT_CHARSET);
            httpPost.setEntity(reqEntity);
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                if (response.getStatusLine().getStatusCode() == SUCCESS) {
                    HttpEntity resEntity = response.getEntity();
                    return EntityUtils.toString(resEntity, DEFAULT_CHARSET);
                } else {
                    log.error("[HTTP]请求响应失败！请求地址：{}，错误码：{}", url, response.getStatusLine().getStatusCode());
                }
            } catch (Exception e) {
                log.error("[HTTP]请求结果解析失败！请求地址：{}，错误信息：{}", url, e.getMessage());
            } finally {
                response.close();
            }
        } catch (Exception e) {
            log.error("[HTTP]请求失败！请求地址：{}，错误信息：{}", url, e.getMessage());
        } finally {
            httpClient.close();
        }
        return null;
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
    public String postByFile(String url, MultipartFile file, String fileParamName, Map<String, String> params, List<HttpUtils.HttpHeaderEnums> headers) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getRequestConfig());
        if (headers != null && headers.size() > 0) {
            headers.forEach(header -> {
                httpPost.setHeader(header.getHeaderName(), header.getHeaderValue());
            });
        }
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
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                if (response.getStatusLine().getStatusCode() == SUCCESS) {
                    HttpEntity resEntity = response.getEntity();
                    String result = EntityUtils.toString(resEntity, StandardCharsets.UTF_8);
                    return result;
                } else {
                    log.error("[HTTP]请求响应失败！请求地址：{}，错误码：{}", url, response.getStatusLine().getStatusCode());
                }
            } catch (Exception e) {
                log.error("[HTTP]请求结果解析失败！请求地址：{}，错误信息：{}", url, e.getMessage());
            } finally {
                response.close();
            }
        } catch (Exception e) {
            log.error("[HTTP]请求失败！请求地址：{}，错误信息：{}", url, e.getMessage());
        } finally {
            httpClient.close();
        }
        return null;
    }

}
