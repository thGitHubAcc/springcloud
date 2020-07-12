package com.fth.queue.activemq03.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.ArrayList;

@Service
public class SendService {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, String msg) {


        ConnectionFactory connectionFactory = jmsTemplate.getConnectionFactory();
        try {
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        jmsTemplate.send(destination,new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {

                TextMessage textMessage = session.createTextMessage("xxoo");
                textMessage.setStringProperty("hehe", "enen");
                return textMessage;
            }
        });


    }
    public void send2(String destination, String msg) {


        ArrayList<String> list = new ArrayList<>();

        list.add("xxx");
        list.add("lain");
        list.add("zhou");
        jmsMessagingTemplate.convertAndSend(destination, list);
    }
    public void send3(String destination, String msg) {


        ArrayList<String> list = new ArrayList<>();

        list.add("xxxx");
        list.add("lain");
        list.add("zhou");
        jmsMessagingTemplate.convertAndSend(new ActiveMQQueue(destination), list);
    }
}
