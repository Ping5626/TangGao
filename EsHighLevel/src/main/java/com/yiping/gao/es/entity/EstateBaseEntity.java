package com.yiping.gao.es.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 高一平
 * @date 2022/2/7
 * @description
 **/
@Data
public class EstateBaseEntity<T> extends BaseEntity {
    /******************** 基础信息 ********************/
    /**
     * 房产/土地
     */
    private String objectType;
    /**
     * 性质用途
     */
    private List<String> objectUsage;
    /**
     * 建筑面积
     */
    private Double buildingArea;
    /**
     * 土地面积
     */
    private Double landArea;
    /******************** 基础信息 ********************/
    /******************** 地理位置 ********************/
    /**
     * 地址
     */
    private String address;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县
     */
    private String district;
    /**
     * 乡镇
     */
    private String town;
    /**
     * 街道
     */
    private String street;
    /**
     * 区划编码
     */
    private String areaCode;
    /**
     * 经纬度
     */
    private String geoLocation;
    /******************** 地理位置 ********************/
    /******************** 评估信息 ********************/
    /**
     * 评估单价
     */
    private Double evalUnitPrice;
    /**
     * 评估总价
     */
    private Double evalTotalPrice;
    /**
     * 评估时间
     */
    private Date evalDate;
    /******************** 评估信息 ********************/
    /******************** 成交信息 ********************/
    /**
     * 成交单价
     */
    private Double dealUnitPrice;
    /**
     * 成交总价
     */
    private Double dealTotalPrice;
    /**
     * 成交状态
     */
    private String dealState;
    /**
     * 成交时间
     */
    private Date dealDate;
    /**
     * 类型
     */
    private String dealType;
    /******************** 成交信息 ********************/
    /**
     * 不同类型数据的私有字段
     */
    private T otherInfo;
}
