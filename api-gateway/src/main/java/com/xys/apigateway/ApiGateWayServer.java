package com.xys.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * api路由分发管理
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/15
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@SpringBootApplication
@EnableSidecar
@EnableZuulProxy
public class ApiGateWayServer {

    /**
     * 启动api路由分发管理服务
     *
     * @param args  所需参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGateWayServer.class, args);
    }

}
