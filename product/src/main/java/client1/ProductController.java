package client1;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 远程访问取得客户端控制器
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/22
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@RestController
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    RemoteRecommendService remoteRecommendService;

    @RequestMapping("/product/recommends")
    @HystrixCommand(fallbackMethod = "callRecommendFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
    })
    public String remoteRecommends(@RequestParam(value = "productId",  required = true) int productId){
        return remoteRecommendService.getRecommendations(productId);
    }

    public String callRecommendFallback(int productId) {
        return "Read Timeout";
    }

}
