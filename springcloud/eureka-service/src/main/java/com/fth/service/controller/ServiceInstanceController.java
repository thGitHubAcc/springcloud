package com.fth.service.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ServiceInstanceController {

    /**
     * 服务发现
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallBack")
    @RequestMapping("service-instance")
    @ResponseBody
    public List getServiceInstance(String serviceName){
        List<String> services = discoveryClient.getServices();
        services.forEach(System.out::println);
        return discoveryClient.getInstances(serviceName);
    }

    @RequestMapping("hello")
    @ResponseBody
    public String hello(HttpServletRequest request){
        int port = request.getServerPort();
        return "hello："+port;
    }

    /**
     *
     * @param serviceName
     * @return
     */
    public List fallBack(String serviceName){
        return null;
    }

    @HystrixCommand(fallbackMethod = "fail")
    @RequestMapping("turbineTest")
    @ResponseBody
    public String turbineTest(){
        String url = "http://";
        String service = "client/";//服务名
        String uri = "hello";//uri
        return restTemplate.getForObject(url + service + uri,String.class);
    }

    public String fail(){
        return "fail";
    }
}
