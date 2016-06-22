package com.xys.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/22
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@RestController
public class RecommendController {

    // Eureka会自动注入注册的所有Client信息，不过并没有啥用处
    @Autowired
    private DiscoveryClient client;

    @RequestMapping(method = RequestMethod.GET,value = "/recommend")
    public String getRecommendations(@RequestParam(value = "productId",  required = true) int productId){

        long start = System.currentTimeMillis();
        ServiceInstance instance = client.getLocalServiceInstance();
        long cost = System.currentTimeMillis() - start;
        return "PRODUCT-INFO : " + productId + " WITH [" + instance.getHost() + ", " + instance.getServiceId() + ", spent " + cost + "]";
    }

}
