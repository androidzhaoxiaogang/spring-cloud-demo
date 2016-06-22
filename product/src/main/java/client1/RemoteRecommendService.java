package client1;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程访问Recommend服务
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/22
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@FeignClient("recommend")
public interface RemoteRecommendService {

    @RequestMapping(method = RequestMethod.GET,value = "/recommend")
    public String getRecommendations(@RequestParam(value = "productId",  required = true) int productId);

}
