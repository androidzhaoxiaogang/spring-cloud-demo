package com.xys.product.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@ApiModel( value = "RecommendationSummary", description = "对产品的建议" )
public class RecommendationSummary {

    private int recommendationId;
    private String author;
    private int rate;

    public RecommendationSummary(int recommendationId, String author, int rate) {
        this.recommendationId = recommendationId;
        this.author = author;
        this.rate = rate;
    }

    @ApiModelProperty(position = 1, value = "建议ID", dataType = "Integer" )
    public int getRecommendationId() {
        return recommendationId;
    }

    @ApiModelProperty(position = 2, value = "建议人", dataType = "String" )
    public String getAuthor() {
        return author;
    }

    @ApiModelProperty(position = 4, value = "比率", dataType = "Integer" )
    public int getRate() {
        return rate;
    }

}
