package com.xys.product.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xys.product.model.Product;
import com.xys.product.model.Recommendation;
import com.xys.product.model.Review;
import com.xys.product.util.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@Component
public class ProductCompositeIntegration {

    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegration.class);

    @Resource
    private LoadBalancerClient loadBalancer;

    @Resource
    ServiceUtils util;

    @Resource
    @LoadBalanced
    private RestOperations restTemplate;


    // -------- //
    // PRODUCTS //
    // -------- //

    @HystrixCommand(fallbackMethod = "defaultProduct")
    public ResponseEntity<Product> getProduct(int productId) {

        LOG.debug("Will call getProduct with Hystrix protection");

        String url = "http://product-service/product/" + productId;
        LOG.debug("GetProduct from URL: {}", url);

        ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
        LOG.debug("GetProduct http-status: {}", resultStr.getStatusCode());
        LOG.debug("GetProduct body: {}", resultStr.getBody());

        Product product = response2Product(resultStr);
        LOG.debug("GetProduct.id: {}", product.getProductId());

        return util.createOkResponse(product);
    }

    /**
     * Fallback method for getProduct()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<Product> defaultProduct(int productId) {
        LOG.warn("Using fallback method for product-service");
        // If we can't get basic product info we better fail!
        return util.createResponse(null, HttpStatus.BAD_GATEWAY);
    }

    // --------------- //
    // RECOMMENDATIONS //
    // --------------- //

    @HystrixCommand(fallbackMethod = "defaultRecommendations")
    public ResponseEntity<List<Recommendation>> getRecommendations(int productId) {
        try {
            LOG.debug("Will call getRecommendations with Hystrix protection");

            String url = "http://recommendation-service/recommendation?productId=" + productId;
            LOG.debug("GetRecommendations from URL: {}", url);

            ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
            LOG.debug("GetRecommendations http-status: {}", resultStr.getStatusCode());
            LOG.debug("GetRecommendations body: {}", resultStr.getBody());

            List<Recommendation> recommendations = response2Recommendations(resultStr);
            LOG.debug("GetRecommendations.cnt {}", recommendations.size());

            return util.createOkResponse(recommendations);
        } catch (Throwable t) {
            LOG.error("getRecommendations error", t);
            throw t;
        }
    }


    /**
     * Fallback method for getRecommendations()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<List<Recommendation>> defaultRecommendations(int productId) {
        LOG.warn("Using fallback method for recommendation-service");
        LOG.debug("GetRecommendations.fallback-cnt {}", 1);
        return util.createResponse(
                Arrays.asList(new Recommendation(productId, 1, "Fallback Author 1", 1, "Fallback Content 1")),
                HttpStatus.OK);
    }


    // ------- //
    // REVIEWS //
    // ------- //

    @HystrixCommand(fallbackMethod = "defaultReviews")
    public ResponseEntity<List<Review>> getReviews(int productId) {
        LOG.debug("Will call getReviews with Hystrix protection");

        String url = "http://review-service/review?productId=" + productId;
        LOG.debug("GetReviews from URL: {}", url);

        ResponseEntity<String> resultStr = restTemplate.getForEntity(url, String.class);
        LOG.debug("GetReviews http-status: {}", resultStr.getStatusCode());
        LOG.debug("GetReviews body: {}", resultStr.getBody());

        List<Review> reviews = response2Reviews(resultStr);
        LOG.debug("GetReviews.cnt {}", reviews.size());

        return util.createOkResponse(reviews);
    }


    /**
     * Fallback method for getReviews()
     *
     * @param productId
     * @return
     */
    public ResponseEntity<List<Review>> defaultReviews(int productId) {
        LOG.warn("Using fallback method for review-service");
        LOG.debug("GetReviews.fallback-cnt {}", 1);
        return util.createResponse(
                Arrays.asList(new Review(productId, 1, "Fallback Author 1", "Fallback Subject 1", "Fallback Content 1")),
                HttpStatus.OK);
    }

    // ----- //
    // UTILS //
    // ----- //

    /*
     * TODO: Extract to a common util-lib
     */

    private ObjectReader productReader = null;
    private ObjectReader getProductReader() {

        if (productReader != null) return productReader;

        ObjectMapper mapper = new ObjectMapper();
        return productReader = mapper.reader(Product.class);
    }

    private ObjectReader reviewsReader = null;
    private ObjectReader getReviewsReader() {
        if (reviewsReader != null) return reviewsReader;

        ObjectMapper mapper = new ObjectMapper();
        return reviewsReader = mapper.reader(new TypeReference<List<Review>>() {});
    }

    public Product response2Product(ResponseEntity<String> response) {
        try {
            return getProductReader().readValue(response.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: Gereralize with <T> method, skip objectReader objects!
    private List<Recommendation> response2Recommendations(ResponseEntity<String> response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List list = mapper.readValue(response.getBody(), new TypeReference<List<Recommendation>>() {});
            List<Recommendation> recommendations = list;
            return recommendations;

        } catch (IOException e) {
            LOG.warn("IO-err. Failed to read JSON", e);
            throw new RuntimeException(e);

        } catch (RuntimeException re) {
            LOG.warn("RTE-err. Failed to read JSON", re);
            throw re;
        }
    }

    private List<Review> response2Reviews(ResponseEntity<String> response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List list = mapper.readValue(response.getBody(), new TypeReference<List<Review>>() {});
            List<Review> reviews = list;
            return reviews;

        } catch (IOException e) {
            LOG.warn("IO-err. Failed to read JSON", e);
            throw new RuntimeException(e);

        } catch (RuntimeException re) {
            LOG.warn("RTE-err. Failed to read JSON", re);
            throw re;
        }
    }

}