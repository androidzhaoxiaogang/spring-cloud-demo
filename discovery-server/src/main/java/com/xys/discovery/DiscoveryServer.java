package com.xys.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务发现
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/15
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServer {

    /**
     * 启动服务发现服务
     *
     * @param args 所需参数
     */
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServer.class, args);
    }

}
