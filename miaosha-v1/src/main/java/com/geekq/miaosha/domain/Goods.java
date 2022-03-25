package com.geekq.miaosha.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private Long id;
    private String goodsName;

    private String tag;
    private String tag2;
    private String rateOfReturnName;
    private String rateOfReturnValue;
    private String cycle;
    private String description;
    private String riskLevel;






    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
