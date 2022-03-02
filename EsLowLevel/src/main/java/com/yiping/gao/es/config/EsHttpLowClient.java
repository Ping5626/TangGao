package com.yiping.gao.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.stereotype.Component;

/**
 * @author 高一平
 * @date 2022/1/11
 * @description
 **/
@Slf4j
@Component
public class EsHttpLowClient {

    /**
     * 获取 LOW-LEVEL-CLIENT ES 链接
     *
     * @return
     */
    public RestClient getClient() {
        // ES 连接信息
        HttpHost[] hosts = {new HttpHost("10.128.129.87", 9200, "http")};
        // 设置身份认证
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("hrmapRead", "ChamcRHT2020"));
        // 创建 LOW-LEVEL-CLIENT 连接
        return RestClient.builder(hosts)
                .setRequestConfigCallback(requestConfigBuilder
                        -> requestConfigBuilder
                        // 统一设置超时 REQUEST 请求配置，也可以分别为每个 REQUEST 请求设置配置，见方法 getHttpOptions()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(50000)
                )
                .setHttpClientConfigCallback(httpClientBuilder
                        -> httpClientBuilder
                        // 设置身份认证
                         .setDefaultCredentialsProvider(credentialsProvider)
                        // 设置线程数
                        .setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build())
                )
                .build();
    }

    /**
     * 单独为 REQUEST 请求设置配置
     *
     * @param request REQUEST 请求
     * @return REQUEST 请求
     */
    public Request requestDefaultSet(Request request) {
        RequestOptions options = getDefaultHttpOptions();
        request.setOptions(options);
        return request;
    }

    /**
     * 单独为 REQUEST 请求设置配置
     *
     * @return
     */
    public RequestOptions getDefaultHttpOptions() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(50000).build();
        return RequestOptions.DEFAULT.toBuilder().setRequestConfig(requestConfig).build();
    }



}
