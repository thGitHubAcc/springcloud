package com.fth.roketmq.rmq01;
import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class Consumer {
    public static void main(String[] args)throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xxoocsm");

        consumer.setNamesrvAddr("127.0.0.1:9876");


        /**
         * a()
         * c()
         * d()
         * b -> 向 rocketmq 写入一条消息()
         * rollback()
         *
         *
         *
         */

        // 每个consumer 关注一个topic

        // topic 关注的消息的地址
        // 过滤器 * 表示不过滤
        consumer.subscribe("myTopic001", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {

            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {

                    System.out.println(new String(msg.getBody()));;
                }
                // 默认情况下 这条消息只会被 一个consumer 消费到 点对点
                // message 状态修改
                // ack
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });


        // 讲道理 冲突，首先，别这么干
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.start();

        // 集群 -> 一组consumer
        // 广播

        System.out.println("Consumer 02 start...");
    }
}
