package com.fth.server;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventConfig {
    /**
     * 事件监听
     * @param event
     */
    @EventListener
    public void serviceRegist(EurekaInstanceRegisteredEvent event){
        System.out.println(event.getInstanceInfo().getAppName());
    }
}
