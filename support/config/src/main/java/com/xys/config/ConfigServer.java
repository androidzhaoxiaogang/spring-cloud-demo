package com.xys.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置服务，将配置信息发送给客户端
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/21
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServer {

    /**
     * 启动服务
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }

}
