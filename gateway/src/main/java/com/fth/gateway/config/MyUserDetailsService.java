package com.fth.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MyUserDetailsService implements ReactiveUserDetailsService {

    private Map<String, UserDetails> users;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    MyUserDetailsService(){
        this.users = new ConcurrentHashMap();
    }

    MyUserDetailsService(Collection<UserDetails> users){
        this.users = new ConcurrentHashMap();
        Iterator var2 = users.iterator();

        while(var2.hasNext()) {
            UserDetails user = (UserDetails)var2.next();
            this.users.put(this.getKey(user.getUsername()), user);
        }
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        System.out.println("userdetails : " + this.users);

        String key = this.getKey(username);
        try{
            UserDetails result = (UserDetails)this.users.get(key);

            this.users.get(key);
            if(result == null ){
                UserDetails user = User.builder().username("admin").password(passwordEncoder.encode("123456")).roles("admin").authorities("admin").build();
                this.users.put("admin",user);
                return Mono.just(user);
            }
            return result == null ? Mono.empty() : Mono.just(User.withUserDetails(result).build());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Mono.just(User.builder().username("admin").password(passwordEncoder.encode("123456")).authorities("admin").build());
        }
//        System.out.println("userdetails");
//
//        //内存中缓存权限数据
//        User.UserBuilder userBuilder = User.builder();
//        UserDetails admin = userBuilder.username("admin").password(passwordEncoder.encode("123456")).roles("USER", "ADMIN").build();
//        UserDetails user = userBuilder.username("user").password(passwordEncoder.encode("123456")).roles("USER").build();
//
//        // 输出加密密码
//        String encodePassword = passwordEncoder.encode("123456");
//        System.out.println(admin);
//
//        return new MapReactiveUserDetailsService(admin,user);
    }

    private String getKey(String username) {
        return username.toLowerCase();
    }


    public void remove(String username){
        this.users.remove(this.getKey(username));
    }

}
