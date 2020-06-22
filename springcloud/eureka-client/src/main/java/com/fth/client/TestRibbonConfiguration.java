package com.fth.client;

import com.fth.client.config.ribbonconfig.RibbonConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * 给服务提供端 设置 Ribbon负载均衡 及策略
 */
@Configuration
@RibbonClient(name = "service",configuration = RibbonConfig.class)
public class TestRibbonConfiguration {
}
