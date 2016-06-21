package client1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RestController
public class Test1Client {

    public static void main(String[] args) {
        SpringApplication.run(Test1Client.class, args);
    }

//    @Resource
//    RestTemplate restTemplate;
//
//    @RequestMapping("ribbonHello")
//    public String ribbonHello() {
//        return restTemplate.getForEntity("http://test1-service/hello", String.class).getBody();
//    }

    @Resource
    HelloService helloService;

    @RequestMapping("remoteHello")
    public String remoteHello() {
        return helloService.remoteHello();
    }
}
