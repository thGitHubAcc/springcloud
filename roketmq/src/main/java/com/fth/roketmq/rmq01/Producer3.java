package com.fth.roketmq.rmq01;
import java.util.ArrayList;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
public class Producer3 {
    public static void main(String[] args)throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("xoxogp");

        // 设置nameserver地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        Message message = new Message("myTopic001", "xxooxx".getBytes());

        // 单向消息
        // p 网络不确定
        producer.sendOneway(message);

        //	producer.shutdown();
        System.out.println("已经停机");

    }
}
