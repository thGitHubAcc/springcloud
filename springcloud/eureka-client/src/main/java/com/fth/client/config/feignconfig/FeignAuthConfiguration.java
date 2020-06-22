package com.fth.client.config.feignconfig;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignAuthConfiguration {

    /**
     * Feign请求带上auth认证信息
     * @return
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("root", "root");
    }
}
