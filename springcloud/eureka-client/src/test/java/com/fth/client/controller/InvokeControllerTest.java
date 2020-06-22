package com.fth.client.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class InvokeControllerTest {
    @Autowired
    private RestTemplate restTemplate;
    @BeforeEach
    void setUp() {
        System.out.println("before");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void invokeServiceByRestTemplate() {
        
    }

    @Test
    void invokeServiceByFeign() {
    }

    @Test
    void invokeServiceByGetMethod() {
    }

    @Test
    void invokeServiceByPostMethod() {
    }

    @Test
    void invokeServiceByPostEntity() {
    }

    @Test
    void invokeServiceByExchange() {
    }

    @Test
    void invokeService() {
    }

    @Test
    void failCallback() {
    }
}