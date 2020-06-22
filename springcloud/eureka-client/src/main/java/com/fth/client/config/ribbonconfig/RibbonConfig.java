package com.fth.client.config.ribbonconfig;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;


@Configurable
@ExcudeRibbonConfig
public class RibbonConfig {

    @Bean
    public IRule ribbonRule(){
        return new RandomRule();
    }
}
