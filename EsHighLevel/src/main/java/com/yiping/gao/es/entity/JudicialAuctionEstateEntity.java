package com.yiping.gao.es.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 高一平
 * @date 2022/2/28
 * @description
 **/
@Data
public class JudicialAuctionEstateEntity {
    /**
     * 链接地址
     */
    private String url;
    /**
     * 来源
     */
    private String webSource;
    /**
     * 土地性质
     */
    private String landProperty;
    /**
     * 起拍日期
     */
    private Date sellStartDate;
    /**
     * 起拍总价
     */
    private BigDecimal sellStartTotalPrice;
    /**
     * 拍卖次数
     */
    private String sellTimes;
    /**
     * 处置单位
     */
    private String disposalOrg;
    /**
     * 报名人数
     */
    private Integer applyPeopleNum;
    /**
     * 设置提醒人数
     */
    private Integer remindPeopleNum;
    /**
     * 围观人数
     */
    private Integer onlookPeopleNum;
}
