package com.fth.client.controller;

import com.fth.client.feign.ServiceFeignInterface;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class InvokeController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private ServiceFeignInterface serviceFeign;

    String url = "http://";
    String service = "service/";//服务名
    String uri = "service-instance";//uri

    /**
     * postForObject
     * @param serviceName
     * @return
     */
    @RequestMapping("invoke-service-by-resttemp")
    @ResponseBody
    public List invokeServiceByRestTemplate(String serviceName){

        MultiValueMap<String,String> paramMap = new LinkedMultiValueMap<String,String>();
        paramMap.add("serviceName",serviceName);
        return restTemplate.postForObject(url+service+uri,paramMap,List.class);
    }
    /**
     * Feign 调用
     * @param serviceName
     * @return
     */
    @RequestMapping("invoke-service-by-feign")
    @ResponseBody
    public List invokeServiceByFeign(String serviceName){
        return serviceFeign.getServiceInstance(serviceName);
    }

    /**
     * getForObject
     * @param serviceName
     * @return
     */
    @RequestMapping("invoke-service-by-getmethod")
    @ResponseBody
    public List invokeServiceByGetMethod(String serviceName){
        return restTemplate.getForObject(url+service+uri+"?serviceName={serviceName}",List.class,serviceName);
    }

    /**
     * postForObject
     * @param serviceName
     * @return
     */
    @RequestMapping("invoke-service-by-postmethod")
    @ResponseBody
    public List invokeServiceByPostMethod(String serviceName){
        MultiValueMap<String,String> paramMap = new LinkedMultiValueMap<String,String>();
        paramMap.add("serviceName",serviceName);
        return restTemplate.postForObject(url+service+uri,paramMap,List.class);
    }

    /**
     * postForEntity
     * @param serviceName
     * @return
     */
    @RequestMapping("invoke-service-by-postentity")
    @ResponseBody
    public List invokeServiceByPostEntity(String serviceName){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization" , "Basic cm9vdDpyb290");
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
        paramMap.add("serviceName", serviceName);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(paramMap,headers);
        return restTemplate.postForEntity(url+service+uri, httpEntity, List.class).getBody();
    }

    /**
     * exchange
     * @param serviceName
     * @return
     */
    @RequestMapping("invoke-service-by-exchange")
    @ResponseBody
    public List invokeServiceByExchange(String serviceName){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization" , "Basic cm9vdDpyb290");
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
        paramMap.add("serviceName", serviceName);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(paramMap,headers);
        return restTemplate.exchange(url+service+uri, HttpMethod.POST, httpEntity, List.class).getBody();
    }

    /**
     * fallBack 熔断 快速失败
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallBack")
    @RequestMapping("invoke-service")
    @ResponseBody
    public String invokeService(){
        restTemplate.getForObject(url+service+uri+"?serviceName={serviceName}",List.class,"service");
        return "success";
    }

    /**
     * 熔断备用策略
     * @return
     */
    public String fallBack(){
        return "fail";
    }


    @RequestMapping("hello")
    @ResponseBody
    public String hello(HttpServletRequest request){
        int port = request.getServerPort();
        return "hello："+port;
    }

    @RequestMapping("token")
    @ResponseBody
    public String ingnoreToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return "token："+ token;
    }

    @RequestMapping("index")
    @ResponseBody
    public ModelAndView index(){
        System.out.println("index");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("test");
        return mv;
    }

    @RequestMapping("test")
    public Map test(){
        Map map = new HashMap();
        map.put("result","ok");
        return map;
    }

    @HystrixCommand(fallbackMethod = "fail")
    @RequestMapping("turbineTest")
    public String turbineTest(){
        String url = "http://";
        String service = "service/";//服务名
        String uri = "hello";//uri
        return restTemplate.getForObject(url + service + uri,String.class);
    }

    public String fail(){
        return "fail";
    }
}
