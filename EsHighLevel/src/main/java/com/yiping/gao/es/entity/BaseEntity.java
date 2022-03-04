package com.yiping.gao.es.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 高一平
 * @date 2022/2/7
 * @description
 **/
@Data
public class BaseEntity {
    /******************** 源数据位置 ********************/
    private String indexId;
    /**
     * 源数据存储位置的唯一ID
     */
    private String dataId;
    /**
     * 源数据存储位置
     */
    private String dataFrom;
    /******************** 源数据位置 ********************/
    /******************** 基础信息 ********************/
    /**
     * 显示标题信息
     */
    private String title;
    /**
     * 数据日期
     */
    private Date dataDateForQuery;
    /******************** 基础信息 ********************/
}
