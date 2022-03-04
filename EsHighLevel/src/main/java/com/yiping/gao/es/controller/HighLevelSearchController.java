package com.yiping.gao.es.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.alibaba.fastjson.JSONObject;
import com.yiping.gao.es.config.EsHttpHighClient;
import com.yiping.gao.es.entity.EstateBaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author 高一平
 * @date 2022/1/11
 * @description
 **/
@Slf4j
@RestController
public class HighLevelSearchController {

    @Autowired
    private EsHttpHighClient client;

    @RequestMapping("test")
    public void test() throws IOException {
        ElasticsearchClient highLevelClient = client.getClient();
        SearchResponse<EstateBaseEntity> search = highLevelClient.search(searchRequest -> searchRequest
                        .index("test")
                        .query(query -> query.term(term -> term.field("objectType").value(value -> value.stringValue("房产")))),
                EstateBaseEntity.class);
        for (Hit<EstateBaseEntity> hit : search.hits().hits()) {
            log.info(hit.source().toString());
        }
    }

}

