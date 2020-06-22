package com.fth.zuul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ZuulHelloController {

    @RequestMapping("hello")
    @ResponseBody
    public String hello(HttpServletRequest request){
        int port = request.getServerPort();
        return "hello："+port;
    }
}
