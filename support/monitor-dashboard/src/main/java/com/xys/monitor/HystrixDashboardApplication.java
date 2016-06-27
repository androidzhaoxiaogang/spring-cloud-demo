package com.xys.monitor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 接口监控服务
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
public class HystrixDashboardApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HystrixDashboardApplication.class).web(true);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixDashboardApplication.class).web(true).run(args);
    }

    @Controller
    public static class HomeController {
        @RequestMapping("/")
        public String home() {
            return "forward:/hystrix";
        }
    }

}