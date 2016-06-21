package com.xys.test1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Test1Service {
    public static void main(String[] args) {
        SpringApplication.run(Test1Service.class, args);
    }
}
