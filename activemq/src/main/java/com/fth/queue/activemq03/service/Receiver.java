package com.fth.queue.activemq03.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Receiver {
    @JmsListener(destination = "receiver",containerFactory = "jmsListenerContainerQueue" )
    public void rece(ArrayList list) {

        System.out.println("收到消息：" + list);
    }
}
