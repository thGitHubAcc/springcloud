package com.fth.client;

import com.fth.client.config.ribbonconfig.ExcudeRibbonConfig;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@EnableHystrixDashboard//开启可视化
@EnableCircuitBreaker//开启熔断
@EnableFeignClients//开启Feign
@EnableEurekaClient//eureka客户端方式启动
@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,value = ExcudeRibbonConfig.class))//忽略带有ExcudeRibbonConfig注解的配置类
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    /**
     * RestTemplate携带认证信息
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
//        RestTemplate restTemplate = new RestTemplate();
//        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
//        interceptors.add(new BasicAuthenticationInterceptor("root","root"));
//        return restTemplate;

// ====================================================================
        return new RestTemplateBuilder()
                .basicAuthentication("root", "root")
                .build();
    }
//
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
