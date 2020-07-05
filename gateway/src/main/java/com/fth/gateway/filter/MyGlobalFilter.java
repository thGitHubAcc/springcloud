package com.fth.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Configuration
public class MyGlobalFilter implements Ordered, GlobalFilter {

    @Resource
    private ReactiveUserDetailsService userDetailsService;

    private String springSecurityContextAttrName = "SPRING_SECURITY_CONTEXT";

    private int count = 0;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("userdetails:" +userDetailsService.findByUsername("admin"));
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
