package com.xys.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.Principal;

/**
 * 授权认证服务器
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
@EnableAuthorizationServer
public class AuthorizationServer extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServer.class, args);
    }

    @RestController
    public static class UserController {

        @RequestMapping("/user")
        public Principal user(Principal user) {
            return user;
        }

    }
}
