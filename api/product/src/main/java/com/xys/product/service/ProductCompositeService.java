package com.xys.product.service;

import com.xys.product.config.SwaggerConfig;
import com.xys.product.model.Product;
import com.xys.product.model.ProductAggregated;
import com.xys.product.model.Recommendation;
import com.xys.product.model.Review;
import com.xys.product.util.ServiceUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 产品控制器
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@RestController
@RequestMapping("/products")
@Api(value = "产品操作")
public class ProductCompositeService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeService.class);

    @Autowired
    ProductCompositeIntegration integration;

    @Autowired
    ServiceUtils util;

    @ApiOperation(value="产品API欢迎信息", notes="")
    @RequestMapping( method = RequestMethod.GET )
    public String getProduct() {
        return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"Hello from ProductAPi\"}";
    }

    @ApiOperation(
            value = "获取产品详细信息",
            notes = "根据url的id来获取产品详细信息",
            authorizations = {@Authorization(value = SwaggerConfig.SECURITY_SCHEMA_O_AUTH_2, scopes =
                    {@AuthorizationScope( scope = SwaggerConfig.AUTHORIZATION_SCOPE_GLOBAL, description = SwaggerConfig.AUTHORIZATION_SCOPE_GLOBAL_DESC )})})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "oauth2.0认证token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "productId", value = "产品ID", required = true, dataType = "Integer", paramType = "path")
    })
    @RequestMapping( value = "/{productId}", method = RequestMethod.GET)
    public ResponseEntity<ProductAggregated> getProduct(@PathVariable int productId) {

        // 1. First get mandatory product information
        ResponseEntity<Product> productResult = integration.getProduct(productId);

        if (!productResult.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the getProduct call
            return util.createResponse(null, productResult.getStatusCode());
        }

        // 2. Get optional recommendations
        List<Recommendation> recommendations = null;
        try {
            ResponseEntity<List<Recommendation>> recommendationResult = integration.getRecommendations(productId);
            if (!recommendationResult.getStatusCode().is2xxSuccessful()) {
                // Something went wrong with getRecommendations, simply skip the recommendation-information in the response
                LOG.debug("Call to getRecommendations failed: {}", recommendationResult.getStatusCode());
            } else {
                recommendations = recommendationResult.getBody();
            }
        } catch (Throwable t) {
            LOG.error("getProduct error", t);
            throw t;
        }


        // 3. Get optional reviews
        ResponseEntity<List<Review>> reviewsResult = integration.getReviews(productId);
        List<Review> reviews = null;
        if (!reviewsResult.getStatusCode().is2xxSuccessful()) {
            // Something went wrong with getReviews, simply skip the review-information in the response
            LOG.debug("Call to getReviews failed: {}", reviewsResult.getStatusCode());
        } else {
            reviews = reviewsResult.getBody();
        }

        return util.createOkResponse(new ProductAggregated(productResult.getBody(), recommendations, reviews));
    }

}
