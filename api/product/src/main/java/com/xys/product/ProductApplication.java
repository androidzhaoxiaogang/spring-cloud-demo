package com.xys.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;

/**
 * 产品组件
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableResourceServer
public class ProductApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ProductApplication.class);

    static {
        // for localhost testing only
        LOG.warn("Will now disable hostname check in SSL, only to be used during development");
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
//        LOG.info("Register MDCHystrixConcurrencyStrategy");
//        HystrixPlugins.getInstance().registerConcurrencyStrategy(new MDCHystrixConcurrencyStrategy());

        ConfigurableApplicationContext ctx = SpringApplication.run(ProductApplication.class, args);

        LOG.info("Connected to RabbitMQ at: {}", ctx.getEnvironment().getProperty("spring.rabbitmq.host"));
    }

    @Controller
   public static class ApiDocument {

        /**
         * 导航到api接口文档界面
         *
         * @return  api接口文档界面
         */
        @RequestMapping("/")
        public String apiDoc(){
            return "forward:/swagger-ui.html";
        }

    }

}
