package com.yiping.gao.es.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 高一平
 * @date 2022/2/28
 * @description
 **/
@Data
public class CollateralEstateEntity {
    /**
     * 评估机构
     */
    private String evalOrg;
    /**
     * 评估基准日
     */
    private Date evalStartDate;
    /**
     * 评估有效截止日
     */
    private Date evalEndDate;
    /**
     * 城市级别
     */
    private Date cityLevel;
    /**
     * 详细分类
     */
    private String specificClassify;
}
