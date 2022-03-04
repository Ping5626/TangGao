package com.yiping.gao.es.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 高一平
 * @date 2022/2/28
 * @description
 **/
@Data
public class CommercialEstateEntity {
    /**
     * 小区建筑面积
     */
    private BigDecimal communityBuildingArea;
    /**
     * 小区使用土地面积
     */
    private BigDecimal communityLandArea;

}
