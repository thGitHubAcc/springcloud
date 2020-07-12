package com.fth.roketmq.config;
import com.fth.roketmq.listener.MyMessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {
    public static final Logger logger = LoggerFactory.getLogger(ConsumerConfig.class);

    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    @Value("${rocketmq.consumer.topics}")
    private String topics;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(topics, "*");

        consumer.registerMessageListener(new MyMessageListener() );
        consumer.start();

        return consumer;
    }

    /**
     * spring.application.name=mq02
     * rocketmq.producer.namesrvAddr=192.168.150.131:9876
     * rocketmq.producer.groupName=${spring.application.name}
     *
     * rocketmq.consumer.topics=tpk02
     */
}
