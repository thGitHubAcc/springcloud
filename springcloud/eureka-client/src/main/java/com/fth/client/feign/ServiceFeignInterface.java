package com.fth.client.feign;

import com.fth.client.config.feignconfig.FeignAuthConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 设置Feign的服务及服务URI
 */
@FeignClient(name = "service",configuration = FeignAuthConfiguration.class)
public interface ServiceFeignInterface {
     @RequestMapping(value = "service-instance",method = RequestMethod.POST)
     public List getServiceInstance(@RequestParam String serviceName);
}
