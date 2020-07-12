package com.fth.roketmq.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProducerConfig.class);

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;

    @Bean
    public DefaultMQProducer getRocketMQProducer() {

        DefaultMQProducer producer;
        producer = new DefaultMQProducer(this.groupName);

        producer.setNamesrvAddr(this.namesrvAddr);

        try {
            producer.start();
            System.out.println("start....");

            LOGGER.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]", this.groupName,
                    this.namesrvAddr));
        } catch (MQClientException e) {
            LOGGER.error(String.format("producer is error {}", e.getMessage(), e));
        }
        return producer;

    }
}