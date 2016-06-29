package com.xys.product.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@ApiModel(value = "ReviewSummary", description = "对产品的评论")
public class ReviewSummary {

    private int reviewId;
    private String author;
    private String subject;

    public ReviewSummary(int reviewId, String author, String subject) {
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
    }

    @ApiModelProperty(position = 1, value = "评论ID", dataType = "Integer")
    public int getReviewId() {
        return reviewId;
    }

    @ApiModelProperty(position = 2, value = "评论人", dataType = "String")
    public String getAuthor() {
        return author;
    }

    @ApiModelProperty(position = 3, value = "评论标题", dataType = "String")
    public String getSubject() {
        return subject;
    }

}
