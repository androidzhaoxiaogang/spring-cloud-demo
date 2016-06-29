package com.xys.product.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@ApiModel(value = "ProductAggregated", description = "描述产品的信息")
public class ProductAggregated {

    private int productId;
    private String name;
    private int weight;
    private List<RecommendationSummary> recommendations;
    private List<ReviewSummary> reviews;

    public ProductAggregated(Product product, List<Recommendation> recommendations, List<Review> reviews) {

        // 1. Setup product info
        this.productId = product.getProductId();
        this.name = product.getName();
        this.weight = product.getWeight();

        // 2. Copy summary recommendation info, if available
        if (recommendations != null)
            this.recommendations = recommendations.stream()
                    .map(r -> new RecommendationSummary(r.getRecommendationId(), r.getAuthor(), r.getRate()))
                    .collect(Collectors.toList());

        // 3. Copy summary review info, if available
        if (reviews != null)
            this.reviews = reviews.stream()
                    .map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(), r.getSubject()))
                    .collect(Collectors.toList());
    }

    @ApiModelProperty(position = 1, value = "产品ID", dataType = "Integer")
    public int getProductId() {
        return productId;
    }

    @ApiModelProperty(position = 2, value = "产品名称", dataType = "String")
    public String getName() {
        return name;
    }

    @ApiModelProperty(position = 3, value = "产品权重", dataType = "Integer")
    public int getWeight() {
        return weight;
    }

    @ApiModelProperty(position = 4, value = "产品建议列表", dataType = "RecommendationSummary")
    public List<RecommendationSummary> getRecommendations() {
        return recommendations;
    }

    @ApiModelProperty(position = 4, value = "产品评论列表", dataType = "ReviewSummary")
    public List<ReviewSummary> getReviews() {
        return reviews;
    }

}
