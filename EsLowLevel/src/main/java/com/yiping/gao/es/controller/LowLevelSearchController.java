package com.yiping.gao.es.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yiping.gao.es.config.EsHttpLowClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 高一平
 * @date 2022/1/11
 * @description
 **/
@Slf4j
@RestController
public class LowLevelSearchController {

    @Autowired
    private EsHttpLowClient client;

    @RequestMapping("{index}/search")
    public JSONObject search(@PathVariable(name = "index") String index, @RequestBody JSONObject param) throws IOException {
        RestClient lowLevelClient = client.getClient();
        Request request = new Request("POST", "/" + index + "/_search");
        request.setEntity(new StringEntity(param.toJSONString(), StandardCharsets.UTF_8));
        Response response = lowLevelClient.performRequest(request);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return JSON.parseObject(result);
    }

}
