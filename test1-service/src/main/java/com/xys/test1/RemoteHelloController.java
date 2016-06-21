package com.xys.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@RestController
public class RemoteHelloController {

    //Eureka会自动注入注册的所有Client信息，不过并没有啥用处
    @Autowired
    private DiscoveryClient client;

    @RequestMapping("hello")
    public String hello() {
        long start = System.currentTimeMillis();
        ServiceInstance instance = client.getLocalServiceInstance();
        // 随机睡眠1000毫秒以内
//        try {
//            Thread.sleep(new Random().nextInt(1000));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        long cost = System.currentTimeMillis() - start;
        return "Remote Hello~ " + instance.getHost() + ", " + instance.getServiceId() + ", spent " + cost;
    }

}
