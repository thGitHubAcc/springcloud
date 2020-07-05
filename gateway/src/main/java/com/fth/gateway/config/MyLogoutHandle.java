package com.fth.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class MyLogoutHandle implements ServerLogoutHandler {
    private String springSecurityContextAttrName = "SPRING_SECURITY_CONTEXT";
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        
        return exchange.getExchange().getSession().doOnNext((session) -> {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            myUserDetailsService.remove(userDetails.getUsername());
            session.getAttributes().remove(springSecurityContextAttrName);
        }).flatMap((session) -> {
            return session.changeSessionId();
        });
    }
}
