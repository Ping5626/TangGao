package com.yiping.gao.es.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.yiping.gao.es.entity.BuildingEs;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 高一平
 * @date 2022/1/10
 * @description
 **/
@Slf4j
@Component
public class EsHttpHighClient {

    public ElasticsearchClient getClient() throws IOException {
        // ES 连接信息
        HttpHost[] hosts = {new HttpHost("localhost", 9200, "http")};
        // 创建 LOW-LEVEL-CLIENT 连接
        RestClient lowLevelClient = RestClient.builder(hosts).build();
        ElasticsearchTransport transport = new RestClientTransport(lowLevelClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);
        return client;
    }

}
