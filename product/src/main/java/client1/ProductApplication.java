package client1;

import com.netflix.discovery.DiscoveryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 产品组件
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/20
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
public class ProductApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ProductApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
        LOG.info("Register ShutdownHook");
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                LOG.info("Shutting down product service, unregister from Eureka!");
                DiscoveryManager.getInstance().shutdownComponent();
            }
        });
    }

}
