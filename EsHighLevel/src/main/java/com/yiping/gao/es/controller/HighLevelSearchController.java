package com.yiping.gao.es.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.yiping.gao.es.config.EsHttpHighClient;
import com.yiping.gao.es.entity.BuildingEs;
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
        SearchResponse<BuildingEs> search = highLevelClient.search(searchRequest -> searchRequest
                        .index("building")
                        .query(query -> query.term(term -> term.field("city").value(value -> value.stringValue("北京")))),
                BuildingEs.class);
        for (Hit<BuildingEs> hit : search.hits().hits()) {
            log.info(hit.toString());
        }
    }

}
