package com.fth.queue.activemq03.controller;

import com.fth.queue.activemq03.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {
    @Autowired
    SendService senderSrv;

    @RequestMapping("send")
    public String send() {


//        senderSrv.send("receiver","hello~!");
        senderSrv.send2("receiver","hello~!");
//        senderSrv.send3("receiver","hello~!");

        return "ok";
    }
}
