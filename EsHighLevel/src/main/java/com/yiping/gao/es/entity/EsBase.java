package com.yiping.gao.es.entity;

import lombok.Data;

/**
 * @author 高一平
 * @date 2022/1/11
 * @description
 **/
@Data
public class EsBase {
    /**
     * 数据 ID
     */
    private String id;
    /**
     * 所在索引库名称
     */
    private String index;
}
